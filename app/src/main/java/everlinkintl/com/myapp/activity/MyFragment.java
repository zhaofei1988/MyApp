package everlinkintl.com.myapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import everlinkintl.com.myapp.R;
import everlinkintl.com.myapp.common.Cons;
import everlinkintl.com.myapp.common.Tools;

public abstract class MyFragment extends Fragment {
    protected View mRoot;
    protected Unbinder mRootUnBinder;
    private boolean mReceiverTag = false;   //广播接受者标识
    public Typeface iconfont;
    public TextView toolbarBreak;
    public TextView toolbarTitle;
    public TextView toolbarRigthTv;
    // 标示是否第一次初始化数据
    protected boolean mIsFirstInitData = true;
    private BroadcastReceiver  broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Cons.RECEIVER_ACTION)) {
                String locationResult = intent.getStringExtra(Cons.RECEIVER_PUT_RSULT);
                 if (null != locationResult && !locationResult.trim().equals("")) {
                    setData(locationResult);
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        if (mReceiverTag) {   //判断广播是否注册
            mReceiverTag = false;   //Tag值 赋值为false 表示该广播已被注销
            getContext().unregisterReceiver(broadcastReceiver1);
        }
        if(mRootUnBinder!=null){
            mRootUnBinder.unbind();
        }
        super.onDestroy();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layId = getContentLayoutId();
            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            View root = inflater.inflate(layId, container, false);
            initWidows(root);
            initWidget(root);
            mRoot = root;

        } else {
            if (mRoot.getParent() != null) {
                // 把当前Root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        Cons.sp =getContext().getSharedPreferences(Cons.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsFirstInitData) {
            // 触发一次以后就不会触发
            mIsFirstInitData = false;
        }
        // 当View创建完成后初始化数据
        initData();
    }

    /**
     * 得到当前界面的资源文件Id
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();

    protected abstract void setData(String s);

    /**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              i
     * 初始化控件
     */
    protected void initWidget(View root) {
        mRootUnBinder = ButterKnife.bind(this, root);
    }
    /**
     * 初始化数据
     */
    protected void initData() {
        if (!mReceiverTag) {     //在注册广播接受者的时候 判断是否已被注册,避免重复多次注册广播
            IntentFilter filter = new IntentFilter();
            mReceiverTag = true;
            filter.addAction(Cons.RECEIVER_ACTION);
            getContext().registerReceiver(broadcastReceiver1, filter);
        }

    }
    /**
     * 设置title
     * @param title ：title
     */
    protected void setTitleName(String title) {
        if (!Tools.isEmpty(toolbarTitle)) {
            toolbarTitle.setText(title);
        }
    }
    /**
     * 设置返回键
     */
    protected void setGoneBreak() {
        if (!Tools.isEmpty(toolbarBreak)) {
            toolbarBreak.setVisibility(View.GONE);
        }
    }
    protected void sendBroadcastToServiceNoLoding(String st) {
        Intent intent = new Intent(Cons.RECEIVER_ACTION_SERVER);
        intent.putExtra(Cons.RECEIVER_PUT_RSULT, st);
        getContext().sendBroadcast(intent);
    }
    protected void sendBroadcastToServiceLoding(String st) {
        Intent intent = new Intent(Cons.RECEIVER_ACTION_SERVER);
        intent.putExtra(Cons.RECEIVER_PUT_RSULT, st);
        getContext().sendBroadcast(intent);
//        LodingDialog.dialogIndex(getContext());
    }
    private int getStatusBarHeight() {
        int result = 20;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    protected void initWidows(View view){
        iconfont = Typeface.createFromAsset(getActivity().getAssets(), "iconfont.ttf");
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (!Tools.isEmpty(toolbar)) {
            toolbar.setPadding(0,getStatusBarHeight()+10,0,0);
            toolbar.setTitle("");
            toolbar.setTitleTextColor(Color.WHITE);
        }
        toolbarBreak = view.findViewById(R.id.toolbar_break_tv);
        toolbarTitle = view.findViewById(R.id.toolbar_title_tv);
        toolbarRigthTv =  view.findViewById(R.id.toolbar_rigth_tv);
        if (!Tools.isEmpty(toolbarBreak)) {
            toolbarBreak.setTypeface(iconfont);
            toolbarBreak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }
    }
    protected void setTitleRigthTv(String title, View.OnClickListener onClickListener) {
        if (!Tools.isEmpty(toolbarRigthTv)) {
            toolbarRigthTv.setVisibility(View.VISIBLE);
            toolbarRigthTv.setText(title);
            toolbarRigthTv.setOnClickListener(onClickListener);
        }else {
            toolbarRigthTv.setVisibility(View.GONE);
        }
    }
}
