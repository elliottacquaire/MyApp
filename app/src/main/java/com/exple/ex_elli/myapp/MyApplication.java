package com.exple.ex_elli.myapp;

import android.app.Application;

import com.orhanobut.logger.Logger;


/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/8/31 16:29
 * 修改人：ex-lixuyang
 * 修改时间：2017/8/31 16:29
 * 修改备注：
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getName();
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG);
    }
}
