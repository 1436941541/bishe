package com.lauren.simplenews.login.model;

import android.util.Log;
import com.lauren.simplenews.commons.SomeConstant;
import com.lauren.simplenews.login.presenter.ILoginPresenter;
import com.lauren.simplenews.utils.okhttp3.MyOkhttpClient;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.lauren.simplenews.commons.Urls.LOGIN;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/31
 * 描述：
 */
public class LoginModel implements ILoginModel {
    ILoginPresenter iLoginPresenter;
    public LoginModel(ILoginPresenter iLoginPresenter){
        this.iLoginPresenter = iLoginPresenter;
    }

    @Override
    public void checkUser(String user, String password) {
        FormBody formBody = new FormBody.Builder().add("name",user)
                .add("password",password).build();
        MyOkhttpClient.getInstance().asyncPost(LOGIN, new MyOkhttpClient.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onSuccess(Response response, Request request, String result) {
                if (response.code() == 200) {
                    Log.d("yyj", "onSuccess: "+result);
                    if (result.equals("true")){
                        iLoginPresenter.retResult(SomeConstant.IS_OK);
                    }
                    else{
                        iLoginPresenter.retResult(SomeConstant.WRONG);
                    }
                }
            }
        }, formBody);
    }
}
