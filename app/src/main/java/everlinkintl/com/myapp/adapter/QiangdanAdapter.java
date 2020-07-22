package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vise.log.ViseLog;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.fragment.FragmentQiangdan;
import everlinkintl.com.myapp.common.DialogUtile;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.QiangDanData;

public class QiangdanAdapter extends MyBaseAdapter{
    FragmentQiangdan mFragmentQiangdan;
    public QiangdanAdapter(Context context, FragmentQiangdan fragmentQiangdan) {
        super(context);
        this.mFragmentQiangdan = fragmentQiangdan;
        this.mContext = context;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.qingdan_itme;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
        QiangDanData task = (QiangDanData) item;
        LinearLayout titleLay = holder.get(R.id.qiangdan_item_title, LinearLayout.class);
        TextView titleTxt = holder.get(R.id.qiangdan_item_title_txt, TextView.class);
        LinearLayout contentLay = holder.get(R.id.qiangdan_item_content, LinearLayout.class);
        TextView contentTransNo = holder.get(R.id.qiangdan_item_trans_no, TextView.class);
        TextView contentRouteName = holder.get(R.id.qiangdan_item_route_name, TextView.class);
        TextView contentRemark = holder.get(R.id.qiangdan_item_remark, TextView.class);
         if(!Tools.isEmpty(task.getTitle_name())){
             titleLay.setVisibility(View.VISIBLE);
             titleTxt.setText(task.getTitle_name());
             contentLay.setVisibility(View.GONE);
         }else {
             titleLay.setVisibility(View.GONE);
             contentLay.setVisibility(View.VISIBLE);
             contentTransNo.setText(task.getTrans_no());
             contentRouteName.setText(task.getRoute_name());
             contentRemark.setText(task.getRemark());
         }
        contentLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtile.dialog(mContext, "您确定要抢"+task.getTrans_no()+"," +
                        "\n"+task.getRoute_name()+"\n的单子吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFragmentQiangdan.dialogHttp(task);

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

            }
        });
    }
}
