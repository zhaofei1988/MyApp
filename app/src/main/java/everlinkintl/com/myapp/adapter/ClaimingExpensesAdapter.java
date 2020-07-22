package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vise.log.ViseLog;

import java.util.List;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.newdata.ClaimingExpensesData;

public class ClaimingExpensesAdapter extends MyBaseAdapter{
    String mStatus;
    public ClaimingExpensesAdapter(Context context,String status) {
        super(context);
        this.mContext = context;
        this.mStatus = status;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.claiming_expenses_item;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
        ClaimingExpensesData task = (ClaimingExpensesData) item;
        TextView titleTxt = holder.get(R.id.action_name_zh_cn, TextView.class);
        Button claimingExpensesJian = holder.get(R.id.claiming_expenses_jian, Button.class);
        TextView actionQty = holder.get(R.id.action_qtyaaa, TextView.class);
        TextView actionUnitZhCn = holder.get(R.id.action_unit_zh_cn, TextView.class);
        Button claimingExpensesJia = holder.get(R.id.claiming_expenses_jia, Button.class);
        TextView claimingExpensesTime = holder.get(R.id.claiming_expenses_time, TextView.class);
        titleTxt.setText(task.getAction_name_zh_cn());
        actionQty.setText(task.getAction_qty()+"");
        actionUnitZhCn.setText(task.getAction_unit_zh_cn());
        claimingExpensesTime.setText(Tools.timeFormaDay());
        claimingExpensesJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isEmpty(mStatus)&&mStatus.trim().equals("DONE")){
                    Tools.ToastsShort(mContext,"本单已经过期，不能报销");
                    return ;
                }
                List<ClaimingExpensesData> list=getData();
                if(list.get(position).getAction_qty()>0){
                    list.get(position).setAction_qty(list.get(position).getAction_qty()-1);
                    setData(list);
                    notifyDataSetChanged();
                }

            }
        });
        claimingExpensesJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Tools.isEmpty(mStatus)&&mStatus.trim().equals("DONE")){
                    Tools.ToastsShort(mContext,"本单已经过期，不能报销");
                    return ;
                }
                List<ClaimingExpensesData> list=getData();
                if(list.get(position).getAction_qty()<100){
                    list.get(position).setAction_qty(list.get(position).getAction_qty()+1);
                    setData(list);
                    notifyDataSetChanged();
                }
            }
        });
    }
}