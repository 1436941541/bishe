package com.lauren.simplenews.news.presenter;


import com.lauren.simplenews.beans.NewsBean;

import java.util.LinkedList;
import java.util.List;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/25
 * 描述：
 */
public interface INewsTopPresenter {

    void getDate(String url,int page,int state);

    void showDate(LinkedList<NewsBean> list, int state);
}
