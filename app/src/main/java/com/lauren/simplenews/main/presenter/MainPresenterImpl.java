package com.lauren.simplenews.main.presenter;

import com.lauren.simplenews.main.view.MainView;
import com.lauren.simplenews.R;

public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                mMainView.switch2News();
                break;
            case R.id.navigation_item_social:
                mMainView.switch2Social();
                break;
            case R.id.navigation_item_friend:
                mMainView.switch2Friend();
                break;
            case R.id.navigation_item_about:
                mMainView.switch2About();
                break;
            case R.id.navigation_item_search:
                mMainView.switch2Find();
                break;
            case R.id.navigation_item_setting:
                mMainView.switch2Setting();
                break;
            default: break;
        }
    }
}
