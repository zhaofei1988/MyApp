package everlinkintl.com.myapp.view;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.user.FeedbackActivity;
import everlinkintl.com.myapp.adapter.FeedbackSelectPopAdapter;

public class FeedbackSelectPop {
        private ListView listView;
        private FeedbackActivity mFeedbackActivity;
        private PopupWindow window;
        private boolean mIsShowing = false;
        private FeedbackSelectPopAdapter feedbackSelectPopAdapter;
        public FeedbackSelectPop(FeedbackActivity fragmentFourth) {
            this.mFeedbackActivity = fragmentFourth;
        }
        private void initPopup() {
            View pop = mFeedbackActivity.getLayoutInflater().inflate(R.layout.feedback_select_pop_layout, null);
            window = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setTouchable(true);
            window.setOutsideTouchable(true);
            window.setFocusable(true);
            window.setClippingEnabled(false);
            window.setBackgroundDrawable(mFeedbackActivity.getDrawable(R.color.c00000000));
            window.setAnimationStyle(R.style.anim_menu_bottombar);
            mIsShowing = false;
            listView = (ListView) pop.findViewById(R.id.feedback_pop_list);
            Button button = (Button) pop.findViewById(R.id.feedback_pop_close_bt);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            if(feedbackSelectPopAdapter ==null){
                feedbackSelectPopAdapter =
                        new FeedbackSelectPopAdapter(mFeedbackActivity.getApplicationContext(),mFeedbackActivity);
            }
            feedbackSelectPopAdapter.setData(mFeedbackActivity.list);
            listView.setAdapter(feedbackSelectPopAdapter);
            feedbackSelectPopAdapter.notifyDataSetChanged();
        }

        public void show(View view) {
            if (window == null) {
                initPopup();
            }
            if (!mIsShowing) {
                window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                mIsShowing = true;
            }
        }

        public void dismiss() {
            if (window != null && mIsShowing) {
                window.dismiss();
                mIsShowing = false;
            }
        }
    }
