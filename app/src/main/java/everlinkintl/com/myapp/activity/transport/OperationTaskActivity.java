package everlinkintl.com.myapp.activity.transport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.OperationTaskAdapter;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.QRHelper;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.newdata.NewTaskDetailsData;

public class OperationTaskActivity extends MyBsetActivity {
    @BindView(R.id.operation_task_list)
    ListView listView;
    boolean isAll = false;
    private String pashUrl = "";
    private String name = "";

    @Override
    protected int getContentLayoutId() {
        return R.layout.operation_task_layout;
    }

    @Override
    protected void setData(String string) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("");

        String list = (String) getIntent().getExtras().get("list");
        Gson gson = new Gson();
        List<NewTaskDetailsData> list1 = gson.fromJson(list, new TypeToken<List<NewTaskDetailsData>>() {
        }.getType());
        OperationTaskAdapter operationTaskAdapter = new OperationTaskAdapter(getApplicationContext());
        operationTaskAdapter.setData(list1);
        listView.setAdapter(operationTaskAdapter);
        operationTaskAdapter.notifyDataSetChanged();
        setTitleName(list1.get(0).getName());
        setTitleRigthTvShow("全部", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list1.size(); i++) {
                    if (!isAll) {
                        list1.get(i).setSleade(true);
                    } else {
                        list1.get(i).setSleade(false);
                    }

                }
                operationTaskAdapter.setData(list1);
                operationTaskAdapter.notifyDataSetChanged();
                if (isAll) {
                    isAll = false;
                    setTitleRigthTvShow("全部");
                } else {
                    isAll = true;
                    setTitleRigthTvShow("取消");
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<NewTaskDetailsData> list1 = operationTaskAdapter.getData();
                name = list1.get(position).getBiz_no();
                Tools tools = new Tools();
                pashUrl = tools.openCamera(OperationTaskActivity.this);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Cons.REQUEST_CAMERA) {
            if (Tools.fileIsExists(pashUrl)) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    }
    Handler handler = new Handler() {
        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            switch (msg.what) {
                // 定位过来的信息 MapLocation
                case 1:
                    if (!Tools.isEmpty(msg.obj)) {
                        Bitmap mBitmap = QRHelper.compressPicture(pashUrl);
                        String result = QRHelper.getReult(mBitmap);
                        String url="";
                        String[] strs=pashUrl.split("/");
                        for(int i=0;i<strs.length;i++){
                            if(i<strs.length-1){
                                url=url+strs[i].toString()+"/";
                            }
                        }
                        url=url+result+".jpg";
                        renameFile(pashUrl,url);
                    }
            }
        }
    };
    /**
     * 重命名文件
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    public static void renameFile(String oldPath, String newPath) {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
    }

}