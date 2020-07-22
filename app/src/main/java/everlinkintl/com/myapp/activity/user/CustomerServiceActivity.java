package everlinkintl.com.myapp.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.common.Tools;

public class CustomerServiceActivity extends MyBsetActivity {
    @BindString(R.string.customer_service_string)
    String customerServiceString;
    @BindString(R.string.phone_num_string)
    String phoneNumString;
    @BindView(R.id.customer_service_icon)
    TextView customerServiceIcon;
    @Override
    protected int getContentLayoutId() {
        return R.layout.customer_service_layout;
    }

    @Override
    protected void setData(String string) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(customerServiceString);
        customerServiceIcon.setTypeface(iconfont);
    }
    @OnClick(R.id.call_phon_btn)
    public void onViewClicked(View view) {
        Tools.callPhone(phoneNumString,getApplicationContext());
    }
}
