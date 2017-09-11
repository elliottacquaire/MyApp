package com.exple.ex_elli.myapp.xunfeivoice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exple.ex_elli.myapp.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XunfeiVoiceActivity extends AppCompatActivity {

    @BindView(R.id.edit_input)
    EditText editInput;
    @BindView(R.id.btn_xunfei)
    Button btnXunfei;

    private  SpeechSynthesizer mTts;
//    private XunfeiSpeechCompound xunfeiSpeechCompound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunfei_voice);
        ButterKnife.bind(this);
//        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59adfe65");
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListner);

//        xunfeiSpeechCompound = new XunfeiSpeechCompound(this);
        XunfeiSpeechCompound.getInstance().init(this);
    }

    @OnClick(R.id.btn_xunfei)
    public void onViewClicked() {
        String data = editInput.getText().toString().trim();
//        if (!TextUtils.isEmpty(data)){
//            voiceOutput(data);
//            return;
//        }
//        voiceOutput("22.22");

//        xunfeiSpeechCompound.speaking(data);
        XunfeiSpeechCompound.getInstance().speaking(data);

    }
    private InitListener mTtsInitListner = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(XunfeiVoiceActivity.this,"初始化失败，错误码：" + code,Toast.LENGTH_SHORT).show();
            } else {

            }
        }
    };
    private void voiceOutput(String data) {

//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "30");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE,"3");


//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
//保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
//如果不需要保存合成音频，注释该行代码
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
//3.开始合成
        mTts.startSpeaking("科大讯飞，让世界聆听我们的声音"+data, mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //开始播放
        @Override
        public void onSpeakBegin() {

        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        //暂停播放
        @Override
        public void onSpeakPaused() {

        }

        //恢复播放回调接口
        @Override
        public void onSpeakResumed() {

        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        //会话结束回调接口，没有错误时，error为null
        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) { //播放完成

            } else {
                Toast.makeText(XunfeiVoiceActivity.this,"code=" + speechError.getErrorCode() + ",msg=" + speechError.getErrorDescription(),Toast.LENGTH_SHORT).show();
            }
        }

        //会话事件回调接口
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTts.stopSpeaking();
        mTts.destroy();
//        xunfeiSpeechCompound.stopSpeaking();
//        xunfeiSpeechCompound.destroy();

        XunfeiSpeechCompound.getInstance().stopSpeaking();
        XunfeiSpeechCompound.getInstance().destroy();
        SpeechUtility.getUtility().destroy();
    }
}
