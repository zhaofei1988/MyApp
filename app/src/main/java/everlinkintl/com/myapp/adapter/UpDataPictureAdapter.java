package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.transport.UpDataPictureActivity;
import everlinkintl.com.myapp.view.PictureDialog;
import everlinkintl.com.myapp.datatemplate.PictureData;

public class UpDataPictureAdapter extends MyBaseAdapter {
    private  Context mContext;
    private UpDataPictureActivity mUpDataPictureActivity;
    public UpDataPictureAdapter(Context context, UpDataPictureActivity upDataPictureActivity) {
        super(context);
        this.mContext =context;
        this.mUpDataPictureActivity = upDataPictureActivity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.up_data_picture_item;
    }

    @Override
    public void handleItem(int itemViewType, final int position, Object item, ViewHolder holder, boolean isRecycle) {
        final PictureData it = (PictureData) item;
        ImageView pictureImg = holder.get(R.id.picture_img,ImageView.class);
        TextView pictureCleanImg = holder.get(R.id.picture_clean_img,TextView.class);
        TextView pictureAddImg = holder.get(R.id.picture_add_img,TextView.class);
        RelativeLayout pictureRL = holder.get(R.id.picture_RL,RelativeLayout.class);
        RelativeLayout addPictureRL = holder.get(R.id.add_picture_RL,RelativeLayout.class);
        pictureCleanImg.setTypeface(mUpDataPictureActivity.iconfont);
        pictureAddImg.setTypeface(mUpDataPictureActivity.iconfont);
        if(it.isIsfoold()){
            pictureRL.setVisibility(View.GONE);
            addPictureRL.setVisibility(View.VISIBLE);
            addPictureRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUpDataPictureActivity.adapterClick(v);
                }
            });
        }else {
            pictureRL.setVisibility(View.VISIBLE);
            addPictureRL.setVisibility(View.GONE);
            Glide.with(this.mContext).load(it.getUrl()).into(pictureImg);
            pictureRL.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    PictureDialog pictureDialog = new PictureDialog(mUpDataPictureActivity,mContext);
                    pictureDialog.dialogIndex(it.getUrl());
                }
            });
            pictureCleanImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> list = getData();
                    list.remove(position);
                    setData(list);
                    notifyDataSetChanged();
                }
            });
        }

    }
}
