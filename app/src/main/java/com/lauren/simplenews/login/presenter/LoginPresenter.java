package com.lauren.simplenews.login.presenter;

import android.app.Activity;
import android.content.Intent;

import com.lauren.simplenews.commons.SomeConstant;
import com.lauren.simplenews.login.model.ILoginModel;
import com.lauren.simplenews.login.model.LoginModel;
import com.lauren.simplenews.login.view.ILoginActivity;
import com.lauren.simplenews.login.view.RegisterActivity;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/31
 * 描述：
 */
public class LoginPresenter implements ILoginPresenter {
    private ILoginActivity iLoginActivity;
    private ILoginModel iLoginModel;
    public LoginPresenter(ILoginActivity iLoginActivity){
        this.iLoginActivity = iLoginActivity;
        iLoginModel = new LoginModel(this);
    }

    @Override
    public void login(String user,String password) {
        if (user.length()==0 && password.length()==0){
            iLoginActivity.showResult(SomeConstant.NONE);
        } else if (user.length() >= 0 && password.length()==0) {
            iLoginActivity.showResult(SomeConstant.ONLY_HAVE_USER);
        }else if (user.length() == 0 && password.length()>=0){
            iLoginActivity.showResult(SomeConstant.ONLY_HAVE_PASSWORD);
        }else {
            iLoginModel.checkUser(user,password);
        }
    }

    @Override
    public void retResult(int code) {
            iLoginActivity.showResult(code);
    }

    @Override
    public void register(Activity activity) {
        Intent intent = new Intent(activity,RegisterActivity.class);
        iLoginActivity.registerActivity(intent);
    }
}
