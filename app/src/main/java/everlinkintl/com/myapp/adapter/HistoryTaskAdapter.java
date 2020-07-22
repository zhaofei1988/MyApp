package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.transport.HistoryTaskActivity;
import everlinkintl.com.myapp.datatemplate.FirstTab;

public class HistoryTaskAdapter extends MyBaseAdapter{
    HistoryTaskActivity mActivity;
    public HistoryTaskAdapter(Context context, HistoryTaskActivity activity) {
        super(context);
        this.mActivity =activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.tab_first_layout_list_item;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, MyBaseAdapter.ViewHolder holder, boolean isRecycle) {
        FirstTab data = (FirstTab) item;
        TextView leftTv = holder.get(R.id.num, TextView.class);
        TextView statusTv = holder.get(R.id.first_tab_item_status_tv, TextView.class);
        TextView itemTv1 = holder.get(R.id.first_tab_item_tv1, TextView.class);
        TextView itemTv2 = holder.get(R.id.first_tab_item_tv2, TextView.class);
        TextView itemTv3 = holder.get(R.id.first_tab_item_tv3, TextView.class);
        TextView itemTv4 = holder.get(R.id.first_tab_item_tv4, TextView.class);
        LinearLayout itemLy = holder.get(R.id.first_tab_item_ly, LinearLayout.class);
        TextView firstIconIv = holder.get(R.id.first_item_icon_tv, TextView.class);
        statusTv.setText("已完成");
        statusTv.setTextColor(mActivity.getResources().getColor(R.color.c44db5e));
        itemTv1.setTextColor(mActivity.getResources().getColor(R.color.c44db5e));
        itemTv2.setTextColor(mActivity.getResources().getColor(R.color.c44db5e));
        itemTv3.setTextColor(mActivity.getResources().getColor(R.color.c44db5e));
        itemTv4.setTextColor(mActivity.getResources().getColor(R.color.c44db5e));
        itemLy.setBackgroundResource(R.color.c2044DB5E);
        leftTv.setText(data.getCargo_location().getTask().getTrans_no());
        firstIconIv.setTypeface(mActivity.iconfont);
    }
}
