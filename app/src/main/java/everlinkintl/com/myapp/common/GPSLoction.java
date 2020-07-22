package everlinkintl.com.myapp.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.CoordinateConverter.CoordType;

public class GPSLoction {
    /**
     * 获取具体位置的经纬度
     */
    public LatLng getLocation(Activity activity) {
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) activity.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Tools.ToastsShort(activity.getApplicationContext(), "请到设置里面打开GPS");
            return null;
        }
        Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
        LatLng latLng=  fromgpstoamap(location,activity.getApplicationContext());
        return latLng;
    }

    public LatLng fromgpstoamap(Location location, Context context) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CoordinateConverter converter = new CoordinateConverter(context);
        // CoordType.GPS 待转换坐标类型
        converter.from(CoordType.GPS);
        // 转换
        converter.coord(latLng);
        // 获取转换之后的高德坐标
        LatLng result = converter.convert();

        return result;
    }
}
