package com.android.tripin.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public abstract class HomeController extends FrameLayout {

    @BindView(R.id.topbar) QMUITopBar mTopBar;

    private HomeControlListener mHomeControlListener;

    public HomeController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.home_layout, this);
        ButterKnife.bind(this);
        initTopBar();
    }

    protected void startFragment(BaseFragment fragment) {
        if (mHomeControlListener != null) {
            mHomeControlListener.startFragment(fragment);
        }
    }

    public void setHomeControlListener(HomeControlListener homeControlListener) {
        mHomeControlListener = homeControlListener;
    }

    protected abstract String getTitle();

    private void initTopBar() {
        mTopBar.setTitle(getTitle());

        mTopBar.addLeftImageButton(R.mipmap.icon_topbar_about, R.id.topbar_right_about_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                UserFragment fragment = new UserFragment();
//                startFragment(fragment);
                Toast.makeText(getContext(), "User", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface HomeControlListener {
        void startFragment(BaseFragment fragment);
    }

}
