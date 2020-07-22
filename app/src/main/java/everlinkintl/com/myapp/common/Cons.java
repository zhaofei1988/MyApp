package everlinkintl.com.myapp.common;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Environment;

public class Cons {

    public final static String url = "http://www.everlinkintl.com:8081";
    public static final String RECEIVER_ACTION = "android.intent.action.MY_BROADCAST";
    public static final String RECEIVER_ACTION_SERVER = "android.intent.action.MY_BROADCAST_SERVICE";
    public static final String HOST = url+"/webSocketEndPoint/websocket?token=";
    public static final String RECEIVER_PUT_RSULT ="result";
    public static final String RECEIVER_PUT_FRAGMENT ="frament";
    public static final String  sdPath = Environment.getExternalStorageDirectory().getPath()+"/everlinkintl";
    public static final int  locationTime = 10;//定位时间间隔
    public static final int  maxCarPerHour = 100000;//车的时速
    public static final String  downUll =url+ "/statics/app/version.txt";
    /**
     * 写入权限的请求code,提示语，和权限码
     */
    public static final String[] PERMS_WRITE ={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };
    // 打开相机service
    public final static int REQUEST_CAMERA = 111;
    public final static int PHOTO_BLBUM = 112;
    public static final String  SHARED_PREFERENCES_FILE_NAME = "everlinkintl_Shared_Preferences";
    public static final String  EVERLINKINT_LOGIN_SP = "everlinkintl_login_sp";
    public static final String  VEH_TASK_ID= "veh_task_id";
    public static final String  CLAI_DATA= "clai_data";
    public static String  veh_task_id= "";
    public static final String  EVERLINKINT_LOGIN_NAME = "everlinkintl_login_name";
    public static final String  PHONE = "phone";
    /**
     * 密码是否正确
     */
    public static final String PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    /**
     * 匹配手机号码的正则表达式
     * <br>支持130——139、150——153、155——159、170、171、172、176、177、178/180-189号段
     */
    public static final String PHONE_NUMBER_REGEX = "^1{1}(3{1}\\d{1}|5{1}[012356789]{1}|8{1}[0123456789]{1}|7{1}[0123678]{1})\\d{8}$";
    // 验证码
    public static final String CHECK_CODE="^\\d{6}$";

    //log设置
    public static final boolean IS_LOG_SHOW = true;
    public static final String VISE_LOG ="everlinkintl.com.myapp";
    public static final String FORMAT_TAG="%d{HH:mm:ss:SSS} %t %c{-5}";
    public static SharedPreferences sp;

}
