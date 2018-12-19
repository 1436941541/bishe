package com.lauren.simplenews.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lauren.simplenews.R;
import com.lauren.simplenews.base.BaseFragment;
import com.lauren.simplenews.beans.NewsBean;
import com.lauren.simplenews.commons.Urls;
import com.lauren.simplenews.news.EndlessRecyclerOnScrollListener;
import com.lauren.simplenews.news.adapter.NewsAdapter;
import com.lauren.simplenews.utils.ThreadPool.ThreadPoolProxyFactory;
import com.lauren.simplenews.utils.okhttp3.MyOkhttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

import static com.lauren.simplenews.commons.Urls.TOP_ID;
import static com.lauren.simplenews.commons.Urls.TOP_ID_END;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/6
 * 描述：新闻头条的fragment
 */
public class NewsTopFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private List<NewsBean> mData;
    private ArrayList<Runnable> arrayList;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mSwipeRefreshWidget = findViewById(R.id.swipe_refresh_widget);
        mRecyclerView = findViewById(R.id.recycle_view);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshWidget.post(new Runnable() {//仿知乎刚开始就加载数据
            @Override
            public void run() {
                mSwipeRefreshWidget.setRefreshing(true);
            }
        });
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mData = new ArrayList<>();
        getData();
        mAdapter = new NewsAdapter(context);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(context) {
            @Override
            public void onLoadMore() {
                mAdapter.setLoadState(mAdapter.LOADING);
                if (mData.size() < 100) {
                    getData();
                } else {
                    // 显示加载到底的提示
                    mAdapter.setLoadState(mAdapter.LOADING_END);
                }
            }
        });
        mAdapter.setmOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mData.size() <= 0) {
                    return;
                }
                NewsBean news = mData.get(position);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }
        });
        return getContentView();
    }

    /**
     * 獲取網絡上的數據
     */
    private void getData() {
        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new MyRunnable());
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            MyOkhttpClient.getInstance().asyncGet(TOP_ID + "0-" + String.valueOf(TOP_ID_END) + ".html", new MyOkhttpClient.HttpCallBack() {
                @Override
                public void onError(Request request, IOException e) {
                    Log.d("yyj", "onFailure: " + e);
                }

                @Override
                public void onSuccess(Response response, Request request, String result) {
                    Gson gson = new Gson();
                    if (result.contains("403")) {
                        Log.d("yyj", "onResponse: " + result);
                        TOP_ID_END = TOP_ID_END + 10;
                        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new MyRunnable());
                    }
                    try {
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObj = parser.parse(result).getAsJsonObject();
                        JsonElement jsonElement = jsonObj.get(Urls.TOP);
                        if (jsonElement == null) {
                            Log.d("yyj", "onResponse: wei kong");
                        }
                        JsonArray jsonArray = jsonElement.getAsJsonArray();
                        for (int i = 1; i < jsonArray.size(); i++) {
                            JsonObject jo = jsonArray.get(i).getAsJsonObject();
                            if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
                                continue;
                            }
                            if (jo.has("TAGS") && !jo.has("TAG")) {
                                continue;
                            }

                            if (!jo.has("imgextra")) {
                                NewsBean news = gson.fromJson(jo, NewsBean.class);
                                mData.add(news);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.setmData(mData);
                                    mAdapter.setLoadState(mAdapter.LOADING);
                                    mSwipeRefreshWidget.setRefreshing(false);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_newslist;
    }


    @Override
    public void onRefresh() {
        getData();
        mSwipeRefreshWidget.setRefreshing(false);
    }
}
