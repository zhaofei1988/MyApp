package everlinkintl.com.myapp.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.MessageAdapter;
import everlinkintl.com.myapp.datatemplate.MessageData;

public class MessageActivity extends MyBsetActivity {
    @BindView(R.id.message_list)
    ListView listView;
    @BindString(R.string.message)
    String message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(message);
        List<MessageData> list =new ArrayList<>();
        for (int i=0;i<10;i++){
            MessageData messageData =new MessageData();
            messageData.setTime("2018-11-1"+i+" 13:25:56");
            messageData.setMessageTitle("消息"+i);
            messageData.setMessageSketch("fkahssdahjk我的写欧系福建省开了房间圣诞快乐房价是打卡练腹肌");
            list.add(messageData);
        }
        MessageAdapter messageAdapter =new MessageAdapter(getApplicationContext());
        messageAdapter.setData(list);
        listView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.message_layout;
    }

    @Override
    protected void setData(String string) {

    }
}
