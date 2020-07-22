package everlinkintl.com.myapp.activity.transport;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.TaskDetailsAdapter;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.Task;
import everlinkintl.com.myapp.http.API;
import everlinkintl.com.myapp.http.Okhttp;
import everlinkintl.com.myapp.newdata.AcceptAssignmentBack;
import everlinkintl.com.myapp.newdata.BasicDataNew;
import everlinkintl.com.myapp.newdata.ButtonData;
import everlinkintl.com.myapp.newdata.Content;
import everlinkintl.com.myapp.newdata.NewTaskDetailsData;
import everlinkintl.com.myapp.datatemplate.TackDetailsData;

public class TaskDetailsActivity extends MyBsetActivity {

    @BindView(R.id.task_details_list)
    public ListView listView;
    @BindView(R.id.assignments)
    public Button assignments;

    @BindView(R.id.end_task)
    public LinearLayout endTask;
    @BindView(R.id.task_details_num)
    public TextView taskDetailsNum;
    @BindView(R.id.task_details_status)
    public TextView taskDetailsStatus;

    @BindView(R.id.bottom_button)
    public RelativeLayout relativeLayout;
    @BindView(R.id.button_layout)
    public LinearLayout buttonLayout;

    @BindView(R.id.list_frameLayout)
    public PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.list_view_null)
    public TextView listViewNull;
    @BindView(R.id.button_title_layout)
    public LinearLayout buttonTitleLayout;

    TaskDetailsAdapter taskDetailsAdapter;
    @BindString(R.string.task_detail)
    String taskDetail;
    @BindString(R.string.task_track)
    String taskTrack;
    String[] erm = {"PICKUP_ADDR", "WH_FRM_ADDR", "WH_TO_ADDR", "DELIVERY_ADDR"};
    String type = "";
    Task task;
    int isHasUnderway;
    Content content;
    String status = "状态：";
    String strTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(taskDetail);
        setTitleRigthTvShow(taskTrack, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        Gson gson = new Gson();
        strTask = (String) getIntent().getExtras().get("task");
        task = gson.fromJson(strTask, Task.class);
        isHasUnderway = (int) getIntent().getExtras().get("isHasUnderway");
        if (task.getStatus() == 1) {
            taskDetailsStatus.setText(status + "未开始");
            assignments.setVisibility(View.VISIBLE);
            endTask.setVisibility(View.GONE);
            buttonTitleLayout.setVisibility(View.GONE);
        } else if (task.getStatus() == 2) {
            taskDetailsStatus.setText(status + "进行中");
            assignments.setVisibility(View.GONE);
            endTask.setVisibility(View.VISIBLE);
            buttonTitleLayout.setVisibility(View.VISIBLE);
        }
        getHttpList(String.valueOf(task.getVeh_task_id()), true);
        ptrClassicFrameLayout.setLoadMoreEnable(false);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getHttpList(String.valueOf(task.getVeh_task_id()), false);
            }
        });
    }


    private void viewData(String bn) {
        Gson gson = new Gson();
        BasicDataNew basicDataNew = gson.fromJson(bn, BasicDataNew.class);
        String strContent = basicDataNew.getContent().replaceAll("\\\\", "");
        content = gson.fromJson(strContent, Content.class);
        taskDetailsNum.setText(content.getTrans_no().trim());
        if(content.getBiz_no_list()!=null&&content.getBiz_no_list().size()==0 ){
            listView.setVisibility(View.GONE);
            ptrClassicFrameLayout.setVisibility(View.GONE);
            listViewNull.setVisibility(View.VISIBLE);
            return;
        }
        listView.setVisibility(View.VISIBLE);
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        listViewNull.setVisibility(View.GONE);
        List<TackDetailsData> list = new ArrayList<>();
        List<NewTaskDetailsData> list1 = new ArrayList<>();
        for (int q = 0; q < erm.length; q++) {
            for (int w = 0; w < content.getBiz_no_list().size(); w++) {
                for (int e = 0; e < content.getBiz_no_list().get(w).getPois().size(); e++) {
                    if (content.getBiz_no_list().get(w).getPois().get(e).getPointType().equals(erm[q])) {
                        NewTaskDetailsData newTaskDetailsData = new NewTaskDetailsData();
                        newTaskDetailsData.setAddress(content.getBiz_no_list().get(w).getPois().get(e).getAddress());
                        newTaskDetailsData.setDesc(content.getBiz_no_list().get(w).getPois().get(e).getDesc());
                        newTaskDetailsData.setId(content.getBiz_no_list().get(w).getPois().get(e).getId());
                        newTaskDetailsData.setName(content.getBiz_no_list().get(w).getPois().get(e).getName());
                        newTaskDetailsData.setRemark(content.getBiz_no_list().get(w).getPois().get(e).getRemark());
                        newTaskDetailsData.setPointType(content.getBiz_no_list().get(w).getPois().get(e).getPointType());
                        newTaskDetailsData.setLocation(content.getBiz_no_list().get(w).getPois().get(e).getLocation());
                        newTaskDetailsData.setBiz_no(content.getBiz_no_list().get(w).getBiz_no());
                        newTaskDetailsData.setGross_wt(content.getBiz_no_list().get(w).getGross_wt());
                        newTaskDetailsData.setHawb_no(content.getBiz_no_list().get(w).getHawb_no());
                        newTaskDetailsData.setMawb_no(content.getBiz_no_list().get(w).getMawb_no());
                        newTaskDetailsData.setD_decl_date(content.getBiz_no_list().get(w).getD_decl_date());
                        newTaskDetailsData.setPackage_info(content.getBiz_no_list().get(w).getPackage_info());
                        newTaskDetailsData.setSeq(content.getBiz_no_list().get(w).getSeq());
                        list1.add(newTaskDetailsData);
                    }
                }
            }
        }

        String type = "";
        for (int s = 0; s < list1.size(); s++) {
            if (!type.equals(list1.get(s).getPointType())) {
                TackDetailsData tackDetailsData = new TackDetailsData();
                tackDetailsData.setHead(list1.get(s).getDesc());
                tackDetailsData.setType(list1.get(s).getPointType());
                list.add(tackDetailsData);
                TackDetailsData tackDetailsData1 = new TackDetailsData();
                tackDetailsData1.setBody(list1.get(s).getName());
                tackDetailsData1.setType(list1.get(s).getPointType());
                tackDetailsData1.setAddress(list1.get(s).getAddress());
                tackDetailsData1.setNewTaskDetailsData(list1.get(s));
                tackDetailsData1.setSelected(false);
                list.add(tackDetailsData1);
            } else {
                TackDetailsData tackDetailsData = new TackDetailsData();
                tackDetailsData.setBody(list1.get(s).getName());
                tackDetailsData.setType(list1.get(s).getPointType());
                tackDetailsData.setAddress(list1.get(s).getAddress());
                tackDetailsData.setNewTaskDetailsData(list1.get(s));
                tackDetailsData.setSelected(false);
                list.add(tackDetailsData);
            }

            type = list1.get(s).getPointType();
        }

        taskDetailsAdapter = new TaskDetailsAdapter(getApplicationContext(), TaskDetailsActivity.this);
        taskDetailsAdapter.setData(list);
        taskDetailsAdapter.status(task.getStatus());
        listView.setAdapter(taskDetailsAdapter);
        taskDetailsAdapter.notifyDataSetChanged();

    }

    @OnClick({R.id.assignments,R.id.assignments_end,R.id.task_details_money,R.id.task_details_sealing_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.assignments:
                if (isHasUnderway == 2) {
                    Tools.ToastsShort(getApplicationContext(), "还有正在做的任务，请结束正在做的任务");
                    return;
                }
                receivesAssignments();
                break;
            case R.id.assignments_end:
                receivesSendTask();
                break;
            case R.id.task_details_money:
                Intent intent =new Intent(getApplicationContext(),ClaimingExpensesActivity.class);
                intent.putExtra("task",strTask);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                getApplicationContext().startActivity(intent);
                break;

            case R.id.task_details_sealing_num:
                Intent intent1 =new Intent(getApplicationContext(),SealingBoxNumActivity.class);
                getApplicationContext().startActivity(intent1);
                break;

        }
    }
    private void receivesSendTask() {

        String st = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
        Map<String, String> map = new HashMap<>();
        map.put("task_id", String.valueOf(task.getVeh_task_id()));
        map.put("phone_no", st);
        API.receiveSendTask(map, TaskDetailsActivity.this, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {

            }
            @Override
            public void onsuccess(String object) {
                String ids = (String) SharedPreferencesUtil.getParam(Cons.VEH_TASK_ID, "");
                if(!Tools.isEmpty(ids)){
                    SharedPreferencesUtil.clearItem(Cons.VEH_TASK_ID);
                    SharedPreferencesUtil.clearItem(ids);
                }
                finish();
            }
        });
    }
    private void receivesAssignments() {
        String st = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
        Map<String, String> map = new HashMap<>();
        map.put("task_id", String.valueOf(task.getVeh_task_id()));
        map.put("phone_no", st);
        API.receivesAssignments(map, TaskDetailsActivity.this, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {

            }

            @Override
            public void onsuccess(String object) {
                ViseLog.e(object);
                Gson gson = new Gson();
                AcceptAssignmentBack acceptAssignmentBack = gson.fromJson(object, AcceptAssignmentBack.class);
                if (!Tools.isEmpty(acceptAssignmentBack.getContent()) && acceptAssignmentBack.getContent().equals("OK")) {
                    Cons.veh_task_id = String.valueOf(task.getVeh_task_id());
                    if(content.getRouting()!=null&&content.getRouting().size()>0){
                        SharedPreferencesUtil.setParam(Cons.VEH_TASK_ID, String.valueOf(task.getVeh_task_id()));
                        SharedPreferencesUtil.setParam(String.valueOf(task.getVeh_task_id()), gson.toJson(content.getRouting()));
                    }else {
                        String ids = (String) SharedPreferencesUtil.getParam(Cons.VEH_TASK_ID, "");
                        if(!Tools.isEmpty(ids)){
                            SharedPreferencesUtil.clearItem(Cons.VEH_TASK_ID);
                            SharedPreferencesUtil.clearItem(ids);
                        }
                    }
                    task.setStatus(2);
                    taskDetailsAdapter.status(task.getStatus());
                    taskDetailsAdapter.notifyDataSetChanged();
                    taskDetailsStatus.setText(status + "进行中");
                    assignments.setVisibility(View.GONE);
                    endTask.setVisibility(View.VISIBLE);
                    buttonTitleLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getHttpList(String id, boolean isres) {
        Map<String, String> map = new HashMap<>();
        map.put("task_id", id);
        API.listDetale(map, TaskDetailsActivity.this, isres, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {
                Tools.ToastsShort(getApplicationContext(), errst);
            }

            @Override
            public void onsuccess(String object) {
                relativeLayout.setVisibility(View.GONE);
                ptrClassicFrameLayout.refreshComplete();
                type = "";
                viewData(object);
            }
        });
    }


    public void dialog(TackDetailsData data) {
        String types = data.getType();
        if(task.getStatus()==2){
            if (!types.equals(type)) {
                type = data.getType();
                Map<String, String> map = new HashMap<>();
                map.put("loc_type", type);
                getButton(map);
            }
        }
    }

    private void setButton(final List<ButtonData> list) {
        relativeLayout.setVisibility(View.VISIBLE);
        Button[] bt = new Button[list.size()];
        buttonLayout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            bt[i] = new Button(this);
            bt[i].setText(list.get(i).getVeh_action_desc());
            bt[i].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.ffffff));
            bt[i].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.boder_rounded_6ca8cd_6ca8cd));
            bt[i].setTag(i);
            LinearLayout.LayoutParams btParams = new LinearLayout.LayoutParams(Tools.dp2px(getApplicationContext(), 90), Tools.dp2px(getApplicationContext(), 35));
            btParams.leftMargin = Tools.dp2px(getApplicationContext(), 20);
            bt[i].setTextSize(11);
            bt[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int is = (Integer) v.getTag();
                    ButtonData buttonData = list.get(is);
                    Gson gson = new Gson();
                    Intent intent = new Intent(getApplicationContext(), UpDataPictureActivity.class);
                    intent.putExtra("ButtonData", gson.toJson(buttonData));
                    intent.putExtra("taskDetailsAdapter", gson.toJson(taskDetailsAdapter.getData()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity(intent);
                }
            });
            buttonLayout.addView(bt[i], btParams);
        }
    }

    @Override
    protected int getContentLayoutId() {

        return R.layout.task_details_layout;
    }

    @Override
    protected void setData(String string) {

    }

    private void getButton(Map<String, String> map) {
        API.getButtonData(map, this, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {

            }

            @Override
            public void onsuccess(String object) {

                Gson gson = new Gson();
                List<ButtonData> list = gson.fromJson(object, new TypeToken<List<ButtonData>>() {
                }.getType());
                setButton(list);
            }
        });
    }


}
