package everlinkintl.com.myapp.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.MyFeedbackAdapter;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.BasicData;
import everlinkintl.com.myapp.datatemplate.MyFeedbackData;

public class MyFeedbackActivity extends MyBsetActivity {
    @BindArray(R.array.feedback_select_array)
    String[] feedbackSelectArray;
    @BindString(R.string.my_feedback)
    String myFeedback;
    List<MyFeedbackData> list;
    @BindView(R.id.my_feedback_list)
    ListView myFeedbackList;
    @BindView(R.id.my_feedback_rl)
    RelativeLayout myFeedbackRl;
    @BindView(R.id.my_feedback_null_list_icon)
    TextView listIcon;
    @Override
    protected int getContentLayoutId() {
        return R.layout.my_feedback_layout;
    }

    @Override
    protected void setData(String string) {
        Gson gson =new Gson();
        BasicData basicData = gson.fromJson(string,BasicData.class);
        if(basicData.getCode() == Tools.code().get("myFeedback")){
            List<MyFeedbackData> list2 = gson.fromJson(basicData.getObject(),List.class);
            if(Tools.isEmpty(list2)){
                myFeedbackRl.setVisibility(View.VISIBLE);
                myFeedbackList.setVisibility(View.GONE);
            }else {
                myFeedbackRl.setVisibility(View.GONE);
                myFeedbackList.setVisibility(View.VISIBLE);
            }
            MyFeedbackAdapter myFeedbackAdapter =new MyFeedbackAdapter(getApplicationContext(),feedbackSelectArray);
            myFeedbackAdapter.setData(list2);
            myFeedbackList.setAdapter(myFeedbackAdapter);
            myFeedbackAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(myFeedback);
        listIcon.setTypeface(iconfont);
        list =new ArrayList<>();
        int s=0;
        for (int i=0;i<15;i++){
            s++;
            if(s==feedbackSelectArray.length){
                s=0;
            }
            MyFeedbackData myFeedbackData =new MyFeedbackData();
            myFeedbackData.setType(s);
            myFeedbackData.setTime("2018-19-"+i+"15:00:00");
            myFeedbackData.setContent(i+"fhsujkafhasdjkfhlasdjkfhsadjkfashjkfhagffksdghkfsdghhfsdg");
            list.add(myFeedbackData);
        }
        BasicData basicData =new BasicData();
        basicData.setCode(Tools.code().get("myFeedback"));
        basicData.setErrorMessage("我的反馈");
        Gson gson =new Gson();
        String stList = gson.toJson(list);
        basicData.setObject(stList);
        String stBasicData = gson.toJson(basicData);
        sendBroadcastToService(stBasicData+"\n");

    }
}
