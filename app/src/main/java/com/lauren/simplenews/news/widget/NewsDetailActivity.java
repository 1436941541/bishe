package com.lauren.simplenews.news.widget;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lauren.simplenews.R;
import com.lauren.simplenews.beans.NewsBean;
import com.lauren.simplenews.beans.NewsDetailBean;
import com.lauren.simplenews.commons.Urls;
import com.lauren.simplenews.utils.ImageLoaderUtils;
import com.lauren.simplenews.utils.JsonUtils;
import com.lauren.simplenews.utils.okhttp3.MyOkhttpClient;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;


public class NewsDetailActivity extends AppCompatActivity {
    private NewsBean mNews;
    private HtmlTextView mTVNewsContent;
    private ProgressBar mProgressBar;//加载视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mProgressBar = findViewById(R.id.progress);
        mTVNewsContent = findViewById(R.id.htNewsContent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mNews = (NewsBean) getIntent().getSerializableExtra("news");
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mNews.getFirstTitle());
        ImageLoaderUtils.display(getBaseContext(), (ImageView) findViewById(R.id.ivImage), mNews.getImageUrl());
        loadNewsDetail(mNews.getNewDetailUrl());
    }

    public void loadNewsDetail(final String url) {
        MyOkhttpClient.getInstance().asyncGet(url, new MyOkhttpClient.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                Log.d("yyj", "onError: " + e);
            }

            @Override
            public void onSuccess(Response response, Request request, String result) {
                if (response.code() == 200){
                    showNewsDetialContent(result);
                }
//                NewsDetailBean newsDetailBean = readJsonNewsDetailBeans(result, docid);
//                if (newsDetailBean != null) {
//                    showNewsDetialContent(newsDetailBean.getBody());
//                    mProgressBar.setVisibility(View.GONE);
//                }
            }
        });
    }

    public void showNewsDetialContent(String newsDetailContent) {
        mTVNewsContent.setHtml(newsDetailContent, new HtmlHttpImageGetter(mTVNewsContent));
        mProgressBar.setVisibility(View.GONE);
    }
}

