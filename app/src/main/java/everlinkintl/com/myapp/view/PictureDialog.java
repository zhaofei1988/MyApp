package everlinkintl.com.myapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;

public class PictureDialog {
    private MyBsetActivity mActivity;
    private Context mContext;
    public PictureDialog(Activity activity, Context context){
        this.mActivity = (MyBsetActivity) activity;
        this.mContext = context;
    }
    public PictureDialog(Context context){
        this.mContext = context;
    }
    public void dialogIndex(String url){
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.picture_dialog_layout, null);
        //将界面填充到AlertDiaLog容器
        setDeBugDialog.setView(dialogView);
        //初始化控件
        ImageView pictureDialogImg = (ImageView) dialogView.findViewById(R.id.picture_dialog_img);
        TextView pictureDialogCloseImg = (TextView) dialogView.findViewById(R.id.picture_dialog_close_img);
        Glide.with(this.mContext).load(url).into(pictureDialogImg);
        pictureDialogCloseImg.setTypeface(mActivity.iconfont);
        //取消点击外部消失弹窗
        setDeBugDialog.setCancelable(false);
        //创建AlertDiaLog
        setDeBugDialog.create();
        final AlertDialog customAlert = setDeBugDialog.show();
        customAlert.getWindow().setBackgroundDrawable(mActivity.getDrawable(R.color.c00000000));
        pictureDialogCloseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAlert.dismiss();
            }
        });

    }
}
