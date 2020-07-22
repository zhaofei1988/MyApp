package everlinkintl.com.myapp.activity.transport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.SearchTaskAdapter;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.CargoLocation;
import everlinkintl.com.myapp.datatemplate.FirstTab;
import everlinkintl.com.myapp.datatemplate.FirstTabData;
import everlinkintl.com.myapp.datatemplate.Task;

public class SearchTaskActivity extends MyBsetActivity {
    @BindView(R.id.search_task_list)
    ListView searchTaskList;
    SearchTaskAdapter historyTaskAdapter;
    @Override
    protected int getContentLayoutId() {
        return R.layout.search_task_layout;
    }

    @Override
    protected void setData(String string) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleGone();
        setTitleSearchShow();
        setTitleRigthTvShow("搜索", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.ToastsShort(getApplicationContext(),editTextSearch.getText().toString());
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
        historyTaskAdapter =new SearchTaskAdapter(getApplicationContext(),this);
//        Gson gson = new Gson();
//        FirstTabData jsonStr = gson.fromJson(s,FirstTabData.class);
        historyTaskAdapter.setData(firstTabData.getData());
        searchTaskList.setAdapter(historyTaskAdapter);
        historyTaskAdapter.notifyDataSetChanged();
    }
}
