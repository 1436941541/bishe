package com.lauren.simplenews.utils;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.lauren.simplenews.application.MyApplication;
import com.lauren.simplenews.beans.NewsBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 *
 * 文件工具类
 */

public class FileUtil {

    /**
     * 字符串保存到手机内存设备中
     *
     * @param str
     */
    public static void saveFile(LinkedList<NewsBean> str, String fileName) {
        // 创建String对象保存文件名路径
        try {
            File file = new File(MyApplication.getContext().getCacheDir(), fileName);
            Log.d("yyj",file.getPath());
            // 如果文件不存在
            if (file.exists()) {
                // 创建新的空文件
                file.delete();
            }
            file.createNewFile();
            // 获取文件的输出流对象
            FileOutputStream outStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
            // 获取字符串对象的byte数组并写入文件流
            objectOutputStream.writeObject(str);
            // 最后关闭文件输出流
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 删除已存储的文件
     */
    public static void deletefile(String fileName) {
        try {
            // 找到文件所在的路径并删除该文件
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取文件里面的内容
     *
     * @return
     */
    public static LinkedList<NewsBean> getFile(File file) {
        try {
            // 创建FileInputStream对象
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            LinkedList<NewsBean> linkedList= (LinkedList<NewsBean>) ois.readObject();
            return linkedList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}