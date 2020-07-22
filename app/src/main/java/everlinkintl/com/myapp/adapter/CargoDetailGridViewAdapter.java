package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.transport.CargoDetailActivity;
import everlinkintl.com.myapp.view.PictureDialog;

public class CargoDetailGridViewAdapter extends MyBaseAdapter {
    Context mContext;
    CargoDetailActivity mCargoDetailActivity;
    public CargoDetailGridViewAdapter(Context context , CargoDetailActivity cargoDetailActivity) {
        super(context);
        this.mContext =context;
        this.mCargoDetailActivity =cargoDetailActivity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.cargo_detail_gridview_item;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
       final String st = (String) item;
        ImageView imageView = holder.get(R.id.cargo_detail_item_gridView_img,ImageView.class);
        Glide.with(mContext).load(st).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureDialog pictureDialog = new PictureDialog(mCargoDetailActivity,mContext);
                pictureDialog.dialogIndex(st);
            }
        });
    }
}
