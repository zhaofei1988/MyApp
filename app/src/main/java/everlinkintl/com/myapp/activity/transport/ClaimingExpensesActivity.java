package everlinkintl.com.myapp.activity.transport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.ClaimingExpensesAdapter;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.Task;
import everlinkintl.com.myapp.http.API;
import everlinkintl.com.myapp.http.Okhttp;
import everlinkintl.com.myapp.newdata.AcceptAssignmentBack;
import everlinkintl.com.myapp.newdata.ClaimingExpensesData;

public class ClaimingExpensesActivity extends MyBsetActivity {
    @BindString(R.string.claiming_expenses)
    String claimingExpenses;
    @BindString(R.string.submit_to)
    String submitTo;
    @BindView(R.id.claiming_expenses_list)
    public ListView listview;
    @BindView(R.id.claiming_expenses_text)
    public TextView text;

    String Status;
    Task task;
    ClaimingExpensesAdapter claimingExpensesAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.claiming_expenses_layout;
    }

    @Override
    protected void setData(String string) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(claimingExpenses);
        Gson gson = new Gson();
        String strTask = (String) getIntent().getExtras().get("task");
        task = gson.fromJson(strTask, Task.class);
        text.setText("运输路径："+task.getRoute_name()+"\n运送时间："+task.getRemark());
        getListActionAll();
        setTitleRigthTvShow(submitTo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBizJobActionValue();
            }
        });
    }
    private void getListActionAll(){
        Map<String ,String> map =new HashMap<>();
        map.put("trans_no",task.getTrans_no());

        API.getListActionAllCode(map,this, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {

            }

            @Override
            public void onsuccess(String object) {
                Gson gson = new Gson();
                List<ClaimingExpensesData> list = gson.fromJson(object, new TypeToken<List<ClaimingExpensesData>>() {
                }.getType());
                if(list!=null&&list.size()>0){
                    getListBizActionValue(list);
                }

            }
        });
    }
    private void getListBizActionValue(List<ClaimingExpensesData> list ){
        Map<String ,String> map =new HashMap<>();
        map.put("trans_no",task.getTrans_no());
        API.getListBizActionValue(map, this, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {

            }

            @Override
            public void onsuccess(String object) {
                Gson gson = new Gson();
                List<ClaimingExpensesData> list1 = gson.fromJson(object, new TypeToken<List<ClaimingExpensesData>>() {
                }.getType());
                if(list1!=null&&list1.size()>0){
                    for(int i=0;i<list1.size();i++){
                        if(list1.get(i).getStatus().trim().equals("DONE")){
                            if(Tools.isEmpty(Status)){
                                Status=list1.get(i).getStatus().trim();
                            }

                        }
                     for(int s=0;s<list.size();s++){
                         if(list1.get(i).getAction_id().equals(list.get(s).getAction_id())){
                             list.get(s).setAction_remark(list1.get(i).getAction_remark());
                             list.get(s).setAction_qty(list1.get(i).getAction_qty());
                         }
                     }
                    }
                }

             if(claimingExpensesAdapter==null){
                 claimingExpensesAdapter=new ClaimingExpensesAdapter(getApplicationContext(),Status);
                 listview.setAdapter(claimingExpensesAdapter);
             }

                claimingExpensesAdapter.setData(list);
                claimingExpensesAdapter.notifyDataSetChanged();
                SharedPreferencesUtil.setParam(Cons.CLAI_DATA, new Gson().toJson(list));
            }
        });
    }
    private void updateBizJobActionValue(){
        List<ClaimingExpensesData> list1 = claimingExpensesAdapter.getData();
        List<ClaimingExpensesData> list2=new ArrayList<>();
        String st=  (String)SharedPreferencesUtil.getParam(Cons.CLAI_DATA, "");
        List<ClaimingExpensesData>  tt = new Gson().fromJson(st, new TypeToken<List<ClaimingExpensesData>>() {
        }.getType());
        list2.addAll(list1);
        for(int a=0;a<tt.size();a++){
            for(int q=0;q<list2.size();q++){
                if(tt.get(a).getAction_id().trim().equals(list2.get(q).getAction_id().trim())
                &&tt.get(a).getAction_qty()==list2.get(q).getAction_qty()){
                    list2.remove(q) ;
                }
            }
        }
        ViseLog.e(list2.size());
        if(list2==null||list2.size()==0){
            Tools.ToastsShort(getApplicationContext(),"您没有改变数据不需要提交");
            return;
        }
        for (int s=0;s<list2.size();s++){
            list2.get(s).setBiz_no(task.getTrans_no());
        }
        API.getUpdateBizJobActionValue(list1, this, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {

            }

            @Override
            public void onsuccess(String object) {
                AcceptAssignmentBack acceptAssignmentBack =new Gson().fromJson(object,AcceptAssignmentBack.class);
                if(acceptAssignmentBack.getContent().equals("success")){
                    Tools.ToastsShort(getApplicationContext(),"报销提交成功");
                    SharedPreferencesUtil.clearItem(Cons.CLAI_DATA);
                    finish();
                }else {
                    Tools.ToastsShort(getApplicationContext(),"报销提交失败");
                }
            }
        });
    }
}
