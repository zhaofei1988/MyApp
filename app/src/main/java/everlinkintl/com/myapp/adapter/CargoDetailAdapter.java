package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.transport.CargoDetailActivity;
import everlinkintl.com.myapp.datatemplate.CargoDetailData;
import everlinkintl.com.myapp.view.NoScrollGridView;

public class CargoDetailAdapter extends MyBaseAdapter {
    Context mContext;
    CargoDetailActivity mCargoDetailActivity;
    public CargoDetailAdapter(Context context, CargoDetailActivity cargoDetailActivity) {
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
        return R.layout.cargo_detail_item;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
        CargoDetailData cargoDetailData = (CargoDetailData) item;
        TextView address = holder.get(R.id.cargo_detail_item_address,TextView.class);
        TextView time = holder.get(R.id.cargo_detail_item_time,TextView.class);
        Button status = holder.get(R.id.cargo_detail_item_status,Button.class);
        NoScrollGridView noScrollGridView = holder.get(R.id.cargo_detail_item_gridView,NoScrollGridView.class);
        CargoDetailGridViewAdapter cargoDetailGridViewAdapter = new CargoDetailGridViewAdapter(mContext,mCargoDetailActivity);
        cargoDetailGridViewAdapter.setData(cargoDetailData.getImgUrl());
        noScrollGridView.setAdapter(cargoDetailGridViewAdapter);
        cargoDetailGridViewAdapter.notifyDataSetChanged();
    }
}
