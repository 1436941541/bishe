package com.lauren.simplenews.utils;

import android.content.Context;
import android.util.Log;
import com.lauren.simplenews.application.MyApplication;
import com.lauren.simplenews.beans.NewsBean;
import java.io.File;
import java.util.LinkedList;
import java.util.Objects;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2019/1/4
 * 描述：新闻缓存类
 */
public class ConfigCache {
    private static final String TAG = ConfigCache.class.getName();
    public static final int CONFIG_CACHE_MOBILE_TIMEOUT  = 3600000;  //1 hour

    public static LinkedList<NewsBean> getNewsCache() {
        LinkedList<NewsBean> result = null;
        File file = new File(MyApplication.getContext().getCacheDir(),"news_cache");
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            Log.d(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime/60000 + "min");
            //1. in case the system time is incorrect (the time is turn back long ago)
            //2. when the network is invalid, you can only read the cache
            if (MyApplication.isNetworkConnected(MyApplication.getContext()) && expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
                return null;
            }
            result = FileUtil.getFile(file);
        }
        return result;
    }

//    public static void setUrlCache(String data, String url) {
//        File file = new File(AppApplication.mSdcardDataDir + "/" + getCacheDecodeString(url));
//        try {
//            //创建缓存数据到磁盘，就是创建文件
//            FileUtils.writeTextFile(file, data);
//        } catch (IOException e) {
//            Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
//            e.printStackTrace();
//        }
//    }


    public static void putCacheToFile(LinkedList<NewsBean> list) {
        FileUtil.saveFile(list,"news_cache");
    }
}
