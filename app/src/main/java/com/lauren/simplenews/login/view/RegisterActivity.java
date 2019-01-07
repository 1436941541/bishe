package com.lauren.simplenews.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lauren.simplenews.R;
import com.lauren.simplenews.base.BaseActivity;
import com.lauren.simplenews.commons.SomeConstant;
import com.lauren.simplenews.commons.Urls;
import com.lauren.simplenews.utils.okhttp3.MyOkhttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {
    private EditText user;
    private ImageView back;
    private EditText password;
    private EditText passwordAgain;
    private Button register;
    private TextView returnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().length()>0 && password.getText().length()>0 && !password.getText().toString().equals(passwordAgain.getText().toString())){
                    Toast.makeText(context, "两次密码保持一致", Toast.LENGTH_SHORT).show();
                }
                if (user.getText().length()>0 && password.getText().length()>0 && password.getText().toString().equals(passwordAgain.getText().toString())){
                    RequestBody requestBody = new FormBody.Builder().add("name",user.getText().toString())
                            .add("password",password.getText().toString()).build();
                    MyOkhttpClient.getInstance().asyncPost(Urls.REGISTER, new MyOkhttpClient.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onSuccess(Response response, Request request, String result) {
                            if (response.code() == 200){
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.get("result").equals("success")){
                                        Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.putExtra("user",user.getText().toString());
                                        intent.putExtra("password",password.getText().toString());
                                        setResult(200,intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, (FormBody) requestBody);
                }
            }
        });
        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
        setStatusBarFullTransparent();
        password = findViewById(R.id.password);
        user = findViewById(R.id.user);
        back = findViewById(R.id.back);
        register = findViewById(R.id.login_button);
        returnLogin = findViewById(R.id.registered);
        passwordAgain = findViewById(R.id.passwordagain);
    }
}
