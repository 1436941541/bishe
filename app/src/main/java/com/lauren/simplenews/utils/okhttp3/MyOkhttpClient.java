package com.lauren.simplenews.utils.okhttp3;


import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/7
 * 描述：okhttp3的封裝
 */
public class MyOkhttpClient {
    private static MyOkhttpClient myOkhttpClient;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private MyOkhttpClient() {
        okHttpClient = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static MyOkhttpClient getInstance() {
        if (myOkhttpClient == null) {
            synchronized (MyOkhttpClient.class) {
                if (myOkhttpClient == null) {
                    myOkhttpClient = new MyOkhttpClient();
                }
            }
        }
        return myOkhttpClient;
    }

    class StringCallBack implements Callback {
        private HttpCallBack httpCallBack;
        private Request request;

        public StringCallBack(Request request, HttpCallBack httpCallBack) {
            this.request = request;
            this.httpCallBack = httpCallBack;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            final IOException fe = e;
            if (httpCallBack != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onError(request, fe);
                    }
                });
            }
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            final String result = response.body().string();
            if (httpCallBack != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onSuccess(response, request, result);
                    }
                });
            }
        }


    }

    public void asyncGet(String url, HttpCallBack httpCallBack) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }

    public void asyncPost(String url, HttpCallBack httpCallBack, FormBody formBody) {
        Request request = new Request.Builder().url(url).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }

    public interface HttpCallBack {
        void onError(Request request, IOException e);

        void onSuccess(Response response, Request request, String result);
    }
}
