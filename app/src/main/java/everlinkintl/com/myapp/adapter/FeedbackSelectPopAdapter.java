package everlinkintl.com.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.user.FeedbackActivity;
import everlinkintl.com.myapp.datatemplate.FeedbackSelectData;

public class FeedbackSelectPopAdapter extends MyBaseAdapter {
    private FeedbackActivity mFeedbackActivity;
    public FeedbackSelectPopAdapter(Context context, FeedbackActivity feedbackActivity) {
        super(context);
        this.mFeedbackActivity =feedbackActivity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
        return R.layout.feedback_select_pop_item_layout;
    }

    @Override
    public void handleItem(int itemViewType, final int position, Object item, ViewHolder holder, boolean isRecycle) {
       final FeedbackSelectData feedbackSelectData =(FeedbackSelectData) item;
        TextView textView = holder.get(R.id.feedback_pop_item_tv,TextView.class);
        if(feedbackSelectData.isSelect()){
            textView.setTextColor(mFeedbackActivity.getResources().getColor(R.color.c4169E1));
        }else {
            textView.setTextColor(mFeedbackActivity.getResources().getColor(R.color.c666666));
        }
        LinearLayout layout = holder.get(R.id.feedback_pop_item_ly,LinearLayout.class);
        textView.setText(feedbackSelectData.getSelectString());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedbackActivity.popSeltctClick(feedbackSelectData);
            }
        });
    }
}
