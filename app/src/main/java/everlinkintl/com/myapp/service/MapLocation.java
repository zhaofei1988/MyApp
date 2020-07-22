package everlinkintl.com.myapp.service;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.google.gson.Gson;
import com.vise.log.ViseLog;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.DialogUtile;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.BasicData;
import everlinkintl.com.myapp.datatemplate.Location;
import everlinkintl.com.myapp.datatemplate.LocationData;
import everlinkintl.com.myapp.datatemplate.Send;
import everlinkintl.com.myapp.datatemplate.SendLoction;

/***
 * 高德地图 地位 配置实现类
 */
public class MapLocation {
    private Context mContext;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    public Handler mHandler;
    private boolean dialogIsShow = false;

    public MapLocation(Context applicationContext, Handler handler) {
        this.mContext = applicationContext;
        this.mHandler = handler;
        initLocation();
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.mContext);
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /***
     * 开启定位
     */
    public void startLocation() {
        locationClient.startLocation();
    }

    /**
     * 删除定位
     */
    public void onDestroy() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.stopLocation();
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            Message msg = Message.obtain();
            Location location1 = new Location();
            Gson gson = new Gson();
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    location1.setLng(String.valueOf(location.getLongitude()));
                    location1.setLat(String.valueOf(location.getLatitude()));
                    getGPSStatusString(location.getLocationQualityReport().getGPSStatus());
                    String st = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
                    if(!Tools.isEmpty(st)){
                        SendLoction sendLoction = new SendLoction();
                        Send send =new Send();
                        sendLoction.setGps_string(location1.getLng() + "," + location1.getLat());
                        sendLoction.setName(st);
                        sendLoction.setReport_time(Tools.timeForma());
                        send.setCode(String.valueOf(Tools.code().get("location")));
                        send.setContent(gson.toJson(sendLoction));
                        msg.obj = gson.toJson(send);
                        msg.what = 1;   //标志消息的标志
                        mHandler.sendMessage(msg);
                    }
                } else {
                    locationClient.stopLocation();
                    startLocation();
                }
            } else {
                locationClient.stopLocation();
                startLocation();
            }

        }
    };

    /**
     * 定位的配置信息
     *
     * @return
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(Cons.locationTime * 1000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private void getGPSStatusString(int statusCode) {
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                DialogUtile.dialog(mContext, "GPS关闭，建议开启GPS，提高定位质量", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        mContext.startActivity(intent);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                DialogUtile.dialog(mContext, "没有GPS定位权限，建议开启gps定位权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        intent.setData(uri);
                        mContext.startActivity(intent);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                break;
        }
    }
}
