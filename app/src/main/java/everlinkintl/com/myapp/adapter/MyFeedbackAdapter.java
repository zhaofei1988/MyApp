package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.widget.TextView;

import butterknife.BindArray;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.datatemplate.MyFeedbackData;

public class MyFeedbackAdapter extends MyBaseAdapter {
    String[] mFeedbackSelectArray;
    public MyFeedbackAdapter(Context context,String[] feedbackSelectArray) {
        super(context);
        this.mFeedbackSelectArray =feedbackSelectArray;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.my_feedback_item_layout;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
        MyFeedbackData myFeedbackData = (MyFeedbackData) item;
        TextView myFeedbackType = holder.get(R.id.my_feedback_type,TextView.class);
        TextView myFeedbackTime = holder.get(R.id.my_feedback_time,TextView.class);
        TextView myFeedbackContent = holder.get(R.id.my_feedback_content,TextView.class);
        myFeedbackType.setText(mFeedbackSelectArray[myFeedbackData.getType()]);
        myFeedbackTime.setText(myFeedbackData.getTime());
        myFeedbackContent.setText(myFeedbackData.getContent());
    }
}
