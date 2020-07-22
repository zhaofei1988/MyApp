package everlinkintl.com.myapp.activity.transport;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.NavigateArrowOptions;
import java.util.ArrayList;
import butterknife.BindString;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;

public class TrackMapActivity extends MyBsetActivity {
    @BindString(R.string.task_track)
    String taskTrack;
    private MapView mapView;
    private AMap aMap;
    private double[][] coords ={{31.477741,120.966435},{31.468088,120.97226},{31.459412,120.974535},{31.452091,120.97741},{31.441864,120.978784}};
    @Override
    protected int getContentLayoutId() {
        return R.layout.track_map_layout;
    }

    @Override
    protected void setData(String string) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = (MapView)findViewById(R.id.track_map_view);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        setTitleName(taskTrack);
        init();
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
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
            addMarkersToMap();
        }
    }
    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        // 设置标记点
        NavigateArrowOptions navigateArrowOptions = new NavigateArrowOptions();
        ArrayList<MarkerOptions> listOptions =new ArrayList<MarkerOptions>();
        for (int s=0;s<coords.length;s++){
            navigateArrowOptions.add(new LatLng(coords[s][0],coords[s][1]));
            MarkerOptions markerOptions =  new MarkerOptions();
            markerOptions.position(new LatLng(coords[s][0],coords[s][1]));
            markerOptions.draggable(true);
            listOptions.add(markerOptions);
        }
        final LatLng latlng = new LatLng(coords[0][0], coords[0][1]);
        aMap.addMarkers(listOptions,true);
        navigateArrowOptions.width(15);
        aMap.addNavigateArrow(navigateArrowOptions);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng,13,0,0)));
            }
        }, 1000);   //延迟秒
    }
}
