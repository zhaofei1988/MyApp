package everlinkintl.com.myapp.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyFragment;
import everlinkintl.com.myapp.adapter.QiangdanAdapter;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.common.TransTypeDataPackage;
import everlinkintl.com.myapp.datatemplate.BasicData;
import everlinkintl.com.myapp.datatemplate.QiangDanData;
import everlinkintl.com.myapp.datatemplate.TransTypeData;
import everlinkintl.com.myapp.http.API;
import everlinkintl.com.myapp.http.Okhttp;
import everlinkintl.com.myapp.newdata.GrabTasksCountData;

public class FragmentQiangdan extends MyFragment {
    @BindString(R.string.qiangdan)
    String qiangdan;
    @BindView(R.id.qianghdan_list)
    ListView listView;

    List<TransTypeData> list;
    @BindView(R.id.qiandan_list_view)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    QiangdanAdapter qiangdanAdapter;
    List<QiangDanData> list2 = new ArrayList();
    int page = 0;
    @Override
    protected int getContentLayoutId() {
        return R.layout.tab_qiangdan_layout;
    }

    @Override
    protected void setData(String s) {
    }
    Handler handler = new Handler() {
        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    page = 0;
                    list2.clear();
                    ptrClassicFrameLayout.loadMoreComplete(true);
                    getList(true, false);
                    break;
                case 2:
                    getList(false, false);
                    break;
                case 3:
                    page = 0;
                    list2.clear();
                    getList(true, true);
                    break;
            }
        }
    };
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitleName(qiangdan);
        setGoneBreak();
        TransTypeDataPackage transTypeDataPackage = new TransTypeDataPackage();
        list = transTypeDataPackage.packageData();
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override

            public void onRefreshBegin(PtrFrameLayout frame) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);

            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                Message message = Message.obtain();
                message.what = 2;
                handler.sendMessage(message);
            }

        });
        Message message = Message.obtain();
        message.what = 3;
        handler.sendMessage(message);
        super.onResume();
        qiangdanAdapter = new QiangdanAdapter(getContext(), FragmentQiangdan.this);
        listView.setAdapter(qiangdanAdapter);
    }
    private void getList(boolean isUpData,boolean isInitial){
        Map<String, String> map = new HashMap<>();
        String name = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
        map.put("page", String.valueOf(page));
        map.put("page_size", String.valueOf(10));
        map.put("name", name);
        API.listGrabTasks(map, getActivity(), isInitial, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {
                    ptrClassicFrameLayout.refreshComplete();
                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                    ptrClassicFrameLayout.loadMoreComplete(true);
            }

            @Override
            public void onsuccess(String object) {
                if (isUpData) {
                    upData(object);
                } else {
                    extraDataLoading(object);
                }

            }
        });
    }
    private void extraDataLoading(String object) {
        page++;
        List<QiangDanData> listData=  data(object);
        if(listData!=null&&listData.size()>0){
            list2.addAll(listData);
            qiangdanAdapter.setData(list2);
            qiangdanAdapter.notifyDataSetChanged();
            ptrClassicFrameLayout.loadMoreComplete(true);
        }else {
            ptrClassicFrameLayout.loadMoreComplete(true);
            ptrClassicFrameLayout.setLoadMoreEnable(false);
        }
    }

    private void upData(String object) {
        List<QiangDanData> listData=  data(object);
        if(listData!=null&&listData.size()>0){
            if(list2!=null&&list2.size()==0){
                page++;
            }
            list2.addAll(listData);
            qiangdanAdapter.setData(listData);
            qiangdanAdapter.notifyDataSetChanged();
        }else {
            Tools.ToastsShort(getContext(),"没有可以抢的任务");
        }
        tasksCount();
        ptrClassicFrameLayout.refreshComplete();
        ptrClassicFrameLayout.setLoadMoreEnable(true);

    }
    private  List<QiangDanData>  data(String st ){
        Gson gson = new Gson();
        List<QiangDanData> list3 = new ArrayList();
        List<QiangDanData> list1 = gson.fromJson(st, new TypeToken<List<QiangDanData>>() {
        }.getType());
        boolean isJ;
        for (int s = 0; s < list.size(); s++) {
            isJ = true;
            for (int d = 0; d < list1.size(); d++) {
                if (list.get(s).getTransType() == list1.get(d).getTrans_type()) {
                    QiangDanData qiangDanData1;
                    if (isJ) {
                        qiangDanData1 = new QiangDanData();
                        qiangDanData1.setTitle_name(list.get(s).getTransTypeName());
                        list3.add(qiangDanData1);
                        isJ = false;
                        qiangDanData1 = new QiangDanData();
                        qiangDanData1.setRemark(list1.get(d).getRemark());
                        qiangDanData1.setRoute_name(list1.get(d).getRoute_name());
                        qiangDanData1.setTrans_no(list1.get(d).getTrans_no());
                        qiangDanData1.setTrans_type(list1.get(d).getTrans_type());
                        qiangDanData1.setVeh_task_id(list1.get(d).getVeh_task_id());
                        list3.add(qiangDanData1);
                    } else {
                        qiangDanData1 = new QiangDanData();
                        qiangDanData1.setRemark(list1.get(d).getRemark());
                        qiangDanData1.setRoute_name(list1.get(d).getRoute_name());
                        qiangDanData1.setTrans_no(list1.get(d).getTrans_no());
                        qiangDanData1.setTrans_type(list1.get(d).getTrans_type());
                        qiangDanData1.setVeh_task_id(list1.get(d).getVeh_task_id());
                        list3.add(qiangDanData1);
                    }

                }
            }
        }
        return list3;
    }
    private void tasksCount() {
            Map<String ,String> map =new HashMap<>();
            String name = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
            map.put("phone_no",name);
            API.grabTasksCount(map, getContext(), new Okhttp.BasicsBack() {
                @Override
                public void onFalia(String errst) {

                }

                @Override
                public void onsuccess(String object) {
                    BasicData basicData =new BasicData();
                    basicData.setCode(Tools.code().get("grabTasksCount"));
                    basicData.setObject(object);
                    Gson gson =new Gson();
                    Intent intent = new Intent(Cons.RECEIVER_ACTION);
                    intent.putExtra(Cons.RECEIVER_PUT_RSULT, gson.toJson(basicData));
                    getContext().sendBroadcast(intent);
                }
            });
    }
    public void dialogHttp(QiangDanData qiangDanData){
        ViseLog.e(qiangDanData);
        Map<String ,String> map =new HashMap<>();
        map.put("task_id",String.valueOf(qiangDanData.getVeh_task_id()));
        API.grabTasks(map, getActivity(), true, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {
                ViseLog.e(errst);
            }

            @Override
            public void onsuccess(String object) {
                Gson gson =new Gson();
                GrabTasksCountData grabTasksCountData =gson.fromJson(object,GrabTasksCountData.class);
                if(grabTasksCountData.getContent().equals("OK")){
                    Tools.ToastsShort(getContext(),"抢单成功,请到任务列表开启任务！！");
                    Message message = Message.obtain();
                    message.what = 3;
                    handler.sendMessage(message);
                }else {
                    Tools.ToastsShort(getContext(),grabTasksCountData.getContent());
                }

            }
        });
    }
}
