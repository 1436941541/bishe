package com.lauren.simplenews.commons;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/31
 * 描述：一些定义为staic final的变量，我将其都封装在了这个类中
 */
public class SomeConstant {
    /**
     * 这两个参数代表的是登录是的状态，是否只有账号，或者只有密码
     */
    public static final int NONE = 0;
    public static final int ONLY_HAVE_USER = 1;
    public static final int ONLY_HAVE_PASSWORD = 2;
    public static final int IS_OK = 3;
    public static final int WRONG = 4;
    public static final int RETURNLOGIN = 100;
}
