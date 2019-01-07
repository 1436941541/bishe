package com.lauren.simplenews.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/7
 * 描述：
 */
public class NewsBean implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * id

     */
    @SerializedName("id")
    private int id;
    /**
     * 第一标题
     */
    @SerializedName("firstTitle")
    private String firstTitle;
    /**
     * 详情网址
     */
    @SerializedName("newDetailUrl")
    private String newDetailUrl;
    /**
     * 第二标题
     */
    @SerializedName("secondTitle")
    private String secondTitle;
    /**
     * 时间
     */
    @SerializedName("time")
    private String time;
    /**
     * 图片地址
     */
    @SerializedName("imageUrl")
    private String imageUrl;



    public String getFirstTitle() {
        return firstTitle;
    }

    public void setFirstTitle(String firstTitle) {
        this.firstTitle = firstTitle;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imgUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewDetailUrl() {
        return newDetailUrl;
    }

    public void setNewDetailUrl(String newDetailUrl) {
        this.newDetailUrl = newDetailUrl;
    }

}
