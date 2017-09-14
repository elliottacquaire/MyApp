package com.exple.ex_elli.myapp.xunfeivoice;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.lang.ref.WeakReference;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/5 14:13
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/5 14:13
 * 修改备注：
 */

public class XunfeiSpeechCompound {
    // Log标签
    private static final String TAG = "XunfeiSpeechCompound";
    // 上下文
    private  WeakReference<Context> mContext;
    // 语音合成对象
    private static SpeechSynthesizer mTts;

    /**
     * 发音人
     */
    private final static String[] COLOUD_VOICERS_ENTRIES = {"小燕", "小宇", "凯瑟琳", "亨利", "玛丽", "小研", "小琪", "小峰", "小梅", "小莉", "小蓉", "小芸", "小坤", "小强 ", "小莹",
            "小新", "楠楠", "老孙",};
    private final static String[] COLOUD_VOICERS_VALUE = {"xiaoyan", "xiaoyu", "catherine", "henry", "vimary", "vixy", "xiaoqi", "vixf", "xiaomei",
            "xiaolin", "xiaorong", "xiaoqian", "xiaokun", "xiaoqiang", "vixying", "xiaoxin", "nannan", "vils",};

    private static final XunfeiSpeechCompound ourInstance = new XunfeiSpeechCompound();

    private XunfeiSpeechCompound() {
    }

    public static XunfeiSpeechCompound getInstance(){
        return ourInstance;
    }

    public void init(Context context){
        // 上下文
        mContext = new WeakReference<>(context);
        // 初始化合成对象
        if (mContext.get() != null){
            mTts = SpeechSynthesizer.createSynthesizer(mContext.get(), new InitListener() {
                @Override
                public void onInit(int code) {
                    if (code != ErrorCode.SUCCESS) {
                        Log.i(TAG, "初始化失败,错误码：" + code);
                    }else {
                        setParam();
                    }
                }
            });
        }

    }

    /**
     * 构造方法
     *
     * @param context
     */
    private XunfeiSpeechCompound(Context context) {
        // 上下文
        mContext = new WeakReference<Context>(context);
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(mContext.get(), new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    Log.i(TAG, "初始化失败,错误码：" + code);
                }else {
                    setParam();
                }
            }
        });
    }

    /**
     * 开始合成
     *
     * @param text
     */
    public void speaking(String text) {
        // 非空判断
        if (TextUtils.isEmpty(text) || isSpeaking()) {
            return;
        }
        int code = mTts.startSpeaking(text, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                if (mContext != null && mContext.get() != null){
                    Toast.makeText(mContext.get(), "没有安装语音+ code = " + code, Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mContext != null && mContext.get() != null){
                    Toast.makeText(mContext.get(), "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    public void destroy(){
        if (null != mTts ) {
            // 停止
            mTts.destroy();
        }
    }
    /*
     * 停止语音播报
     */
    public void stopSpeaking() {
        // 对象非空并且正在说话
        if (null != mTts && mTts.isSpeaking()) {
            // 停止说话
            mTts.stopSpeaking();
        }
    }

    /**
     * 判断当前有没有说话
     *
     * @return
     */
    public static boolean isSpeaking() {
        if (null != mTts) {
            return mTts.isSpeaking();
        } else {
            return false;
        }
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            Log.i(TAG, "开始播放");
        }

        @Override
        public void onSpeakPaused() {
            Log.i(TAG, "暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            Log.i(TAG, "继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            // TODO 缓冲的进度
            Log.i(TAG, "缓冲 : " + percent);
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // TODO 说话的进度
            Log.i(TAG, "合成 : " + percent);
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Log.i(TAG, "播放完成");

            } else if (error != null) {
                Log.i(TAG, error.getPlainDescription(true));
            }

        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 引擎类型 网络
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, COLOUD_VOICERS_VALUE[0]);
        // 设置语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        // 设置音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        // 设置音量
        mTts.setParameter(SpeechConstant.VOLUME, "40");
        // 设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");

        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/KRobot/wavaudio.pcm");
        // 背景音乐  1有 0 无
        // mTts.setParameter("bgs", "1");
    }
}
