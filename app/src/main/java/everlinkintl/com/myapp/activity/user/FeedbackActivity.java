package everlinkintl.com.myapp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.activity.transport.ClaimingExpensesActivity;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.BasicData;
import everlinkintl.com.myapp.datatemplate.FeedbackSelectData;
import everlinkintl.com.myapp.datatemplate.MyFeedbackData;
import everlinkintl.com.myapp.view.FeedbackSelectPop;

public class FeedbackActivity extends MyBsetActivity {
    @BindView(R.id.feedback_tv)
    TextView feedbackTv;
    @BindView(R.id.feedback_select_tv)
    TextView feedbackSelectTv;
    @BindView(R.id.feedback_et)
    EditText feedbackEt;
    @BindString(R.string.feedback_string)
    String feedbackString;
    @BindString(R.string.feedback_input_string)
    String feedbackInputString;
    @BindString(R.string.feedback_select_string)
    String feedbackSelectString;
    @BindString(R.string.my_feedback)
    String myFeedback;
    @BindString(R.string.feedback_success_string)
    String feedbackSuccess;
    @BindString(R.string.select_string)
    String selectString;
    public List<FeedbackSelectData> list;
    FeedbackSelectData feedbackSelectData1;
    @BindArray(R.array.feedback_select_array)
    String[] feedbackSelectArray;
    FeedbackSelectPop feedbackSelectPop;
    @BindView(R.id.feedback_ly)
    LinearLayout feedbackLy;

    @Override
    protected int getContentLayoutId() {
        return R.layout.feedback_layout;
    }

    @Override
    protected void setData(String string) {
        Gson gson = new Gson();
        BasicData basicData = gson.fromJson(string, BasicData.class);
        if (basicData.getCode() == Tools.code().get("feedback")) {
            Message message = Message.obtain();
            message.what = 2;
            handler.sendMessage(message);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(feedbackString);
        setTitleRigthTvShow(myFeedback, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyFeedbackActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                startActivity(intent);
            }
        });
        feedbackSelectTv.setTypeface(iconfont);
        index();
    }

    private void index() {
        list = new ArrayList<>();
        for (int i = 0; i < feedbackSelectArray.length; i++) {
            FeedbackSelectData feedbackSelectData = new FeedbackSelectData();
            feedbackSelectData.setSelectString(feedbackSelectArray[i]);
            feedbackSelectData.setSelectType(i);
            if (i == 0) {
                feedbackSelectData.setSelect(true);
            }
            list.add(feedbackSelectData);
        }
    }

    @OnClick({R.id.feedback_btn, R.id.feedback_rl})
    public void onViewClicked(View view) {
        if (!Tools.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.feedback_btn:
                feedbackBtn();
                break;
            case R.id.feedback_rl:
                feedbackSelectPop = new FeedbackSelectPop(FeedbackActivity.this);
                feedbackSelectPop.show(feedbackLy);
                break;
        }
    }

    private void feedbackBtn() {
        if (Tools.isEmpty(feedbackEt.getText().toString().trim())) {
            Tools.ToastsShort(getApplicationContext(), feedbackInputString);
            return;
        }
        if (Tools.isEmpty(feedbackSelectData1)) {
            Tools.ToastsShort(getApplicationContext(), feedbackSelectString);
            return;
        }
        BasicData basicData = new BasicData();
        Gson gson = new Gson();
        MyFeedbackData myFeedbackData = new MyFeedbackData();
        myFeedbackData.setContent(feedbackEt.getText().toString().trim());
        myFeedbackData.setType(feedbackSelectData1.getSelectType());
        myFeedbackData.setTime(Tools.timeFormat());
        String stMyFeedbackData = gson.toJson(myFeedbackData);
        basicData.setObject(stMyFeedbackData);
        basicData.setCode(Tools.code().get("feedback"));
        basicData.setErrorMessage("意见反馈");
        String stBasicData = gson.toJson(basicData);
        sendBroadcastToService(stBasicData + "\n");
    }

    public void popSeltctClick(FeedbackSelectData petion) {
        this.feedbackSelectData1 = petion;
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    feedbackTv.setText(feedbackSelectData1.getSelectString());
                    for (int i = 0; i < list.size(); i++) {
                        if (feedbackSelectData1.getSelectType() == i) {
                            list.get(i).setSelect(true);
                        } else {
                            list.get(i).setSelect(false);
                        }
                    }
                    feedbackSelectPop.dismiss();
                    break;
                case 2:
                    index();
                    feedbackEt.setText("");
                    feedbackTv.setText(selectString);
                    Tools.ToastsShort(getApplicationContext(), feedbackSuccess);
                    break;
            }
        }
    };
}
