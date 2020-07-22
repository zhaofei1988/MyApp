package everlinkintl.com.myapp.activity.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.CaptureActivity;
import everlinkintl.com.myapp.activity.MyApplication;
import everlinkintl.com.myapp.activity.MyFragment;
import everlinkintl.com.myapp.activity.transport.ClaimingExpensesActivity;
import everlinkintl.com.myapp.activity.transport.TaskDetailsActivity;
import everlinkintl.com.myapp.activity.user.AboutActivity;
import everlinkintl.com.myapp.activity.user.CustomerServiceActivity;
import everlinkintl.com.myapp.activity.user.FeedbackActivity;
import everlinkintl.com.myapp.activity.user.LoginActivity;
import everlinkintl.com.myapp.activity.user.MessageActivity;
import everlinkintl.com.myapp.activity.user.RegisterActivity;
import everlinkintl.com.myapp.activity.user.UserMessageActivity;
import everlinkintl.com.myapp.activity.user.VersionCodeActivity;
import everlinkintl.com.myapp.adapter.TabFourthUserAdapter;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.TabFourthData;
import everlinkintl.com.myapp.datatemplate.Task;
import everlinkintl.com.myapp.http.API;
import everlinkintl.com.myapp.http.Okhttp;
import everlinkintl.com.myapp.newdata.HWData;
import everlinkintl.com.myapp.newdata.VehicleTaskId;
import everlinkintl.com.myapp.view.HeadUploadingDialog;
import everlinkintl.com.myapp.view.ZQImageViewRoundOval;

public class FragmentFourth extends MyFragment {
    @BindView(R.id.tab_fourth_list)
    ListView listView;
    @BindArray(R.array.user_string)
    String[] userString;
    @BindArray(R.array.user_string_icon)
    String[] userStringIcon;
    @BindDrawable(R.drawable.tab4_boder_round_bg)
    Drawable roundBg;
    @BindDrawable(R.drawable.tab4_boder_round_bg1)
    Drawable roundBg1;
    @BindDrawable(R.drawable.tab4_boder_round_bg2)
    Drawable roundBg2;
    @BindDrawable(R.drawable.tab4_boder_round_bg3)
    Drawable roundBg3;
    @BindDrawable(R.drawable.tab4_boder_round_bg4)
    Drawable roundBg4;
    @BindDrawable(R.drawable.tab4_boder_round_bg5)
    Drawable roundBg5;
    @BindDrawable(R.drawable.tab4_boder_round_bg6)
    Drawable roundBg6;
    @BindDrawable(R.drawable.tab4_boder_round_bg7)
    Drawable roundBg7;
    Button button;
    ZQImageViewRoundOval zqImageViewRoundOval;
    View headView;
    View footView;
    boolean isadd = true;
    @BindString(R.string.again_camera)
    String againCamera;
    private String pashUrl = "";

