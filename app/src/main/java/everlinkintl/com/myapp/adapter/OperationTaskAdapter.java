package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.newdata.NewTaskDetailsData;

public class OperationTaskAdapter extends MyBaseAdapter {
    Context mContext;
    public OperationTaskAdapter(Context context ) {
        super(context);
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.operation_task_item;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
        NewTaskDetailsData data = (NewTaskDetailsData)item;
        LinearLayout layout = holder.get(R.id.operation_task_list_ly, LinearLayout.class);
        TextView operationTaskListNum = holder.get(R.id.operation_task_list_num, TextView.class);
        TextView operationTaskListVolumeWeight = holder.get(R.id.operation_task_list_volume_weight, TextView.class);
        TextView operationTaskListTime = holder.get(R.id.operation_task_list_time, TextView.class);
        TextView operationTaskListDec = holder.get(R.id.operation_task_list_dec, TextView.class);
        CheckBox checkBox = holder.get(R.id.operation_task_list_checkbox, CheckBox.class);
        checkBox.setChecked(data.isSleade());
        operationTaskListNum.setText(data.getBiz_no());
        operationTaskListVolumeWeight.setText("重量:"+data.getGross_wt()+"kg;"+"  体积:"+data.getPackage_info());
        if(Tools.isEmpty(data.getD_decl_date())){
            operationTaskListTime.setText("时间:无");
        }else {
            operationTaskListTime.setText("时间:"+data.getD_decl_date());
        }
        if(Tools.isEmpty(data.getRemark())){
            operationTaskListDec.setText("备注:无");
        }else {
            operationTaskListDec.setText("备注:"+data.getRemark());
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NewTaskDetailsData> list =getData();
                list.get(position).setSleade(checkBox.isChecked());
                setData(list);
                notifyDataSetChanged();
            }
        });
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(mContext,CargoDetailActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
////                mContext.startActivity(intent);
//            }
//        });
    }
}
