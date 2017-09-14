package com.exple.ex_elli.myapp.BaiduVoice;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;


/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/6 17:02
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/6 17:02
 * 修改备注：
 */

public class BaiduSpeechCompound {

    private static final String APIKEY = "56keo9uTI2I6uWmUS42k16Xz";
    private static final String SECRETKEY = "BegK9WA02Mk50vgBTKykFO7kyUYjVpWZ";
    private static final String TAG = BaiduSpeechCompound.class.getName();
    private SpeechSynthesizer mSpeechSynthesizer;
    // 上下文
    private WeakReference<Context> mContext;

    private static final BaiduSpeechCompound instance = new BaiduSpeechCompound();

    private BaiduSpeechCompound() {
    }
    public static BaiduSpeechCompound getInstance(){
        return instance;
    }
    public void init(Context context){
        // 上下文
        mContext = new WeakReference<>(context);
        // 初始化合成对象
        if (mContext.get() != null){
            setParam();
        }

    }
    private void setParam() {
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(mContext.get());
        mSpeechSynthesizer.setSpeechSynthesizerListener(speechSynthesizerListener);
//        mSpeechSynthesizer.setApiKey("MxPpf3nF5QX0pndKKhS7IXcB", "7226e84664474aa098296da5eb2aa434");
        mSpeechSynthesizer.setApiKey(APIKEY,SECRETKEY);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "3");
        // 设置Mix模式的合成策略 ttsMode	引擎选择（online是纯在线合成引擎，Mix是离在线混合引擎）
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            toPrint("auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            toPrint("auth failed errorMsg=" + errorMsg);
        }

        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
    }

    public void speak(String data) {

        //需要合成的文本text的长度不能超过1024个GBK字节。
        if (TextUtils.isEmpty(data)) {
            return;
        }
        int result = mSpeechSynthesizer.speak(data);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    public void onRelease() {
        if (mSpeechSynthesizer!=null){
            mSpeechSynthesizer.release();
        }

    }

    private SpeechSynthesizerListener speechSynthesizerListener = new SpeechSynthesizerListener() {
        @Override
        public void onSynthesizeStart(String s) {
            toPrint("onSynthesizeStart utteranceId=" + s);
        }

        /**
         * 合成数据和进度的回调接口，分多次回调
         *
         * @param s
         * @param bytes 合成的音频数据。该音频数据是采样率为16K，2字节精度，单声道的pcm数据。
         * @param i 文本按字符划分的进度，比如:你好啊 进度是0-3
         */
        @Override
        public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

        }

        /**
         * 合成正常结束，每句合成正常结束都会回调，如果过程中出错，则回调onError，不再回调此接口
         *
         * @param s
         */
        @Override
        public void onSynthesizeFinish(String s) {
            toPrint("onSynthesizeFinish utteranceId=" + s);
        }

        /**
         * 播放开始，每句播放开始都会回调
         *
         * @param s
         */
        @Override
        public void onSpeechStart(String s) {
            toPrint("onSpeechStart utteranceId=" + s);
        }

        /**
         * 播放进度回调接口，分多次回调
         *
         * @param s
         * @param i 文本按字符划分的进度，比如:你好啊 进度是0-3
         */
        @Override
        public void onSpeechProgressChanged(String s, int i) {

        }
        /**
         * 播放正常结束，每句播放正常结束都会回调，如果过程中出错，则回调onError,不再回调此接口
         *
         * @param s
         */
        @Override
        public void onSpeechFinish(String s) {
            toPrint("onSpeechFinish utteranceId=" + s);
        }

        /**
         * 当合成或者播放过程中出错时回调此接口
         *
         * @param s
         * @param speechError 包含错误码和错误信息
         */
        @Override
        public void onError(String s, SpeechError speechError) {
            toPrint("onError error=" + "(" + speechError.code + ")" + speechError.description + "--utteranceId=" + s);
        }
    };

    private void toPrint(String str) {
        Logger.i(str);
    }
}
