package com.exple.ex_elli.myapp;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
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
        // 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
        //此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，
        // 请使用参数：SpeechConstant.APPID +"=12345678," + SpeechConstant.FORCE_LOGIN +"=true"。
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59adfe65");

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

    }

}
