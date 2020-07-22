package everlinkintl.com.myapp.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyFragment;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.GPSLoction;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.newdata.Routing;
import everlinkintl.com.myapp.service.MapLocation;

public class FragmentSecond extends MyFragment implements
        AMap.OnMarkerClickListener {
    private MapView mapView;
    private AMap aMap;
    @BindString(R.string.location_submit)
    String locationSubmit;
    private static final int TIMER = 999;
    private static boolean flag = true;
    List<Marker> li;
    Marker marker;
    @Override
    protected int getContentLayoutId() {
        return R.layout.tab_second_layout;
    }

    @Override
    protected void setData(String s) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.tab_map);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        setTitleName(locationSubmit);
        setGoneBreak();
        init();
    }

    private void setTimer() {
        Message message = mHandler.obtainMessage(TIMER);     // Message
        mHandler.sendMessageDelayed(message, 2000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMER:
                    //在这里去执行定时操作逻辑
                    if (flag) {
                        markData();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void stopTimer() {
        flag = false;
    }

    @Override
    public void onStart() {

        //http请求
        super.onStart();
    }

    private void markData() {
        String ids = (String) SharedPreferencesUtil.getParam(Cons.VEH_TASK_ID, "");
        if (!Tools.isEmpty(ids) && Cons.veh_task_id.equals(ids)) {
            String data = (String) SharedPreferencesUtil.getParam(ids, "");

            if (!Tools.isEmpty(data)) {
                Gson gson = new Gson();
                List<Routing> routing = gson.fromJson(data, new TypeToken<List<Routing>>() {
                }.getType());
                stopTimer();
                addMarkersToMap(routing);
            } else {
                setTimer();
            }
        } else {
            setTimer();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        flag = true;
        setTimer();
        mapView.onResume();
        super.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {

        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
            MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.interval(1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
            myLocationStyle.showMyLocation(true);
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        }
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(List<Routing> routing) {

        ArrayList<MarkerOptions> listOptions = new ArrayList<MarkerOptions>();
        for (int s = 0; s < routing.size(); s++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Double.valueOf(routing.get(s).getLocation().getLat()), Double.valueOf(routing.get(s).getLocation().getLng())));
            try {
                if (!Tools.isEmpty(routing.get(s).getAddress())) {
                    String st = routing.get(s).getAddress();
                    StringBuilder str = new StringBuilder();
                    char[] c = st.toCharArray();
                    int o = 0;
                    for (int w = 0; w < c.length; w++) {
                        o++;
                        if (o == 14) {
                            o = 0;
                            str.append(c[w] + "\n");
                        } else {
                            str.append(c[w]);
                        }
                    }
                    markerOptions.title(str.toString());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            markerOptions.draggable(true);
            markerOptions.visible(true);
            listOptions.add(markerOptions);
            marker= aMap.addMarker(markerOptions);
        }
        List<Marker> li = aMap.addMarkers(listOptions, true);
        for (int s = 0; s < li.size(); s++) {
            li.get(s).showInfoWindow();
        }


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // TODO Auto-generated method stub
        return false;
    }
}
