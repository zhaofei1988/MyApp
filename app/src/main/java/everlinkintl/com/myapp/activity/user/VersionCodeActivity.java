package everlinkintl.com.myapp.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.activity.transport.ClaimingExpensesActivity;
import everlinkintl.com.myapp.common.QRCodeUtil;
import everlinkintl.com.myapp.common.Tools;

public class VersionCodeActivity extends MyBsetActivity {
    @BindViews({R.id.version_code_update_num_tv,R.id.version_code_update_next_tv,R.id.version_code_about_next_tv})
    List<TextView> list;
    @BindString(R.string.version_code)
    String versionCode;
    @BindView(R.id.version_code_img)
    ImageView imageView;
    @Override
    protected int getContentLayoutId() {
        return R.layout.version_code_layout;
    }

    @Override
    protected void setData(String string) {
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(versionCode);
        list.get(1).setTypeface(iconfont);
        list.get(2).setTypeface(iconfont);
        list.get(0).setText(Tools.getLocalVersionName(getApplicationContext()));
        Bitmap mBitmap=QRCodeUtil.createQRCodeBitmap("https://www.baidu.com",
                Tools.dp2px(getApplicationContext(),170),
                Tools.dp2px(getApplicationContext(),170));
        imageView.setImageBitmap(mBitmap);
    }
    @OnClick({R.id.version_code_update_ly,R.id.version_code_about_ly})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.version_code_update_ly:
                break;
            case R.id.version_code_about_ly:
                Intent intent = new Intent(getApplicationContext() ,AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
                break;
        }
    }
}