    @Override
    protected int getContentLayoutId() {
        return R.layout.tab_fourth_layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void setData(String s) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViseLog.e("sssssssssss");
        headView = getActivity().getLayoutInflater().inflate(R.layout.tab_fourth_head_layout, null);
        footView = getActivity().getLayoutInflater().inflate(R.layout.tab_fourth_foot_layout, null);
        button = (Button) footView.findViewById(R.id.tab4_fool_bt);
        zqImageViewRoundOval = (ZQImageViewRoundOval) headView.findViewById(R.id.tab4_head_img);
        TextView noTead = (TextView) headView.findViewById(R.id.tab4_head_no_read);
        TextView headMessage = (TextView) headView.findViewById(R.id.tab4_head_message);
        Glide.with(getContext()).load(R.drawable.user_easyico).into(zqImageViewRoundOval);
        if (isadd) {
            isadd = false;
            listView.addHeaderView(headView);
            listView.addFooterView(footView);
        }

        List<Drawable> list = new ArrayList<>();
        List<Class> list2 = new ArrayList<>();
        list.add(roundBg6);
        list2.add(QuickActivity.class);
        list.add(roundBg7);
        list2.add(CaptureActivity.class);
        list.add(roundBg);
        list2.add(UserMessageActivity.class);
        list.add(roundBg1);
        list2.add(RegisterActivity.class);
        list.add(roundBg2);
        list2.add(CustomerServiceActivity.class);
        list.add(roundBg3);
        list2.add(FeedbackActivity.class);
        list.add(roundBg4);
        list2.add(VersionCodeActivity.class);
        list.add(roundBg5);
        list2.add(AboutActivity.class);

        List<TabFourthData> list1 = new ArrayList<>();
        for (int i = 0; i < userString.length; i++) {
            TabFourthData tabFourthData = new TabFourthData();
            tabFourthData.setBg(list.get(i));
            if (i == 4) {
                tabFourthData.setShowLay(true);
            }
            tabFourthData.setIcon(userStringIcon[i]);
            tabFourthData.setTitle(userString[i]);
            if (i == 6) {
                tabFourthData.setRigthMessage(Tools.getLocalVersionName(getContext()));
            }
            tabFourthData.setaClass(list2.get(i));
            list1.add(tabFourthData);
        }
        TabFourthUserAdapter tabFourthUserAdapter =
                new TabFourthUserAdapter(getContext(), FragmentFourth.this);
        tabFourthUserAdapter.setData(list1);
        listView.setAdapter(tabFourthUserAdapter);
        tabFourthUserAdapter.notifyDataSetChanged();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.clearAll();
                sendBroadcastToServiceLoding("closeService");
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        zqImageViewRoundOval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeadUploadingDialog headUploadingDialog = new HeadUploadingDialog(FragmentFourth.this);
                headUploadingDialog.show(getActivity().findViewById(R.id.tab_fourth_ly));
            }
        });
        headMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MessageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });
    }

    public void dialogClickBack(View view) {
        Tools tools = new Tools();
        switch (view.getId()) {
            case R.id.uploding_photos_bt:
                pashUrl = tools.openCamera(getActivity());
                break;
            case R.id.uploding_photo_album_bt:
                tools.openPhotoAlbum(getActivity());
                break;
        }
    }
    public void activityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Cons.REQUEST_CAMERA) {
            if (Tools.fileIsExists(pashUrl)) {
                Glide.with(getContext()).load(pashUrl).into(zqImageViewRoundOval);
            } else {
                Tools.ToastsShort(getContext(), againCamera);
            }
        }
        if (requestCode == 0x001 && resultCode == 0x001) {
            String resultData = data.getStringExtra("result");
           String[] sp = resultData.split("/");
            getVechicleTaskSeq(sp);
        }

        if (requestCode == Cons.PHOTO_BLBUM) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                if (!Tools.isEmpty(Tools.getRealFilePath(getContext(), uri))) {
                    pashUrl = Tools.getRealFilePath(getContext(), uri);
                    Glide.with(getContext()).load(pashUrl).into(zqImageViewRoundOval);
                }
            }
        }
    }
    private void getVechicleTaskSeq(String[] sp1) {
        Map<String, String> map = new HashMap<>();
        map.put("vc_biz_no", sp1[0]);
        map.put("client_id", sp1[1]);
        API.getVechicleTaskSeq(map, getActivity(), new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {
            }

            @Override
            public void onsuccess(String object) {
                if (object.indexOf("code") != -1) {
                    Gson gson = new Gson();
                    HWData hwData = gson.fromJson(object, HWData.class);
                    if (hwData.getCode() == 10200 && !Tools.isEmpty(hwData.getData())) {
                        String json = gson.toJson(hwData.getData());
                        VehicleTaskId vehicleTaskId =gson.fromJson(json,VehicleTaskId.class);
                        Task task=new Task();
                        task.setVeh_task_id(Integer.valueOf(vehicleTaskId.getVehicle_task_id()));
                        task.setStatus(1);
                        Intent intent =new Intent(getContext(), TaskDetailsActivity.class);
                        intent.putExtra("task",gson.toJson(task));
                        intent.putExtra("isHasUnderway",1);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        getContext().startActivity(intent);
                    }else {
                        Tools.ToastsShort(getContext(),object);
                    }
                }else {
                    Tools.ToastsShort(getContext(),object);
                }
            }
        });
    }
}
