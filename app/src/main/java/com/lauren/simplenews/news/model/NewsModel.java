package com.lauren.simplenews.news.model;
import android.widget.Toast;

import com.lauren.simplenews.application.MyApplication;
import com.lauren.simplenews.beans.NewsBean;
import com.lauren.simplenews.commons.Urls;
import com.lauren.simplenews.news.presenter.INewsTopPresenter;
import com.lauren.simplenews.utils.ConfigCache;
import com.lauren.simplenews.utils.JsonUtils;
import com.lauren.simplenews.utils.okhttp3.MyOkhttpClient;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/25
 * 描述：
 */
public class NewsModel implements INewsModel {
    private INewsTopPresenter newsTopPresenter;
    public NewsModel(INewsTopPresenter iNewsTopPresenter){
        this.newsTopPresenter = iNewsTopPresenter;
    }

    @Override
    public List<NewsBean> getDate(String url, final int state) {
        if (MyApplication.isNetworkConnected(MyApplication.getContext())){
            MyOkhttpClient.getInstance().asyncGet(url, new MyOkhttpClient.HttpCallBack() {
                @Override
                public void onError(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onSuccess(Response response, Request request, String result) {
                    if (response.code()==200){
                        LinkedList<NewsBean> list = JsonUtils.getListPersonByGson(result);
                        ConfigCache.putCacheToFile(list);
                        newsTopPresenter.showDate(list,state);
                        Urls.page++;
                    }
                }
            });
        }
        else {
            LinkedList<NewsBean> cacheConfigString = ConfigCache.getNewsCache();
//        //根据结果判定是读取缓存，还是重新读取
            if (cacheConfigString != null) {
                showConfig(cacheConfigString);
            }
        }

//        else {

        return null;
    }

    private void showConfig(LinkedList<NewsBean> cacheConfigString) {
        newsTopPresenter.showDate(cacheConfigString,4);
    }
}
