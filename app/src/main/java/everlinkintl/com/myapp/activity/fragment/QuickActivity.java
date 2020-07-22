package everlinkintl.com.myapp.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.FragmentPictureAdapter;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.GPSLoction;
import everlinkintl.com.myapp.common.QRHelper;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.PictureData;
import everlinkintl.com.myapp.http.API;
import everlinkintl.com.myapp.http.Okhttp;

public class QuickActivity extends MyBsetActivity {
    @BindString(R.string.shortcut_freightage)
    String shortcutFreightage;
    @BindView(R.id.up_picture_gridview)
    GridView gridView;
    private String pashUrl = "";
    List<PictureData> list;
    List<PictureData> list1;
    FragmentPictureAdapter upDataPictureAdapter;
    int ps = 0;
    String[] str1 = {"VEH_ACT_TYPR8", "VEH_ACT_TYPR9"};
    String[] str2 = {"送货失败", "送货成功"};

    @Override
    protected int getContentLayoutId() {
        return R.layout.tab_third_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(shortcutFreightage);
        setGoneBreak();
        upDataPictureAdapter = new FragmentPictureAdapter(QuickActivity.this, QuickActivity.this);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        PictureData pictureData = new PictureData();
        pictureData.setIsfoold(true);
        list.add(pictureData);
        list1.addAll(list);
        upDataPictureAdapter.setData(list);
        gridView.setAdapter(upDataPictureAdapter);
        upDataPictureAdapter.notifyDataSetChanged();
    }
    @Override
    protected void setData(String s) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Cons.REQUEST_CAMERA) {
            if (Tools.fileIsExists(pashUrl)) {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        }
    }

    public void adapterClick(View view) {
        switch (view.getId()) {
            case R.id.add_picture_RL:
                Tools tools = new Tools();
                pashUrl = tools.openCamera(QuickActivity.this);
                break;
        }
    }

    @OnClick({R.id.send_fail, R.id.send_succeed})
    public void onViewClicked(View view) {
        if (upDataPictureAdapter.getData() == null || upDataPictureAdapter.getData().size() <= 1) {
            Tools.ToastsShort(QuickActivity.this, "请拍照提交");
            return;
        }
        Message message = new Message();
        message.what = 1;
        switch (view.getId()) {
            case R.id.send_fail:
                message.arg1 = 0;
                handler.sendMessage(message);
                break;
            case R.id.send_succeed:
                message.arg1 = 1;
                handler.sendMessage(message);
                break;

        }
    }

    private Map<String, String> sendData(String num, String veh_action_type, String veh_action_desc) {
        GPSLoction gpsLoction = new GPSLoction();
        LatLng latLng = gpsLoction.getLocation(QuickActivity.this);
        CoordinateConverter converter = new CoordinateConverter(QuickActivity.this);
        // CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标点 LatLng类型
        converter.coord(latLng);
        // 执行转换操作
        LatLng desLatLng = converter.convert();
        String st = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");

        Map<String, String> map = new HashMap<>();
        map.put("biz_no", num);
        map.put("phone_no", st);
        map.put("action_type", veh_action_type);
        map.put("action_desc", veh_action_desc);
        map.put("create_date", Tools.timeForma());
        map.put("gps_string", String.valueOf(desLatLng.longitude) + "," + String.valueOf(desLatLng.latitude));
        map.put("remark", "null");
        return map;
    }

    private void addFile(Map<String, String> map, String uri) {
        API.addFile(map, uri, QuickActivity.this, new Okhttp.FileBack() {
            @Override
            public void onFalia(int code, java.lang.String errst) {
            }

            @Override
            public void fileOnsuccess(Object object) {
                Tools.ToastsShort(QuickActivity.this, ps + "张图片上传成功");
                if (ps == upDataPictureAdapter.getData().size() - 1) {
                    Tools.deleteDirectory();
                    upDataPictureAdapter.setData(list1);
                    upDataPictureAdapter.notifyDataSetChanged();
                    Tools.ToastsShort(QuickActivity.this, "提交完成");
                } else {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }


            }
        });
    }

    Handler handler = new Handler() {
        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (ps < upDataPictureAdapter.getData().size() - 1) {
                        List<PictureData> ls = upDataPictureAdapter.getData();
                        Map<String, String> maps = sendData(ls.get(ps).getNum(), str1[msg.arg1], str2[msg.arg1]);
                        String utl = ls.get(ps).getUrl();
                        addFile(maps, utl);
                    }
                    ps++;
                    break;
                case 2:
                    Bitmap mBitmap = QRHelper.compressPicture(pashUrl);
                    String result = QRHelper.getReult(mBitmap);
                    if (Tools.isEmpty(result)) {
                        Tools.ToastsLong(QuickActivity.this, "拍的照片中没有二维码，如果单子中没有二维码，请到“运输任务”>“运输详情”中提交");
                        return;
                    }
                    PictureData pictureData = new PictureData();
                    String utl = Tools.comp(pashUrl);
                    pictureData.setIsfoold(false);
                    pictureData.setUrl(utl);
                    pictureData.setNum(result);
                    list.add(0, pictureData);
                    upDataPictureAdapter.setData(list);
                    upDataPictureAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
