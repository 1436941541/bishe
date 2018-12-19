package com.lauren.simplenews.main.widget;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lauren.simplenews.R;
import com.lauren.simplenews.base.BaseActivity;
import com.lauren.simplenews.main.presenter.MainPresenter;
import com.lauren.simplenews.main.presenter.MainPresenterImpl;
import com.lauren.simplenews.main.view.MainView;
import com.lauren.simplenews.news.widget.NewsTopFragment;


public class MainActivity extends BaseActivity implements MainView {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;//ActionBarDrawerToggle 是
    // DrawerLayout.DrawerListener实现。和 NavigationDrawer 搭配使用
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerListener();
        setupDrawerContent(mNavigationView);
        mMainPresenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch2News();
    }

    /**
     * 设置抽屉的滑动
     */
    private void setDrawerListener() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //mDrawerLayout与mDrawerToggle搭配使用，实现炫酷效果
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            Toast.makeText(context, "暂时没做分享", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mMainPresenter.switchNavigation(menuItem.getItemId());//调用present层的来让view层换界面
                        menuItem.setChecked(true);//选中点击的
                        mDrawerLayout.closeDrawers();//关闭侧滑栏
                        return true;
                    }
                });
    }

    /*
     * 进去默认显示的是新闻这个碎片
     * */
    @Override
    public void switch2News() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new NewsTopFragment()).commit();
        mToolbar.setTitle(R.string.navigation_news);
    }

    @Override
    public void switch2Images() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ImageFragment()).commit();
        Log.d("how", "switch2Image: ");
//        mToolbar.setTitle(R.string.navigation_images);
    }

    @Override
    public void switch2Weather() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new WeatherFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_weather);
    }

    @Override
    public void switch2About() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AboutFragment()).commit();
        mToolbar.setTitle(R.string.navigation_about);
    }
}
