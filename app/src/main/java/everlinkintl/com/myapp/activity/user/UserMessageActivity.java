package everlinkintl.com.myapp.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.UserMessageAdapter;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.BasicData;
import everlinkintl.com.myapp.datatemplate.UserMessageData;

public class UserMessageActivity extends MyBsetActivity {
    @BindView(R.id.user_message_list)
    ListView listView;
    @BindArray(R.array.user_message_name)
    String[] name;
    @BindArray(R.array.user_message_content)
    String[] content;
    @BindString(R.string.user_message)
    String userMessage;
    List<UserMessageData> list;
    UserMessageAdapter userMessageAdapter;
    @Override
    protected int getContentLayoutId() {
        return R.layout.user_message_layout;
    }

    @Override
    protected void setData(String string) {
        Gson gson =new Gson();
        BasicData basicData = gson.fromJson(string,BasicData.class);
        if(basicData.getCode() == Tools.code().get("userMessage")){
            List<String> list2 = gson.fromJson(basicData.getObject(),List.class);
            for(int s=0;s<list.size();s++){
                if(s<list2.size()){
                    try {
                        list.get(s).setContent(URLDecoder.decode(list2.get(s),"utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            userMessageAdapter.setData(list);
            userMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(userMessage);
        list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            UserMessageData userMessageData = new UserMessageData();
            userMessageData.setName(name[i]);
            userMessageData.setContent("");
            list.add(userMessageData);
        }
        userMessageAdapter = new UserMessageAdapter(getApplicationContext());
        userMessageAdapter.setData(list);
        listView.setAdapter(userMessageAdapter);
        userMessageAdapter.notifyDataSetChanged();
        List<String> list1 = new ArrayList<>();
        try {
            list1.add(URLEncoder.encode("汪小菲","utf-8"));
            list1.add(URLEncoder.encode("18516530896","utf-8"));
            list1.add(URLEncoder.encode("421818999920291725","utf-8"));
            list1.add(URLEncoder.encode("我的家在东北松花江上","utf-8"));
            list1.add(URLEncoder.encode("MK1001","utf-8"));
            list1.add(URLEncoder.encode("一辆豪华的桑塔纳","utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BasicData basicData =new BasicData();
        basicData.setCode(Tools.code().get("userMessage"));
        basicData.setErrorMessage("个人信息");
        Gson gson =new Gson();
        String stList = gson.toJson(list1);
        basicData.setObject(stList);
        String stBasicData = gson.toJson(basicData);
        sendBroadcastToService(stBasicData+"\n");
    }

}
