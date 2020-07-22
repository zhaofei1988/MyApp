package everlinkintl.com.myapp.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.TextToSpeechCommon;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.BasicData;
import everlinkintl.com.myapp.datatemplate.NotificationRead;
import everlinkintl.com.myapp.datatemplate.Send;
import everlinkintl.com.myapp.datatemplate.SendLoction;
import everlinkintl.com.myapp.http.API;
import everlinkintl.com.myapp.http.Okhttp;
import everlinkintl.com.myapp.newdata.GrabTasksCountData;
import everlinkintl.com.myapp.newdata.Routing;
import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class WebSocketService extends Service {
    MapLocation mapLocation = null;
    private boolean mReceiverTag = false;   //广播接受者标识
    private StompClient mStompClient;
    private CompositeDisposable compositeDisposable;
    TextToSpeechCommon textToSpeechCommon;
    // 两次点击按钮之间的最小点击间隔时间(单位:ms)
    private static final int MIN_CLICK_DELAY_TIME = 6000;
    // 最后一次点击的时间
    private long lastClickTime;
    int time = 0;
    int time1 = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler handler = new Handler() {
        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            switch (msg.what) {
                // 定位过来的信息 MapLocation
                case 1:
                    if (!Tools.isEmpty(msg.obj)) {
                        loictionSend((String) msg.obj);
                        tasksCount();
                        dot((String) msg.obj);
                    }
                    break;
                // 定位过来的信息 MapLocation
                case 2:
                    restart();
                    break;
                case 3:
                    if (!Tools.isEmpty(msg.obj)) {
                        Intent intent = new Intent(Cons.RECEIVER_ACTION);
                        intent.putExtra(Cons.RECEIVER_PUT_RSULT, (String) msg.obj);
                        getApplicationContext().sendBroadcast(intent);
                    }
                    break;
            }
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null) {
                String action = intent.getAction();
                if (Cons.RECEIVER_ACTION_SERVER.equals(action)) {
                    String locationResult = intent.getStringExtra(Cons.RECEIVER_PUT_RSULT);
                    if (null != locationResult && !locationResult.trim().equals("")) {
                      if(locationResult.trim().equals("closeService")){
                          Intent intent2 = new Intent(WebSocketService.this, WebSocketService.class);
                          stopService(intent2);
                      }

                    }
                }
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mReceiverTag) {     //在注册广播接受者的时候 判断是否已被注册,避免重复多次注册广播
            IntentFilter filter = new IntentFilter();
            mReceiverTag = true;
            filter.addAction(Cons.RECEIVER_ACTION_SERVER);
            registerReceiver(broadcastReceiver, filter);
        }
        mapLocation = new MapLocation(this.getApplicationContext(), handler);
        mapLocation.startLocation();
        flags = START_STICKY;
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_app";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "恒联物流", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("恒联物流")
                    .setContentText("恒联物流服务").build();
            startForeground(1, notification);
        }
        socket();

        return super.onStartCommand(intent, flags, startId);
    }

    private void socket() {
        String token = (String) SharedPreferencesUtil.getParam( Cons.EVERLINKINT_LOGIN_SP, "");
        String name = (String) SharedPreferencesUtil.getParam( Cons.EVERLINKINT_LOGIN_NAME, "");
        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("token", token));
        headers.add(new StompHeader("username", name));
        headers.add(new StompHeader(StompHeader.ACK, "client"));
        if (Tools.isEmpty(token)) {
            return;
        }
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, Cons.HOST + token);
        resetSubscriptions();
        registerStompTopic();
        mStompClient.withClientHeartbeat(1000).withServerHeartbeat(1000);
        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Tools.ToastsShort(getApplicationContext(), "连接已开启");
                            break;
                        case ERROR:
                            restart();
                            break;
                        case CLOSED:
                            Tools.ToastsShort(getApplicationContext(), "连接关闭正在重启");
                            Message message = handler.obtainMessage(2);     // Message
                            handler.sendMessageDelayed(message, MIN_CLICK_DELAY_TIME);
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            restart();
                            break;
                    }
                });
        compositeDisposable.add(dispLifecycle);
        mStompClient.connect(headers);
    }

    private void restart() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {// 两次点击的时间间隔大于最小限制时间，则触发点击事件
            lastClickTime = currentTime;
            Tools.ToastsShort(getApplicationContext(), "连接出错 请稍等正在重新连接...");
            colseStompClient();
            resetSubscriptions();
            socket();
        }
    }

    //订阅消息
    private void registerStompTopic() {
        Disposable dispTopic2 = mStompClient.topic("/user/queue/notifications")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
//                    if (textToSpeechCommon == null) {
//                        textToSpeechCommon = new TextToSpeechCommon(getApplicationContext());
//                    }
                    Gson gson1 = new Gson();
                    NotificationRead notificationRead = gson1.fromJson(topicMessage.getPayload(), NotificationRead.class);
//                    textToSpeechCommon.speech(notificationRead.getContent());
//                    BasicData basicData =new BasicData();
//                    basicData.setCode(Tools.code().get("tos"));
//                    Message message = handler.obtainMessage(3);     // Message
//                    message.obj=gson1.toJson(basicData);
//                    handler.sendMessage(message);
                }, throwable -> {
                    restart();
                });
        compositeDisposable.add(dispTopic2);
    }
    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }


    protected CompletableTransformer applySchedulers() {
        return upstream -> upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void loictionSend(String cont) {
        if (time > 6) {
            time = 0;
            sendEchoViaStomp("/app/v1/GpsTracking", cont);
        }else {
            time++;
        }

    }
    private void tasksCount() {
        if (time1==0||time1 > 30) {
            time1 = 0;
            Map<String ,String> map =new HashMap<>();
            String name = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
            map.put("phone_no",name);
            API.grabTasksCount(map, getApplicationContext(), new Okhttp.BasicsBack() {
                @Override
                public void onFalia(String errst) {

                }

                @Override
                public void onsuccess(String object) {
                    BasicData basicData =new BasicData();
                    basicData.setCode(Tools.code().get("grabTasksCount"));
                    basicData.setObject(object);
                    Gson gson =new Gson();
                    Message message = handler.obtainMessage(3);     // Message
                    message.obj=gson.toJson(basicData);
                    handler.sendMessage(message);
                }
            });
        }else {
            time1++;
        }

    }

    /**
     * 发送消息
     *
     * @param name /app/hello2 发送的地址
     * @param cont 发送的内容 json 字符串
     */
    public void sendEchoViaStomp(String name, String cont) {
        if (!mStompClient.isConnected()) return;
        compositeDisposable.add(mStompClient.send(name, cont)
                .compose(applySchedulers())
                .subscribe(() -> {
                }, throwable -> {
                    restart();
                }));

    }

    private void dot(String st) {
        if (Tools.isEmpty(st)) {
            return;
        }
        String ids = (String) SharedPreferencesUtil.getParam(Cons.VEH_TASK_ID, "");
        if (Tools.isEmpty(ids)) {
            return;
        }
        if (!Cons.veh_task_id.equals(ids)) {
            return;
        }
        String data = (String) SharedPreferencesUtil.getParam(ids, "");
        if (Tools.isEmpty(data)) {
            return;
        }
        Gson gson = new Gson();
        Send send = gson.fromJson(st, Send.class);
        List<Routing> routing = gson.fromJson(data, new TypeToken<List<Routing>>() {
        }.getType());
        SendLoction sendLoction = gson.fromJson(send.getContent(), SendLoction.class);
        String[] st1 = sendLoction.getGps_string().split(",");
        LatLng latLng = new LatLng(Double.valueOf(st1[0]), Double.valueOf(st1[1]));
        // 行驶距离 在定位时间间隔行驶的距离
        float ds = Cons.maxCarPerHour / 3600 * (Cons.locationTime+5);
        for (int s = 0; s < routing.size(); s++) {
            LatLng latLng2 = new LatLng(Double.valueOf(routing.get(s).getLocation().getLng()), Double.valueOf(routing.get(s).getLocation().getLat()));
            float distance = AMapUtils.calculateLineDistance(latLng, latLng2);//计算两点之间的距离
            if (distance < ds && !routing.get(s).isDot()) {
//                if (textToSpeechCommon == null) {
//                    textToSpeechCommon = new TextToSpeechCommon(getApplicationContext());
//                }
                routing.get(s).setDot(true);
                SharedPreferencesUtil.setParam(Cons.veh_task_id, gson.toJson(routing));
//                textToSpeechCommon.speech(routing.get(s).getName());
                String st2 = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
                if (!Tools.isEmpty(st2)) {
                    SendLoction sendLoction1 = new SendLoction();
                    Send send1 = new Send();
                    sendLoction1.setGps_string(routing.get(s).getLocation().getLng() + "," + routing.get(s).getLocation().getLng());
                    sendLoction1.setName(st2);
                    sendLoction1.setReport_time(Tools.timeForma());
                    sendLoction1.setRemark("到达：" + routing.get(s).getName() + "打点");
                    send1.setCode(String.valueOf(Tools.code().get("location")));
                    send1.setContent(gson.toJson(sendLoction));
                    sendEchoViaStomp("/app/v1/GpsTracking", gson.toJson(send));
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void colseStompClient() {
        if (mStompClient != null) mStompClient.disconnect();
    }

    @Override
    public void onDestroy() {
        if (mReceiverTag) {   //判断广播是否注册
            mReceiverTag = false;   //Tag值 赋值为false 表示该广播已被注销
            unregisterReceiver(broadcastReceiver);
        }
        colseStompClient();
        if (compositeDisposable != null) compositeDisposable.dispose();
        super.onDestroy();
    }

}
