package com.exple.ex_elli.myapp.yunzhisheng;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.exple.ex_elli.myapp.R;
import com.orhanobut.logger.Logger;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechSynthesizerListener;

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

public class USCSpeechCompound {

    private static final String TAG = USCSpeechCompound.class.getName();

    private SpeechSynthesizer mSpeechSynthesizer;
    // 上下文
    private WeakReference<Context> mContext;

    private MediaPlayer mediaPlayer;

    private static final USCSpeechCompound instance = new USCSpeechCompound();

    private USCSpeechCompound() {
    }
    public static USCSpeechCompound getInstance(){
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
        mSpeechSynthesizer = new SpeechSynthesizer(mContext.get(), Config.appKey,Config.secret);
        //离线授权
        mSpeechSynthesizer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_NET);
        //初始化监听
        mSpeechSynthesizer.setTTSListener(speechSynthesizerListener);

        mSpeechSynthesizer.init(null);

    }

    public void speak(String data) {

        //需要合成的文本text的长度不能超过1024个GBK字节。
        if (TextUtils.isEmpty(data)) {
            return;
        }
        int key = mSpeechSynthesizer.playText(data);
        Logger.i("speak-------------------"+key);
    }

    public void onPause() {
        // 主动停止识别
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stop();
        }
    }
    public void onRelease() {
        // 主动释放离线引擎
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.release(SpeechConstants.TTS_RELEASE_ENGINE, null);
        }
    }

    public void playReplaceMedia(){
        if (mContext.get() != null){
            mediaPlayer = MediaPlayer.create(mContext.get(), R.raw.noticevoice);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mp != null){
                        mp.seekTo(0);
                        mp.release();
                    }
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (mp != null){
                        mp.reset();
                        mp.release();
                    }
                    return false;
                }
            });
        }
    }

    /*getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

    AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
        mediaPlayer.setDataSource(file.getFileDescriptor(),
                file.getStartOffset(), file.getLength());
        file.close();
        mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
        mediaPlayer.prepare();
    } catch (IOException e) {
        mediaPlayer = null;
    }*/

    private SpeechSynthesizerListener speechSynthesizerListener = new SpeechSynthesizerListener() {

        @Override
        public void onEvent(int type) {
            switch (type) {
                case SpeechConstants.TTS_EVENT_INIT:
                    // 初始化成功回调
                    log_i("onInitFinish");
                    break;
                case SpeechConstants.TTS_EVENT_SYNTHESIZER_START:
                    // 开始合成回调
                    log_i("beginSynthesizer");
                    break;
                case SpeechConstants.TTS_EVENT_SYNTHESIZER_END:
                    // 合成结束回调
                    log_i("endSynthesizer");
                    break;
                case SpeechConstants.TTS_EVENT_BUFFER_BEGIN:
                    // 开始缓存回调
                    log_i("beginBuffer");
                    break;
                case SpeechConstants.TTS_EVENT_BUFFER_READY:
                    // 缓存完毕回调
                    log_i("bufferReady");
                    break;
                case SpeechConstants.TTS_EVENT_PLAYING_START:
                    // 开始播放回调
                    log_i("onPlayBegin");
                    break;
                case SpeechConstants.TTS_EVENT_PLAYING_END:
                    // 播放完成回调
                    log_i("onPlayEnd");
                    onRelease();
                    break;
                case SpeechConstants.TTS_EVENT_PAUSE:
                    // 暂停回调
                    log_i("pause");
                    break;
                case SpeechConstants.TTS_EVENT_RESUME:
                    // 恢复回调
                    log_i("resume");
                    break;
                case SpeechConstants.TTS_EVENT_STOP:
                    // 停止回调
                    log_i("stop");
                    break;
                case SpeechConstants.TTS_EVENT_RELEASE:
                    // 释放资源回调
                    log_i("release");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onError(int i, String s) {
            // 语音合成错误回调
            log_i("onError"+i+s);
            playReplaceMedia();
        }
    };

    private void log_i(String log) {
        Logger.i(TAG, log);
    }
}
