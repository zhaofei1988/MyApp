package everlinkintl.com.myapp.http;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.vise.log.ViseLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import everlinkintl.com.myapp.activity.user.LoginActivity;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.view.LodingDialog;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/***
 * 封装的http 请求
 * 调用  new Okhttp.Objectcallback()
 * post post（）请求
 * get get（）轻轨
 *
 */

public class Okhttp {
    /***
     * 封装请求头
     * @param context       上下文
     * @return 返回请求头 （Map）
     */

    private static Map<String, String> okhttpHead(Context context) {
        Object loginDatas = SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_SP, "");
        Map<String, String> head = new HashMap<>();
        head.put("versionName", Tools.getLocalVersionName(context));
        head.put("versionCode", String.valueOf(Tools.getLocalVersion(context)));
        head.put("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        head.put("X-Requested-With", "XMLHttpRequest");
        head.put("Accept-Endoding", "default");
        head.put("token", (String) loginDatas);
        return head;
    }
    /***
     * 封装请求头
     * @param context       上下文
     * @return 返回请求头 （Map）
     */

    private static Map<String, String> fileOkhttpHead(Context context) {
        Object loginDatas = SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_SP, "");
        Map<String, String> head = new HashMap<>();
        head.put("versionName", Tools.getLocalVersionName(context));
        head.put("versionCode", String.valueOf(Tools.getLocalVersion(context)));
        head.put("Content-Disposition", "form-data;filename=enctype");
        head.put("token", (String) loginDatas);
        Tools.ToastsShort(context,"token="+loginDatas);
        return head;
    }
    /**
     * post 请求
     *
     * @param url       接口地址
     * @param paramters 请求参数
     * @param activity  当前activity
     * @param b         请求参数 是否展示loding
     * @param callback  返回数据
     */
    public static void post(String url, Map<String, String> paramters,
                            Activity activity, boolean b, final BasicsBack callback) {
        String urls = Cons.url + url;
        Map<String, String> head = okhttpHead(activity);
        AlertDialog alertDialog = showLoding(activity, b);
        ViseLog.e(urls);
        ViseLog.e(paramters);
        try {
            OkHttpUtils.post()
                    .url(urls)
                    .params(paramters)
                    .headers(head)
                    .build()
                    .connTimeOut(5000)
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                            callback.onFalia(e.toString());
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }
                            Tools.ToastsShort(activity, e.toString());
                            if(e.toString().indexOf("401")>-1){
                                Intent intent   = new Intent(activity, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                activity.startActivity(intent);
                            }
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            callback.onsuccess(response);
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }

                        }
                    });
        } catch (Exception e) {
            Tools.ToastsShort(activity, e.toString());
            e.printStackTrace();
        }
    }
    /**
     * post 请求
     *
     * @param url       接口地址
     * @param activity  当前activity
     * @param b         请求参数 是否展示loding
     * @param callback  返回数据
     */

    /**
     * post 请求
     *
     * @param url       接口地址
     * @paramObject   请求参数
     * @param activity  当前activity
     * @param b         请求参数 是否展示loding
     * @param callback  返回数据
     */
    public static void post(String url, Object object,
                            Activity activity, boolean b, final BasicsBack callback) {
        String urls = Cons.url + url;
        Map<String, String> head = okhttpHead(activity.getApplicationContext());
        AlertDialog alertDialog = showLoding(activity, b);
        ViseLog.e(urls);
        ViseLog.e(new Gson().toJson(object));
        try {
            OkHttpUtils.postString()
                    .url(urls)
                    .content(new Gson().toJson(object))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .headers(head)
                    .build()
                    .connTimeOut(5000)
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                            callback.onFalia(e.toString());
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }
                            Tools.ToastsShort(activity, e.toString());
                            if(e.toString().indexOf("401")>-1){
                                Intent intent   = new Intent(activity, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                activity.startActivity(intent);
                            }
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            callback.onsuccess(response);
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }

                        }
                    });
        } catch (Exception e) {
            Tools.ToastsShort(activity, e.toString());
            e.printStackTrace();
        }
    }
    /**
     * post 请求
     *
     * @param url       接口地址
     * @param paramters 请求参数
     * @param callback  返回数据
     */
    public static void postNoLoding(String url, Map<String, String> paramters, Context context, final BasicsBack callback) {
        String urls = Cons.url + url;
        Map<String, String> head = okhttpHead(context);
        try {
            OkHttpUtils.post()
                    .url(urls)
                    .params(paramters)
                    .headers(head)
                    .build()
                    .connTimeOut(5000)
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                            callback.onFalia(e.toString());

                            Tools.ToastsShort(context, e.toString());
                            if(e.toString().indexOf("401")>-1){
                                Intent intent   = new Intent(context, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                context.startActivity(intent);
                            }
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            callback.onsuccess(response);


                        }
                    });
        } catch (Exception e) {
            Tools.ToastsShort(context, e.toString());
            e.printStackTrace();ViseLog.e(e.toString());
        }
    }
    /**
     * get 请求
     *
     * @param url       接口地址
     * @param paramters 请求参数
     * @param activity  当前activity
     * @param b         请求参数 是否展示loding
     * @param callback  返回数据
     */
    public static void get(String url, Map<String, String> paramters,
                           Activity activity, boolean b, final BasicsBack callback) {
        String urls = Cons.url + url;
        Map<String, String> head = okhttpHead(activity);
        AlertDialog alertDialog = showLoding(activity, b);
        try {
            OkHttpUtils
                    .get()
                    .url(urls)
                    .params(paramters)
                    .headers(head)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            callback.onFalia(e.toString());
                            Tools.ToastsShort(activity, e.toString());
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }
                            if(e.toString().indexOf("401")>-1){
                                Intent intent   = new Intent(activity, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                activity.startActivity(intent);
                            }
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            callback.onsuccess(response);
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }

                        }
                    });
        } catch (Exception e) {
            Tools.ToastsShort(activity, e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @param url
     * @param callback
     */
    public static void postFile(final String url, final Map<String, String> params, String urlFile, Activity activity, boolean b, final FileBack callback) {
        String urls = Cons.url + url;
        Map<String, String> head = fileOkhttpHead(activity);
        AlertDialog alertDialog = showLoding(activity, b);
        try {
            File file = new File(urlFile);
            if (!file.exists()) {
                return;
            }
            String filename = file.getName();
            OkHttpUtils.post()
                    .url(urls)
                    .headers(head)
                    .params(params)
                    .addFile("file", filename, file)
                    .build()
                    .execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Tools.ToastsShort(activity, e.toString());
                    callback.onFalia(id, e.toString());
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    callback.fileOnsuccess(response);
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            Tools.ToastsShort(activity.getApplicationContext(), e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param url         接口地址
     * @param callback    返回数据
     */
    public static void getFile(String url,  Activity activity, final DownLoadBack callback) {
        Map<String, String> head = okhttpHead(activity.getApplicationContext());
        String spStr[] = url.split("/");
        String fileName = spStr[spStr.length-1];
        try {
            OkHttpUtils
                    .get()
                    .url(url)
                    .headers(head)
                    .build()
                    .execute(new FileCallBack(Cons.sdPath, fileName) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Tools.ToastsShort(activity, e.toString());
                            callback.onFalia(id, e.toString());
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            callback.downLoadInProgress(progress, total);
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            callback.downLoadOnsuccess(response,Cons.sdPath+"/"+fileName);
                        }
                    });
        } catch (Exception e) {
            Tools.ToastsShort(activity.getApplicationContext(), e.toString());
            e.printStackTrace();
        }
    }

    private static AlertDialog showLoding(Activity activity, boolean b) {
        AlertDialog alertDialog = null;
        if (b) {
            alertDialog = LodingDialog.dialogIndex(activity);
        }
        return alertDialog;
    }

    //    private static void closeLoding(boolean b){
//        if(b==true){
//            LodingDialog.dismiss();
//        }
//    }
    public interface BasicsBack {
        void onFalia(String errst);

        void onsuccess(String object);
    }

    public interface FileBack {
        void onFalia(int code, String errst);

        void fileOnsuccess(Object object);
    }

    public interface DownLoadBack {
        void onFalia(int code, String errst);

        void downLoadInProgress(float progress, long total);

        void downLoadOnsuccess(File file,String pars);
    }
}
