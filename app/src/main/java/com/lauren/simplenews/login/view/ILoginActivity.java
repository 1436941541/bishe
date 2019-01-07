package com.lauren.simplenews.login.view;

import android.content.Intent; /**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/31
 * 描述：
 */
public interface ILoginActivity {
    void showResult(int code);

    void registerActivity(Intent intent);
}
