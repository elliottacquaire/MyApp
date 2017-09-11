package com.exple.ex_elli.myapp;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextToSpeechActivity extends AppCompatActivity {

    @BindView(R.id.edt_input)
    EditText edtInput;
    @BindView(R.id.btn_say)
    Button btnSay;

    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        ButterKnife.bind(this);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    int result1 = textToSpeech.setLanguage(Locale.US);
                    int result2 = textToSpeech.setLanguage(Locale.CHINESE);
                    if (result1 == TextToSpeech.LANG_MISSING_DATA
                            || result1 == TextToSpeech.LANG_NOT_SUPPORTED
                            || result2 == TextToSpeech.LANG_MISSING_DATA
                            || result2 == TextToSpeech.LANG_NOT_SUPPORTED ) {
                        Toast.makeText(TextToSpeechActivity.this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    textToSpeech.setPitch(1.0f);
                    // 设置语速 1.0 is the normal speech rate
                    textToSpeech.setSpeechRate(1.0f);

                    Toast.makeText(TextToSpeechActivity.this, "初始化success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TextToSpeechActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.btn_say)
    public void onViewClicked() {

        String content = edtInput.getText().toString();
        if (content.isEmpty()) {
            //

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak("你好接口2222",TextToSpeech.QUEUE_FLUSH,null,null);
            }else {
                textToSpeech.speak("你好", TextToSpeech.QUEUE_ADD, null);
            }
        } else {
            //
            textToSpeech.speak(content, TextToSpeech.QUEUE_ADD, null);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
