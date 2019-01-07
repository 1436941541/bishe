package com.lauren.simplenews.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lauren.simplenews.R;
import com.lauren.simplenews.base.BaseActivity;
import com.lauren.simplenews.login.presenter.ILoginPresenter;
import com.lauren.simplenews.login.presenter.LoginPresenter;
import com.lauren.simplenews.main.widget.MainActivity;

import static com.lauren.simplenews.commons.SomeConstant.NONE;
import static com.lauren.simplenews.commons.SomeConstant.ONLY_HAVE_PASSWORD;
import static com.lauren.simplenews.commons.SomeConstant.ONLY_HAVE_USER;
import static com.lauren.simplenews.commons.SomeConstant.RETURNLOGIN;
import static com.lauren.simplenews.commons.SomeConstant.WRONG;

public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginActivity {
    private EditText user;
    private EditText password;
    private Button login;
    private TextView forgetpw;
    private TextView register;
    private TextView errorpw;
    private ImageView weChat;
    private ILoginPresenter iLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("yyjj", "onCreate: ss");
        iLoginPresenter = new LoginPresenter(this);
        user.setOnClickListener(this);
        password.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgetpw.setOnClickListener(this);
        weChat.setOnClickListener(this);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_logn);
        setStatusBarFullTransparent();
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);
        forgetpw = findViewById(R.id.forgetPassword);
        register = findViewById(R.id.registered);
        errorpw = findViewById(R.id.errorpw);
        weChat = findViewById(R.id.wechat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                iLoginPresenter.login(user.getText().toString(),password.getText().toString());
                break;
            case R.id.registered:
                iLoginPresenter.register(LoginActivity.this);
                break;
            case R.id.forgetPassword:
                break;
            case R.id.wechat:break;
            default:break;
        }
    }

    @Override
    public void showResult(int code) {
        if (code == ONLY_HAVE_USER){
            setWaring("请输入密码");
        } else if (code == ONLY_HAVE_PASSWORD) {
            setWaring("请输入账号");
        } else if (code == NONE){
            setWaring("请输入账号和密码");
        } else if (code == WRONG){
            setWaring("错误的账号和密码");
        }else {
            errorpw.setVisibility(View.GONE);
            login.setText("正在登陆，请稍后");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void registerActivity(Intent intent) {
        startActivityForResult(intent,RETURNLOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RETURNLOGIN && resultCode == 200) {
            user.setText(data.getStringExtra("user"));
            password.setText(data.getStringExtra("password"));
        }
    }

    private void setWaring(String message){
        errorpw.setText(message);
        errorpw.setVisibility(View.VISIBLE);
    }
}
