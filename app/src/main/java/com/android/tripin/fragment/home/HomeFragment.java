package com.android.tripin.fragment.home;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Felix on 6/8/2018.
 * Description: Home Fragment
 */

public class HomeFragment extends BaseFragment {

    private final static String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.tabs) QMUITabSegment mTabSegment;
    private HashMap<Pager, HomeController> mPages;

    enum Pager {
        PLAN, MAP, SHARE;

        public static Pager getPagerFromPositon(int position) {
            switch (position) {
                case 0:
                    return PLAN;
                case 1:
                    return MAP;
                case 2:
                    return SHARE;
                default:
                    return MAP;
            }
        }
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        private int mChildCount = 0;

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mPages.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            HomeController page = mPages.get(Pager.getPagerFromPositon(position));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(page, params);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount == 0) {
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
    };


    /**
     * onCreateView
     */
    @Override
    protected View onCreateView() {
        @SuppressLint("InflateParams") FrameLayout layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, layout);
        initTabs();
        initPagers();
        return layout;
    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_blue);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
//        mTabSegment.setDefaultTabIconPosition(QMUITabSegment.ICON_POSITION_BOTTOM);

//        // 如果你的 icon 显示大小和实际大小不吻合:
//        // 1. 设置icon 的 bounds
//        // 2. Tab 使用拥有5个参数的构造器
//        // 3. 最后一个参数（setIntrinsicSize）设置为false
//        int iconShowSize = QMUIDisplayHelper.dp2px(getContext(), 20);
//        Drawable normalDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component);
//        normalDrawable.setBounds(0, 0, iconShowSize, iconShowSize);
//        Drawable selectDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component_selected);
//
//        selectDrawable.setBounds(0, 0, iconShowSize, iconShowSize);
//
//        QMUITabSegment.Tab component = new QMUITabSegment.Tab(
//                normalDrawable,
//                normalDrawable,
//                "Components", false, false
//        );

        QMUITabSegment.Tab plan = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component),
                ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component_selected),
                "Plan", false
        );

        QMUITabSegment.Tab map = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_util),
                ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_util_selected),
                "Map", false
        );
        QMUITabSegment.Tab share = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_lab),
                ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_lab_selected),
                "Share", false
        );
        mTabSegment.addTab(plan)
                .addTab(map)
                .addTab(share);
    }

    private void initPagers() {

        HomeController.HomeControlListener listener = new HomeController.HomeControlListener() {
            @Override
            public void startFragment(BaseFragment fragment) {
                HomeFragment.this.startFragment(fragment);
            }
        };

        mPages = new HashMap<>();

        HomeController homeComponentsController = new HomePlanController(getActivity());
        homeComponentsController.setHomeControlListener(listener);
        mPages.put(Pager.PLAN, homeComponentsController);

        HomeController homeUtilController = new HomeMapController(getActivity());
        homeUtilController.setHomeControlListener(listener);
        mPages.put(Pager.MAP, homeUtilController);

        HomeController homeLabController = new HomeShareController(getActivity());
        homeLabController.setHomeControlListener(listener);
        mPages.put(Pager.SHARE, homeLabController);

        mViewPager.setAdapter(mPagerAdapter);
        mTabSegment.setupWithViewPager(mViewPager, false);
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }
}