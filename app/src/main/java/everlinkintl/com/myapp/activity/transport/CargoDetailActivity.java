package everlinkintl.com.myapp.activity.transport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.CargoDetailAdapter;
import everlinkintl.com.myapp.datatemplate.CargoDetailData;

public class CargoDetailActivity extends MyBsetActivity {
    @BindView(R.id.cargo_detail_list)
    ListView listView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.cargo_detail_layout;
    }

    @Override
    protected void setData(String string) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("121312312312312312312");
        List<CargoDetailData> list =new ArrayList<>();
        int p=0;
        for (int i =0;i<10;i++){
            CargoDetailData cargoDetailData =new CargoDetailData();
            cargoDetailData.setTime("pppppp"+1);
            cargoDetailData.setAddress("adadasd"+i);
            cargoDetailData.setButtonText("aaaaa"+1);
            if(p==3){
                p=0;
            }
            cargoDetailData.setButtonStatus(p);
            p++;
            List<String> list1 =new ArrayList<>();
            list1.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542257375705&di=edb57872e6c60253f25f99dbb7400a0d&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F3b292df5e0fe99254845c40539a85edf8db1711e.jpg");
            list1.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542257375706&di=292817615327da0c8c1cc3552d2c385c&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8601a18b87d6277fa50dc96125381f30e924fc48.jpg");
            list1.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542257375706&di=bb325f312478e9d4c5be4579db0e3b70&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F03087bf40ad162d91b5d784b1cdfa9ec8a13cd32.jpg");
            list1.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542257375705&di=47a5d679d62828605cd4dd793eeffd4e&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fe4dde71190ef76c689199e739016fdfaae51679a.jpg");
            cargoDetailData.setImgUrl(list1);
            list.add(cargoDetailData);
        }
        CargoDetailAdapter cargoDetailAdapter = new CargoDetailAdapter(getApplicationContext(),this);
        cargoDetailAdapter.setData(list);
        listView.setAdapter(cargoDetailAdapter);
        cargoDetailAdapter.notifyDataSetChanged();

    }
}
