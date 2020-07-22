package everlinkintl.com.myapp.activity.transport;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.BoxDataPictureAdapter;
import everlinkintl.com.myapp.adapter.UpDataPictureAdapter;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.GPSLoction;
import everlinkintl.com.myapp.common.SharedPreferencesUtil;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.PictureData;
import everlinkintl.com.myapp.datatemplate.TackDetailsData;
import everlinkintl.com.myapp.http.API;
import everlinkintl.com.myapp.http.Okhttp;
import everlinkintl.com.myapp.newdata.ButtonData;

public class SealingBoxNumActivity extends MyBsetActivity {
    @BindView(R.id.box_picture_gridview)
    GridView gridView;
    private String pashUrl = "";
    List<PictureData> list;
    BoxDataPictureAdapter upDataPictureAdapter;
    ButtonData buttonData;
    List<TackDetailsData> tackList;
    int ps = 0;

    @BindView(R.id.box_num)
    EditText boxNum;

    @Override
    protected int getContentLayoutId() {
        return R.layout.sealing_box_num_layout;
    }

    @Override
    protected void setData(String string) {

    }
    public void adapterClick(View view) {
        switch (view.getId()) {
            case R.id.add_picture_RL:
                Tools tools = new Tools();
                pashUrl = tools.openCamera(SealingBoxNumActivity.this);
                break;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("封箱号操作");
        upDataPictureAdapter = new BoxDataPictureAdapter(getApplicationContext(), SealingBoxNumActivity.this);
        list = new ArrayList<>();
        PictureData pictureData = new PictureData();
        pictureData.setIsfoold(true);
        list.add(pictureData);
        upDataPictureAdapter.setData(list);
        gridView.setAdapter(upDataPictureAdapter);
        upDataPictureAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.box_img_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.box_img_btn:
                if (buttonData.getUpload_img().equals("M")) {
                    if (upDataPictureAdapter.getData() == null || upDataPictureAdapter.getData().size() <= 1) {
                        Tools.ToastsShort(getApplicationContext(), "请拍照提交");
                        return;
                    }
                }
                upData();
                break;
        }
    }

    private void upData() {
        if (list != null && list.size() > 1) {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        } else {
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }

    }

    Handler handler = new Handler() {
        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (ps < list.size() - 1) {
                        List<PictureData> ls= upDataPictureAdapter.getData();
                        Map<String, String> maps = sendData(buttonData, tackList);
                        if(maps!=null){
                            String utl = ls.get(ps).getUrl();
                            addFile(maps, utl);
                        }

                    }
                    ps++;
                    break;
                case 2:
                    Map<String, String> map1 = sendData(buttonData, tackList);
                    if(map1!=null) {
                        sendNoFile(map1);

                    }
                    break;

            }
        }
    };

    private void addFile(Map<String, String> map, String uri) {
        API.addFile(map, uri, SealingBoxNumActivity.this, new Okhttp.FileBack() {
            @Override
            public void onFalia(int code, java.lang.String errst) {
            }

            @Override
            public void fileOnsuccess(Object object) {
                Tools.ToastsShort(getApplicationContext(), ps + "张图片上传成功");
                if (ps == upDataPictureAdapter.getData().size() - 1) {
                    Tools.deleteDirectory();
                    finish();
                } else {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }


            }
        });
    }

    private void sendNoFile(Map<String, String> map) {
        API.add(map, SealingBoxNumActivity.this, new Okhttp.BasicsBack() {
            @Override
            public void onFalia(String errst) {
                Tools.ToastsShort(getApplicationContext(), errst);
            }

            @Override
            public void onsuccess(String object) {
                Tools.ToastsShort(getApplicationContext(), "提交成功");
            }
        });
    }

    private Map<String, String> sendData(ButtonData buttonData, List<TackDetailsData> list) {
        Map<String, String> map = new HashMap<>();
        if (Tools.isEmpty(boxNum.getText().toString().trim())) {
            Tools.ToastsShort(getApplicationContext(), "请输入封箱号");
            return null;
        }
        map.put("num", boxNum.getText().toString().trim());
        GPSLoction gpsLoction = new GPSLoction();
        LatLng latLng = gpsLoction.getLocation(SealingBoxNumActivity.this);
        CoordinateConverter converter = new CoordinateConverter(getApplicationContext());
        // CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标点 LatLng类型
        converter.coord(latLng);
        // 执行转换操作
        LatLng desLatLng = converter.convert();
        if(desLatLng==null){
            Tools.ToastsShort(getApplicationContext(), "请检查GPS是否打开");
            return null;
        }
        String st = (String) SharedPreferencesUtil.getParam(Cons.EVERLINKINT_LOGIN_NAME, "");
        String num = "";
        for (int s = 0; s < list.size(); s++) {
            if (list.get(s).isSelected()) {
                num = list.get(s).getNewTaskDetailsData().getBiz_no().trim() + "," + num;
            }
        }
        num = num.substring(0, num.length() - 1);

        map.put("biz_no", num);
        map.put("phone_no", st);
        map.put("action_type", buttonData.getVeh_action_type());
        map.put("action_desc", buttonData.getVeh_action_desc());
        map.put("create_date", Tools.timeForma());
        map.put("gps_string", String.valueOf(desLatLng.longitude) + "," + String.valueOf(desLatLng.latitude));

        ViseLog.e(map);
        return map;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Cons.REQUEST_CAMERA) {
            if (Tools.fileIsExists(pashUrl)) {
                PictureData pictureData = new PictureData();
                pictureData.setIsfoold(false);
                String url =  Tools.comp(pashUrl);
                pictureData.setUrl(url);
                list.add(0, pictureData);
                upDataPictureAdapter.setData(list);
                upDataPictureAdapter.notifyDataSetChanged();
            }
        }
    }
}
