package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.CaptureActivity;
import everlinkintl.com.myapp.activity.fragment.FragmentFourth;
import everlinkintl.com.myapp.activity.user.RegisterActivity;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.datatemplate.TabFourthData;

public class TabFourthUserAdapter extends MyBaseAdapter {
    Context mContext;
    FragmentFourth mFragmentFourth;
    public TabFourthUserAdapter(Context context , FragmentFourth fragmentFourth) {
        super(context);
        this.mContext =context;
        this.mFragmentFourth= fragmentFourth;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.tab_fourth_item_layout;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
       final TabFourthData tabFourthData = (TabFourthData) item;
        LinearLayout layout =holder.get(R.id.tab_fourth_item_lay,LinearLayout.class);
        RelativeLayout relativeLayout =holder.get(R.id.tab_fourth_item_de,RelativeLayout.class);
        TextView itemIcon = holder.get(R.id.tab_fourth_item_icon,TextView.class);
        TextView itemName = holder.get(R.id.tab_fourth_item_name,TextView.class);
        TextView itemNext = holder.get(R.id.tab_fourth_item_next,TextView.class);
        TextView itemRigthMessage = holder.get(R.id.tab_fourth_item_rigth_message,TextView.class);
        if(tabFourthData.isShowLay()){
            layout.setVisibility(View.VISIBLE);
        }else {
            layout.setVisibility(View.GONE);
        }
        itemIcon.setBackground(tabFourthData.getBg());
        itemIcon.setText(tabFourthData.getIcon());
        itemIcon.setTypeface(mFragmentFourth.iconfont);
        itemName.setText(tabFourthData.getTitle());
        itemNext.setTypeface(mFragmentFourth.iconfont);
        itemRigthMessage.setText(tabFourthData.getRigthMessage());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext,tabFourthData.getaClass());
                intent.setClass(mContext,tabFourthData.getaClass());
                if(tabFourthData.getaClass() == RegisterActivity.class){
                    //利用bundle来存取数据
                    Bundle bundle=new Bundle();
                    bundle.putString(Cons.PHONE,"18516530896");
                    //再把bundle中的数据传给intent，以传输过去
                    intent.putExtras(bundle);
                }
                if(tabFourthData.getaClass() == CaptureActivity.class){
                    intent.putExtra("type", 1);
                    mFragmentFourth.getActivity().startActivityForResult(intent, 0x001);
                }else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    mContext.startActivity(intent);
                }

            }
        });
    }
}
