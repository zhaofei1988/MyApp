package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.log.ViseLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.fragment.FragmentFirst;
import everlinkintl.com.myapp.activity.transport.TaskDetailsActivity;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.Task;

public class FirstTabAdapter extends MyBaseAdapter {
    FragmentFirst mFragmentFirst;
    Context mContext;
    public FirstTabAdapter(Context context,FragmentFirst fragmentFirst) {
        super(context);
        this.mFragmentFirst = fragmentFirst;
        this.mContext = context;
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
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
        Task task = (Task) item;
        TextView leftTv = holder.get(R.id.num, TextView.class);
        TextView firstIconIv = holder.get(R.id.first_item_icon_tv, TextView.class);
        TextView itemTv1 = holder.get(R.id.first_tab_item_tv1, TextView.class);
        TextView itemTv2 = holder.get(R.id.first_tab_item_tv2, TextView.class);
        TextView itemTv3 = holder.get(R.id.first_tab_item_tv3, TextView.class);
        TextView status = holder.get(R.id.first_tab_item_status_tv, TextView.class);
        TextView tv4 = holder.get(R.id.first_tab_item_tv4, TextView.class);
        TextView remark = holder.get(R.id.remark, TextView.class);

        LinearLayout firstTabItem = holder.get(R.id.first_tab_item, LinearLayout.class);
        leftTv.setText(task.getTrans_no());
        itemTv1.setText(task.getBiz_count());
        itemTv2.setText(task.getBiz_pick_count());
        itemTv3.setText(task.getBiz_done_count());
        if(Tools.isEmpty(task.getBiz_exception_count())){
            tv4.setText("");
        }else {
            tv4.setText(task.getBiz_exception_count());
        }
        String re="";
        if(!Tools.isEmpty(task.getRoute_name())){
            re="行驶路径:"+task.getRoute_name().trim();
        }else {
            re="行驶路径:";
        }
        if(!Tools.isEmpty(task.getRemark())){
            re=re+"\n备注："+task.getRemark().trim();
        }else {
            re=re+"\n备注：";
        }
        remark.setText(re);
        firstIconIv.setTypeface(mFragmentFirst.iconfont);
        if(task.getStatus()==1){
            status.setText("未开始");
        }else if(task.getStatus()==2){
            status.setText("进行中");
        }
        firstTabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Task> li= getData();
                SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=  task.getRemark();
                String string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();

                try {
                    Date date = format.parse(time);
                    long timestamp=date.getTime();
                    Date date1 = format.parse(string);
                    long timestamp1=date1.getTime();
                    if(timestamp1<=timestamp){
                       Tools.ToastsShort(mContext,"还没有到接单时间，请看备注时间！！！！");
                       return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int sta=1;
                for (int i=0;i<li.size();i++){
                    if(li.get(i).getStatus()==2){
                        sta=li.get(i).getStatus();
                        break;
                    }
                }
                Gson gson=new Gson();
                Intent intent =new Intent(mContext,TaskDetailsActivity.class);
                intent.putExtra("task",gson.toJson(task));
                intent.putExtra("isHasUnderway",sta);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                mContext.startActivity(intent);
            }
        });
    }
}
