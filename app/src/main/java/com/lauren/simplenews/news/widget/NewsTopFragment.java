package com.lauren.simplenews.news.widget;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.lauren.simplenews.R;
import com.lauren.simplenews.base.BaseFragment;
import com.lauren.simplenews.beans.NewsBean;
import com.lauren.simplenews.beans.Person;
import com.lauren.simplenews.commons.Urls;
import com.lauren.simplenews.news.EndlessRecyclerOnScrollListener;
import com.lauren.simplenews.news.adapter.NewsAdapter;
import com.lauren.simplenews.news.presenter.INewsTopPresenter;
import com.lauren.simplenews.news.presenter.NewsTopPresenter;
import com.lauren.simplenews.news.view.INewsTop;
import com.lauren.simplenews.utils.JsonUtils;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/6
 * 描述：新闻头条的fragment
 */
public class NewsTopFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,INewsTop {
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private NewsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinkedList<NewsBean> mData;
    private INewsTopPresenter iNewsTopPresenter = new NewsTopPresenter(this);

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//根据请求是否通过的返回码进行判断，然后进一步运行程序
        if (grantResults.length > 0 && requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(),"权限获取成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        return getContentView();
    }

    @Override
    public void showLoading() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showDate(LinkedList<NewsBean> newsBeans,int state) {
        mAdapter.setLoadState(mAdapter.LOADING);
        if (state == 0 || state == 3){//上啦加载和初始化进来
            mAdapter.setLast(newsBeans);
        }
        else if (state == 1){//下拉刷新
            mAdapter.setFirst(newsBeans);
        }
        else if (state == 4){//缓存
            mAdapter.setmData(newsBeans);
        }
        mData = mAdapter.getData();
    }

    @Override
    protected void init() {
        setRefresh();
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mData = new LinkedList<>();
        mAdapter = new NewsAdapter(context);
        iNewsTopPresenter.getDate(Urls.DONGQIUDI,Urls.page,3);//默认初始进来
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(context) {
            @Override
            public void onLoadMore() {
                mAdapter.setLoadState(mAdapter.LOADING);
                if (mData.getLast().getId() > 40) {
                    iNewsTopPresenter.getDate(Urls.DONGQIUDI,Urls.page,0);
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
    }

    private void setRefresh() {
        mSwipeRefreshWidget = findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        showLoading();
    }

//    class MyRunnable implements Runnable {
//
//        @Override
//        public void run() {
//            MyOkhttpClient.getInstance().asyncGet(TOP_ID + "0-" + String.valueOf(TOP_ID_END) + ".html", new MyOkhttpClient.HttpCallBack() {
//                @Override
//                public void onError(Request request, IOException e) {
//                    Log.d("yyj", "onFailure: " + e);
//                }
//
//                @Override
//                public void onSuccess(Response response, Request request, String result) {
//                    Gson gson = new Gson();
//                    if (result.contains("403")) {
//                        Log.d("yyj", "onResponse: " + result);
//                        TOP_ID_END = TOP_ID_END + 10;
//                        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new MyRunnable());
//                    }
//                    try {
//                        JsonParser parser = new JsonParser();
//                        JsonObject jsonObj = parser.parse(result).getAsJsonObject();
//                        JsonElement jsonElement = jsonObj.get(Urls.TOP);
//                        if (jsonElement == null) {
//                            Log.d("yyj", "onResponse: wei kong");
//                        }
//                        JsonArray jsonArray = jsonElement.getAsJsonArray();
//                        for (int i = 1; i < jsonArray.size(); i++) {
//                            JsonObject jo = jsonArray.get(i).getAsJsonObject();
//                            if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
//                                continue;
//                            }
//                            if (jo.has("TAGS") && !jo.has("TAG")) {
//                                continue;
//                            }
//
//                            if (!jo.has("imgextra")) {
//                                NewsBean news = gson.fromJson(jo, NewsBean.class);
//                                mData.add(news);
//                            }
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mAdapter.setmData(mData);
//                                    mAdapter.setLoadState(mAdapter.LOADING);
//                                    mSwipeRefreshWidget.setRefreshing(false);
//                                }
//                            });
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_newslist;
    }


    @Override
    public void onRefresh() {
        iNewsTopPresenter.getDate(Urls.DONGQIUDI,Urls.page,1);
        hideLoading();
    }
}
