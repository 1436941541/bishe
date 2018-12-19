package com.lauren.simplenews.guide;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lauren.simplenews.R;
import com.lauren.simplenews.base.BaseActivity;
import com.lauren.simplenews.main.widget.MainActivity;
import com.lauren.simplenews.utils.CacheUtils;
import com.lauren.simplenews.utils.ToolsUtil;

import java.util.ArrayList;

/**
 * 导航页面
 */
public class GuideActivity extends BaseActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private int leftMax;//两点之间的距离
    private Button button;
    private ArrayList<ImageView> imageViews;//导航的图片
    private ImageView red_point;//导航图片的其中一个红点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int images[] = new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3,
        };
        imageViews = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(images[i]);
            imageViews.add(imageView);
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ToolsUtil.dip2px(context, 10), ToolsUtil.dip2px(context, 10));
            if (i > 0) {
                params.leftMargin = ToolsUtil.dip2px(context, 10);
            }
            point.setLayoutParams(params);
            linearLayout.addView(point);
        }
        viewPager.setAdapter(new GuideVPAdapter());
        red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
        viewPager.addOnPageChangeListener(new GuideVPChangeListener());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
                CacheUtils.putBoolean(context, "start_main");
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_guide);
        setStatusBarFullTransparent();
        viewPager = findViewById(R.id.vp_guide);
        linearLayout = findViewById(R.id.ll_guide);
        red_point = findViewById(R.id.red_point_guide);
        button = findViewById(R.id.bt_guide);
    }

    /**
     * 监听视图树的绘制过程，来计算两个点之间的距离
     */
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        /**
         * 执行不止一次
         */
        @Override
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
            leftMax = linearLayout.getChildAt(1).getLeft() - linearLayout.getChildAt(0).getLeft();
        }
    }

    class GuideVPChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 页面滑动时回调
         *
         * @param position             当前页面滑动的位置
         * @param positionOffset       页面滑动百分比
         * @param positionOffsetPixels 滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int leftMargin = (int) (position * leftMax + positionOffset * leftMax);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) red_point.getLayoutParams();
            params.leftMargin = leftMargin;
            red_point.setLayoutParams(params);
        }

        /**
         * 当页面被选中的时候回调
         *
         * @param position 被选中的页面的对应位置
         */
        @Override
        public void onPageSelected(int position) {
            if (position == 2) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
        }

        /**
         * viewpager页面滑动状态发生变化的时候
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class GuideVPAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * @param view   当前创建的视图
         * @param object 下面instantiateItem返回的结果值
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * 类似于listview中adapter的 getview ，但是需要add进去
         *
         * @param container viewpager
         * @param position  要创建页面的位置
         * @return 返回合创建当前页面有关系的值, 意思就是可以不返回页面, 比如返回position，一般返回页面
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }
    }

}
