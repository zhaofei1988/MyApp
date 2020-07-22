package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vise.log.ViseLog;

import java.util.List;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.transport.TaskDetailsActivity;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.TackDetailsData;

public class TaskDetailsAdapter extends MyBaseAdapter {
    private TaskDetailsActivity taskDetailsActivity;
    Context mContext;
    int mStatus;

    public TaskDetailsAdapter(Context context, TaskDetailsActivity activity) {
        super(context);
        taskDetailsActivity = activity;
        this.mContext = context;

    }

    public void status(int status) {
        this.mStatus = status;

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.task_details_item;
    }

    @Override
    public void handleItem(int itemViewType, final int position, Object item, ViewHolder holder, boolean isRecycle) {
        final TackDetailsData data = (TackDetailsData) item;
        RelativeLayout heaLayout = holder.get(R.id.head_layout, RelativeLayout.class);
        final LinearLayout bodyLayout = holder.get(R.id.body_layout, LinearLayout.class);
        TextView headNameText = holder.get(R.id.head_name_text, TextView.class);
        TextView bodyNameText = holder.get(R.id.body_name_text, TextView.class);
        TextView headImg = holder.get(R.id.head_img, TextView.class);
        CheckBox taskDetailsCheckbox = holder.get(R.id.task_details_checkbox, CheckBox.class);
        TextView bodyRemarkText = holder.get(R.id.body_remark_text, TextView.class);
        TextView bodyNum = holder.get(R.id.body_num, TextView.class);
        TextView bodyVolumeWeight = holder.get(R.id.body_volume_weight, TextView.class);
        TextView bodyTime = holder.get(R.id.body_time, TextView.class);
        TextView bodyDec = holder.get(R.id.body_dec, TextView.class);
        LinearLayout checkboxLay = holder.get(R.id.task_details_checkbox_lay, LinearLayout.class);

        if (data.getHead() != null) {
            headNameText.setText(data.getHead());
            headImg.setVisibility(View.VISIBLE);
            heaLayout.setVisibility(View.VISIBLE);
            bodyLayout.setVisibility(View.GONE);
            if ("PICKUP_ADDR".equals(data.getType())) {
                headImg.setText(mContext.getString(R.string.place_delivery_icon));
            } else if ("WH_FRM_ADDR".equals(data.getType())) {
                headImg.setText(mContext.getString(R.string.driving_circuit_icon));
            } else if ("WH_TO_ADDR".equals(data.getType())) {
                headImg.setText(mContext.getString(R.string.place_arrival_icon));
            } else if ("DELIVERY_ADDR".equals(data.getType())) {
                headImg.setText(mContext.getString(R.string.place_arrival_icon));
            } else {
                headImg.setText(mContext.getString(R.string.place_arrival_icon));
            }
            headImg.setTypeface(taskDetailsActivity.iconfont);
        } else {
            heaLayout.setVisibility(View.GONE);
            headImg.setVisibility(View.GONE);
            bodyLayout.setVisibility(View.VISIBLE);
            String body = data.getBody();
            bodyNameText.setText(Html.fromHtml(body));
            bodyRemarkText.setText(data.getAddress());
            taskDetailsCheckbox.setChecked(data.isSelected());
            bodyNum.setText(data.getNewTaskDetailsData().getBiz_no());
            if (!Tools.isEmpty(data.getNewTaskDetailsData().getHawb_no())) {
                bodyNum.setText(data.getNewTaskDetailsData().getBiz_no() + "\n运单号:" + data.getNewTaskDetailsData().getHawb_no());
            }
            if (!Tools.isEmpty(data.getNewTaskDetailsData().getHawb_no()) && !Tools.isEmpty(data.getNewTaskDetailsData().getMawb_no())) {
                bodyNum.setText(data.getNewTaskDetailsData().getBiz_no() + "\n运单号:" + data.getNewTaskDetailsData().getHawb_no().trim() + "/" + data.getNewTaskDetailsData().getMawb_no().trim());
            }

            bodyVolumeWeight.setText("重量:" + data.getNewTaskDetailsData().getGross_wt() + "kg;" + "  体积:" + data.getNewTaskDetailsData().getPackage_info());
            if (Tools.isEmpty(data.getNewTaskDetailsData().getD_decl_date())) {
                bodyTime.setText("时间:无");
            } else {
                bodyTime.setText("时间:" + data.getNewTaskDetailsData().getD_decl_date());
            }
            if (Tools.isEmpty(data.getNewTaskDetailsData().getRemark())) {
                bodyDec.setText("备注:无");
            } else {
                bodyDec.setText("备注:" + data.getNewTaskDetailsData().getRemark());
            }
            if (mStatus == 1) {
                checkboxLay.setVisibility(View.GONE);
            } else if (mStatus == 2) {
                checkboxLay.setVisibility(View.VISIBLE);
            }
        }
        bodyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TackDetailsData> list = getData();
                if(list.get(position).isSelected()){
                    list.get(position).setSelected(false);
                }else {
                    list.get(position).setSelected(true);
                }
                String type = list.get(position).getType();
                taskDetailsActivity.dialog(data);
                for (int i = 0; i < list.size(); i++) {
                    if (!type.equals(list.get(i).getType())) {
                        list.get(i).setSelected(false);
                    }
                }
                setData(list);
                notifyDataSetChanged();
            }
        });
    }
}
