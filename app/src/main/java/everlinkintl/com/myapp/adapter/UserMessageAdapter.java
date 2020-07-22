package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.widget.TextView;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.datatemplate.UserMessageData;

public class UserMessageAdapter extends MyBaseAdapter {
    public UserMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.user_message_item_layout;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
        UserMessageData userMessageData = (UserMessageData) item;
        TextView name = holder.get(R.id.user_message_name,TextView.class);
        TextView content = holder.get(R.id.user_message_content,TextView.class);
        name.setText(userMessageData.getName());
        content.setText(userMessageData.getContent());
    }
}
