package com.lauren.simplenews.news.view;

import com.lauren.simplenews.beans.NewsBean;

import java.util.LinkedList;
import java.util.List;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/25
 * 描述：
 */
public interface INewsTop {
    void showLoading();

    void hideLoading();

    void showDate(LinkedList<NewsBean> newsBeans, int state);
}
