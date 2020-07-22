package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.log.ViseLog;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.systemmessage.MessageDetileActivity;
import everlinkintl.com.myapp.datatemplate.MessageData;

public class MessageAdapter extends MyBaseAdapter {
    Context mContextntext;
    public MessageAdapter(Context context) {
        super(context);
        this.mContext =context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.tab_third_item_layout;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
        final MessageData messageData = (MessageData) item;
        TextView time = holder.get(R.id.message_time, TextView.class);
        TextView name = holder.get(R.id.message_name, TextView.class);
        TextView sketch = holder.get(R.id.message_sketch, TextView.class);
        LinearLayout layout = holder.get(R.id.message_detaile, LinearLayout.class);
        ViseLog.e(messageData.getMessageSketch());
        time.setText(messageData.getTime());
        name.setText(messageData.getMessageTitle());
        sketch.setText(messageData.getMessageSketch());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageDetileActivity.class);
                Gson gson =new Gson();
                String st = gson.toJson(messageData);
                intent.putExtra("tabThirdMessageData",st);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                mContext.startActivity(intent);

            }
        });

    }
}
