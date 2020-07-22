package everlinkintl.com.myapp.http;

import android.app.Activity;
import android.content.Context;

import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.Map;

import everlinkintl.com.myapp.activity.transport.ClaimingExpensesActivity;

/***   API 函数调用
 *             Map <String ,String>  map= new HashMap<>();
 *         API.loging(map,this.getApplicationContext(),new Okhttp.Objectcallback() {
 *             @Override
 *             public void onsuccess(Object object) {
 *
 *             }
 *
 *             @Override
 *             public void fileOnsuccess(Object object) {
 *
 *             }
 *
 *             @Override
 *             public void onFalia(int code, String errst) {
 *
 *             }
 *
 *             @Override
 *             public void downLoadInProgress(float progress, long total) {
 *
 *             }
 *
 *             @Override
 *             public void downLoadOnsuccess(File file) {
 *
 *             }
 *         });
 */
public class API {
    //登陆
    public static void loging(Map<String,String> map, Activity activity, Okhttp.BasicsBack handler){
       Okhttp.post("/login",map,activity,true,handler);
    }
    //校验token
    public static void checkoutToken(Map<String,String> map,  Activity activity, Okhttp.BasicsBack handler ){
        Okhttp.get("/users/current",map,activity,false,handler);
    }
    //获取任务列表
    public static void grabTasksCount(Map<String,String> map,  Context context,Okhttp.BasicsBack handler ){
        Okhttp.postNoLoding("/v1/VehTasks/grab_tasks_count",map,context,handler);
    }
    //获取单子数量
    public static void list(Map<String,String> map,  Activity activity, boolean isLoding ,Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/VehTasks/list",map,activity,isLoding,handler);
    }
    //抢单列表
    public static void listGrabTasks(Map<String,String> map,  Activity activity, boolean isLoding ,Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/VehTasks/list_grab_tasks",map,activity,isLoding,handler);
    }
    //抢单
    public static void grabTasks(Map<String,String> map,  Activity activity, boolean isLoding ,Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/VehTasks/grab_task",map,activity,isLoding,handler);
    }
    //获取任务详细
    public static void receivesAssignments(Map<String,String> map,  Activity activity, Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/VehTasks/accept_task",map,activity,true,handler);
    }
    public static void receiveSendTask(Map<String,String> map,  Activity activity, Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/VehTasks/end_task",map,activity,false,handler);
    }
    //获取任务详细
    public static void listDetale(Map<String,String> map,  Activity activity, boolean isres, Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/VehTasks/veh_task_id",map,activity,isres,handler);
    }
    //上传照片
    public static void addFile(Map<String,String> map,String fileUrl, Activity activity, Okhttp.FileBack handler ){
        Okhttp.postFile("/v1/BizAction/add",map,fileUrl,activity,true,handler);
    }
    public static void add(Map<String,String> map,  Activity activity,  Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/BizAction/add1",map,activity,true,handler);
    }
    public static void getButtonData(Map<String,String> map,  Activity activity,  Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/LocAction/list",map,activity,true,handler);
    }
    public static void getListActionAllCode(Map<String,String> map, Activity activity, Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/BizJobAction/listActionAllCode",map,activity,true,handler);
    }
    public static void getListBizActionValue(Map<String,String> map,  Activity activity,  Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/BizJobAction/listBizActionValue",map,activity,true,handler);
    }
    public static void getUpdateBizJobActionValue(Object object,  Activity activity,  Okhttp.BasicsBack handler ){
        Okhttp.post("/v1/BizJobAction/updateBizJobActionValue",object,activity,true,handler);
    }
    public static void getVechicleTaskSeq(Map<String,String> map,  Activity activity,  Okhttp.BasicsBack handler ){
        Okhttp.get("/wh/get_vechicle_task_seq_for_mysql",map,activity,true,handler);
    }
    public static void getVechicleTask(Map<String,String> map,  Activity activity,  Okhttp.BasicsBack handler ){
        Okhttp.get("/wh/get_vechicle_task",map,activity,true,handler);
    }
//    public static void location(Map<String,String> map, Context context, Okhttp.BasicsBack handler ){
//        Okhttp.post("api/location",map,context,handler);
//    }


}
