package everlinkintl.com.myapp.activity.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.activity.MyApplication;
import everlinkintl.com.myapp.activity.MyBsetActivity;
import everlinkintl.com.myapp.adapter.MyFragmentPagerAdapter;
import everlinkintl.com.myapp.common.Tools;
import everlinkintl.com.myapp.datatemplate.BasicData;
import everlinkintl.com.myapp.newdata.GrabTasksCountData;
import io.reactivex.annotations.Nullable;

public class MainActivity extends MyBsetActivity {

    private long exitTime = 0;
    // tab 布局 列表
    @Nullable
    @BindViews({R.id.first_tab, R.id.second_tab, R.id.third_tab, R.id.four_tab})
    public List<RelativeLayout> tabLLoutList;
    public List<Integer> tabLLoutListId = new ArrayList<>();
    // tab 里面的 图片
    @Nullable
    @BindViews({R.id.first_icon_tv, R.id.second_icon_tv, R.id.third_icon_tv, R.id.four_icon_tv})
    public List<TextView> tabTvList1;
    // tab 里面得 文案
    @Nullable
    @BindViews({R.id.first_tv, R.id.second_tv, R.id.third_tv, R.id.four_tv})
    public List<TextView> tabTvList;
    @Nullable
    @BindView(R.id.qiangdan_messagess1)
    public TextView qiangdanMessage;

    // view pagwe
    @Nullable
    @BindView(R.id.my_viewpager)
    public ViewPager viewPager;
    //Fragment 集合
    private List<Fragment> mFragmentList = new ArrayList<>();
    //FragmentAdapter
    private MyFragmentPagerAdapter mFragmentAdapter;
    @Nullable
    @BindString(R.string.exit)
    String exit;
    @Nullable
    @BindColor(R.color.c4169E1)
    int colorC4169E1;
    @Nullable
    @BindColor(R.color.c666666)
    int colorC666666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
    }

    // 页面样式
    private void setView() {
        for (int s = 0; s < tabLLoutList.size(); s++) {
            tabLLoutListId.add(tabLLoutList.get(s).getId());
        }
        for (int a = 0; a < tabTvList1.size(); a++) {
            tabTvList1.get(a).setTypeface(iconfont);
        }
        FragmentFirst fragmentFirst = new FragmentFirst();
        FragmentSecond fragmentSecond = new FragmentSecond();
//        FragmentThird fragmentThird = new FragmentThird();
        FragmentQiangdan fragmentThird = new FragmentQiangdan();
        FragmentFourth fragmentFourth = new FragmentFourth();
        mFragmentList.add(fragmentFirst);
        mFragmentList.add(fragmentSecond);
        mFragmentList.add(fragmentThird);
        mFragmentList.add(fragmentFourth);
        mFragmentAdapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager(), (ArrayList<Fragment>) mFragmentList);
        viewPager();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setData(String string) {
        if (!Tools.isEmpty(string)) {
            Gson gson = new Gson();
            BasicData basicData1 = gson.fromJson(string, BasicData.class);
            if (basicData1.getCode() == Tools.code().get("grabTasksCount")) {
                GrabTasksCountData grabTasksCountData = gson.fromJson(basicData1.getObject(), GrabTasksCountData.class);
                if (!Tools.isEmpty(grabTasksCountData.getContent())){
                    if (grabTasksCountData.getContent().equals("0")) {
                    qiangdanMessage.setVisibility(View.GONE);
                } else {
                    qiangdanMessage.setVisibility(View.VISIBLE);
                    qiangdanMessage.setText(grabTasksCountData.getContent());
                }
                }
            }
        }
    }

    @Override
    public void jump() {
        if (!Tools.isEmpty(setCurrent) && setCurrent.equals("1")) {
            viewPager.setCurrentItem(1);
            setCurrent = null;
        }
        super.jump();
    }

    private void viewPager() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面在滑动过程中不停触发该方法：position：当前滑动到的位置，positionOffset：偏移量的百分比，positionOffsetPixels：偏移量的数值
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                currentFragment(i);
            }

            //ViewPager跳转到新页面时触发该方法，position表示新页面的位置。
            @Override
            public void onPageSelected(int i) {
                for (int s = 0; s < tabTvList1.size(); s++) {
                    if (s == i) {
                        tabTvList1.get(s).setTextColor(colorC4169E1);
                        tabTvList.get(s).setTextColor(colorC4169E1);
                    } else {
                        tabTvList1.get(s).setTextColor(colorC666666);
                        tabTvList.get(s).setTextColor(colorC666666);
                    }
                }
            }

            //当页面的滑动状态改变时该方法会被触发，页面的滑动状态有3个：“0”表示什么都不做，“1”表示开始滑动，“2”表示结束滑动。
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //把数据传递给适配器中，进行数据处理。
        viewPager.setAdapter(mFragmentAdapter);
    }

    @OnClick({R.id.first_tab, R.id.second_tab, R.id.third_tab, R.id.four_tab})
    public void onViewClicked(View view) {
        int indexof = tabLLoutListId.indexOf(view.getId());
        viewPager.setCurrentItem(indexof);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        sendBroadcastToService("closeService");
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Tools.ToastsShort(getApplicationContext(), exit);
            exitTime = System.currentTimeMillis();
        } else {
            ((MyApplication) getApplication()).finishAll();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentFourth fragment = (FragmentFourth) mFragmentList.get(3);
        fragment.activityResult(requestCode, resultCode, data);
    }


}
