package com.lauren.simplenews.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lauren.simplenews.R;
import com.lauren.simplenews.base.BaseActivity;
import com.lauren.simplenews.guide.GuideActivity;
import com.lauren.simplenews.main.widget.MainActivity;
import com.lauren.simplenews.utils.CacheUtils;

/**
 * 起始引导页面
 */
public class SplashActivity extends BaseActivity {
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(relativeLayout, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(relativeLayout, "rotation", 0f, 360f),
                ObjectAnimator.ofFloat(relativeLayout, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(relativeLayout, "scaleY", 0f, 1f));
        animatorSet.setDuration(2000).start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                boolean isStartMain = CacheUtils.getBoolean(context, "start_main");
                Intent intent;
                if (isStartMain) {
                    intent = new Intent(context, MainActivity.class);
                } else {
                    intent = new Intent(context, GuideActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        setStatusBarFullTransparent();
        relativeLayout = findViewById(R.id.rl_splahs_root);
    }

}
