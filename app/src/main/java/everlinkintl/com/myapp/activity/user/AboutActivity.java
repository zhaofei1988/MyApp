package everlinkintl.com.myapp.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.BindString;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;

public class AboutActivity extends MyBsetActivity {
    @BindString(R.string.about)
    String about;
    @Override
    protected int getContentLayoutId() {
        return R.layout.about_layout;
    }

    @Override
    protected void setData(String string) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(about);
    }
}
