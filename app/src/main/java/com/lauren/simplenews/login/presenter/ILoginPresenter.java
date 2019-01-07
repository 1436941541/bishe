package com.lauren.simplenews.login.presenter;

import android.app.Activity;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/31
 * 描述：
 */
public interface ILoginPresenter {
    void login(String user,String password);

    void retResult(int code);

    void register(Activity activity);
}
