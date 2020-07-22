package everlinkintl.com.myapp.activity.transport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.HistoryTaskAdapter;
import everlinkintl.com.myapp.datatemplate.CargoLocation;
import everlinkintl.com.myapp.datatemplate.FirstTab;
import everlinkintl.com.myapp.datatemplate.FirstTabData;
import everlinkintl.com.myapp.datatemplate.Task;

public class HistoryTaskActivity extends MyBsetActivity {
    @BindView(R.id.history_task_list)
    ListView historTaskList;
    @BindString(R.string.history_list)
    String historyList;
    HistoryTaskAdapter historyTaskAdapter;
    @Override
    protected int getContentLayoutId() {
        return R.layout.history_task_layout;
    }

    @Override
    protected void setData(String string) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(historyList);
        setTitleSearchTvShow(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),SearchTaskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                startActivity(intent);
            }
        });
        List<FirstTab> listData =new ArrayList<>();
        for(int i=0;i<10;i++){
            FirstTab data = new FirstTab();
            CargoLocation cargoLocation = new CargoLocation();
            Task task =new Task();
            task.setTrans_no("RDRKUSVIAI16090061"+i);
            cargoLocation.setTask(task);
            data.setCargo_location(cargoLocation);
            listData.add(data);
        }
        FirstTabData firstTabData =new FirstTabData();
        firstTabData.setErrcode(0);
        firstTabData.setErrmsg("正常");
        firstTabData.setData(listData);
        historyTaskAdapter =new HistoryTaskAdapter(getApplicationContext(),this);
//        Gson gson = new Gson();
//        FirstTabData jsonStr = gson.fromJson(s,FirstTabData.class);
        historyTaskAdapter.setData(firstTabData.getData());
        historTaskList.setAdapter(historyTaskAdapter);
        historyTaskAdapter.notifyDataSetChanged();
    }
}
