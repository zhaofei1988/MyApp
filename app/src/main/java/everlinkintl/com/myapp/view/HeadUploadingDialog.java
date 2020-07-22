package everlinkintl.com.myapp.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.fragment.FragmentFourth;

public class HeadUploadingDialog {
    private FragmentFourth mFragmentFourth;
    private PopupWindow window;
    private boolean mIsShowing = false;

    public HeadUploadingDialog(FragmentFourth fragmentFourth) {
        this.mFragmentFourth = fragmentFourth;
    }


    private void initPopup() {
        View pop = mFragmentFourth.getLayoutInflater().inflate(R.layout.head_uploading_dialog_layout, null);
        window = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setClippingEnabled(false);
        window.setBackgroundDrawable(mFragmentFourth.getActivity().getDrawable(R.color.c00000000));
        window.setAnimationStyle(R.style.anim_menu_bottombar);
        mIsShowing = false;
        Button closeBt = (Button) pop.findViewById(R.id.uploding_close_bt);
        final Button photosBt = (Button) pop.findViewById(R.id.uploding_photos_bt);
        final Button closeAlbumBt = (Button) pop.findViewById(R.id.uploding_photo_album_bt);
        closeAlbumBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentFourth.dialogClickBack(closeAlbumBt);
                dismiss();
            }
        });
        photosBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentFourth.dialogClickBack(photosBt);
                dismiss();
            }
        });
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
