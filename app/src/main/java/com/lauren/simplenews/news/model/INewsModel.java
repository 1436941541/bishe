package com.lauren.simplenews.news.model;

import com.lauren.simplenews.beans.NewsBean;

import java.util.List;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/25
 * 描述：
 */
public interface INewsModel {
        List<NewsBean> getDate(String url,int state);
}
