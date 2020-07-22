package everlinkintl.com.myapp.activity.systemmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindString;
import butterknife.BindViews;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.MessageData;

public class MessageDetileActivity extends MyBsetActivity {
    @BindViews({R.id.message_detail_title_tv,R.id.message_detail_time_tv,R.id.message_detail_content_tv})
    List<TextView> list;
    @BindString(R.string.message_detail)
    String messageDetail;

    @Override
    protected int getContentLayoutId() {
        return R.layout.message_detiale_layout;
    }

    @Override
    protected void setData(String string) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(messageDetail);
        Intent intent = getIntent();
        if (!Tools.isEmpty(intent.getExtras())) {
            Bundle bundle = intent.getExtras();
            String st = bundle.getString("tabThirdMessageData");
            Gson gson =new Gson();
            MessageData tabThirdMessageData= gson.fromJson(st,MessageData.class);
            list.get(0).setText(tabThirdMessageData.getMessageTitle());
            list.get(1).setText(tabThirdMessageData.getTime());
            list.get(2).setText(tabThirdMessageData.getMessageSketch());
        }
    }
}
