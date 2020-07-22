package everlinkintl.com.myapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.fragment.QuickActivity;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.PictureData;
import everlinkintl.com.myapp.view.PictureDialog;

public class FragmentPictureAdapter extends MyBaseAdapter {
    private  Context mContext;
    private QuickActivity mFragment;
    public FragmentPictureAdapter(Context context, QuickActivity fragment) {
        super(context);
        this.mContext =context;
        this.mFragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.fragment_picture_item;
    }

    @Override
    public void handleItem(int itemViewType, final int position, Object item, ViewHolder holder, boolean isRecycle) {
        final PictureData it = (PictureData) item;
        ImageView pictureImg = holder.get(R.id.picture_img,ImageView.class);
        TextView pictureCleanImg = holder.get(R.id.picture_clean_img,TextView.class);
        TextView pictureAddImg = holder.get(R.id.picture_add_img,TextView.class);
        LinearLayout pictureRL = holder.get(R.id.picture_RL,LinearLayout.class);
        RelativeLayout addPictureRL = holder.get(R.id.add_picture_RL,RelativeLayout.class);
        TextView textMum = holder.get(R.id.text_num,TextView.class);
        pictureCleanImg.setTypeface(mFragment.iconfont);
        pictureAddImg.setTypeface(mFragment.iconfont);
        if(it.isIsfoold()){
            pictureRL.setVisibility(View.GONE);
            addPictureRL.setVisibility(View.VISIBLE);
            addPictureRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.adapterClick(v);
                }
            });
        }else {
            if(!Tools.isEmpty(it.getNum())){
                textMum.setText(it.getNum());
            }
            pictureRL.setVisibility(View.VISIBLE);
            addPictureRL.setVisibility(View.GONE);
            Glide.with(this.mContext).load(it.getUrl()).into(pictureImg);
            pictureRL.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    PictureDialog pictureDialog = new PictureDialog(mFragment,mContext);
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
