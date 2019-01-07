package com.lauren.simplenews.news.presenter;

import com.lauren.simplenews.beans.NewsBean;
import com.lauren.simplenews.news.model.INewsModel;
import com.lauren.simplenews.news.model.NewsModel;
import com.lauren.simplenews.news.view.INewsTop;

import java.util.LinkedList;
import java.util.List;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/25
 * 描述：
 */
public class NewsTopPresenter implements INewsTopPresenter {
    private INewsTop iNewsTop;
    private INewsModel iNewsModel;
    public NewsTopPresenter(INewsTop iNewsTop){
        this.iNewsTop = iNewsTop;
        this.iNewsModel = new NewsModel(this);
    }
    @Override
    public void getDate(String url,int page,int state) {
        String realUrl = url+String.valueOf(page);
        iNewsModel.getDate(realUrl,state);
    }


    @Override
    public void showDate(LinkedList<NewsBean> list,int state) {
        iNewsTop.showDate(list,state);
    }
}
