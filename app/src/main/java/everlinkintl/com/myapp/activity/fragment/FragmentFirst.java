package everlinkintl.com.myapp.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyFragment;
import everlinkintl.com.myapp.activity.transport.HistoryTaskActivity;
import everlinkintl.com.myapp.adapter.FirstTabAdapter;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.BasicData;
import everlinkintl.com.myapp.datatemplate.Task;
import everlinkintl.com.myapp.http.API;
import everlinkintl.com.myapp.http.Okhttp;
import everlinkintl.com.myapp.newdata.BasicDataNew;
import everlinkintl.com.myapp.newdata.Content;

public class FragmentFirst extends MyFragment {
    FirstTabAdapter firstTabAdapter;
    @BindView(R.id.first_tab_list)
    ListView listView;
    @BindString(R.string.transportation_task)
    String transportationTask;
    @BindString(R.string.history_task)
    String historyTask;
    int page = 0;
    @BindView(R.id.test_list_view_frame)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    List<Task> basicDataList = new ArrayList<>();
    @Override
    protected int getContentLayoutId() {
        return R.layout.tab_first_layout;
    }

    @Override
    protected void setData(String s) {
        if (!Tools.isEmpty(s)) {
            Gson gson = new Gson();
            BasicData basicData = gson.fromJson(s, BasicData.class);
            if (basicData.getCode() == Tools.code().get("tos")) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }


    }

    @Override
    public void onStart() {
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitleName(transportationTask);
        setGoneBreak();
        setTitleRigthTv(historyTask, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryTaskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        Intent intent = new Intent(Cons.RECEIVER_ACTION);
        intent.putExtra(Cons.RECEIVER_PUT_FRAGMENT, "1");
        getContext().sendBroadcast(intent);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override

            public void onRefreshBegin(PtrFrameLayout frame) {
                Message message = Message.obtain();
                message.what = 2;
                handler.sendMessage(message);
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override

            public void loadMore() {
                Message message = Message.obtain();
                message.what = 3;
                handler.sendMessage(message);
            }

        });
        firstTabAdapter = new FirstTabAdapter(getContext(), FragmentFirst.this);
        listView.setAdapter(firstTabAdapter);

    }

    Handler handler = new Handler() {
        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    page = 0;
                    basicDataList.clear();
                    getHttpList(true, true);
                    break;
                case 2:
                    page = 0;
                    basicDataList.clear();
                    ptrClassicFrameLayout.loadMoreComplete(true);
                    getHttpList(true, false);
                    break;
                case 3:
                    getHttpList(false, false);
                    break;
            }
        }
    };

    private void getHttpList(boolean isUpData, boolean isInitial) {
        Map<String, String> map = new HashMap<>();
        String name = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
        map.put("page", String.valueOf(page));
        map.put("page_size", String.valueOf(10));
        map.put("name", name);
        API.list(map, getActivity(), isInitial, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {
                if (!isUpData) {
                    ptrClassicFrameLayout.setLoadMoreEnable(false);
                    ptrClassicFrameLayout.loadMoreComplete(true);
                }
                Tools.ToastsShort(getContext(), errst);
            }

            @Override
            public void onsuccess(String object) {
                if (isUpData) {
                    upData(object);
                } else {
                    extraDataLoading(object);
                }

            }
        });
    }

    private void extraDataLoading(String object) {
        page++;
        Gson gson = new Gson();
        List<Task> list = gson.fromJson(object, new TypeToken<List<Task>>() {
        }.getType());
        if (list != null && list.size() != 0) {
            basicDataList.addAll(list);
            firstTabAdapter.setData(basicDataList);
            firstTabAdapter.notifyDataSetChanged();
            ptrClassicFrameLayout.loadMoreComplete(true);
        } else {
            ptrClassicFrameLayout.loadMoreComplete(true);
            ptrClassicFrameLayout.setLoadMoreEnable(false);
        }

    }

    private void upData(String object) {
        if (basicDataList != null && basicDataList.size() == 0) {
            page++;
        }
        Gson gson = new Gson();
        List<Task> list = gson.fromJson(object, new TypeToken<List<Task>>() {
        }.getType());

        if (list != null && list.size() != 0) {
            basicDataList.addAll(list);
            firstTabAdapter.setData(list);
            firstTabAdapter.notifyDataSetChanged();
        }

        ptrClassicFrameLayout.refreshComplete();
        ptrClassicFrameLayout.setLoadMoreEnable(true);
        for (int t = 0; t < list.size(); t++) {
            if (list.get(t).getStatus() == 2 && !String.valueOf(list.get(t).getVeh_task_id()).equals(Cons.veh_task_id)) {
                getDetale(String.valueOf(list.get(t).getVeh_task_id()), false);

                break;
            }

        }
    }

    private void getDetale(String id, boolean isres) {
        Map<String, String> map = new HashMap<>();
        map.put("task_id", id);
        API.listDetale(map, getActivity(), isres, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {
                Tools.ToastsShort(getContext(), errst);
            }

            @Override
            public void onsuccess(String object) {
                Gson gson = new Gson();
                BasicDataNew basicDataNew = gson.fromJson(object, BasicDataNew.class);
                String strContent = basicDataNew.getContent().replaceAll("\\\\", "");
                Content content = gson.fromJson(strContent, Content.class);
                if (content.getRouting() != null && content.getRouting().size() > 0) {
                    Cons.veh_task_id = id;
                    SharedPreferencesUtil.setParam( Cons.VEH_TASK_ID, id);
                    SharedPreferencesUtil.setParam(id, gson.toJson(content.getRouting()));
                } else {
                    String ids = (String) SharedPreferencesUtil.getParam(Cons.VEH_TASK_ID, "");
                    if (!Tools.isEmpty(ids)) {
                        SharedPreferencesUtil.clearItem(Cons.VEH_TASK_ID);
                        SharedPreferencesUtil.clearItem(ids);
                    }

                }
            }
        });
    }

}
