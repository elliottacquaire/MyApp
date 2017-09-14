package com.exple.ex_elli.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.exple.ex_elli.myapp.BaiduVoice.BaiduVoiceActivity;
import com.exple.ex_elli.myapp.camera.CameraActivity;
import com.exple.ex_elli.myapp.xunfeivoice.XunfeiVoiceActivity;
import com.exple.ex_elli.myapp.zxingcode.ZMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_jump_first)
    Button btnJumpFirst;
    @BindView(R.id.btn_jump_second)
    Button btnJumpSecond;
    @BindView(R.id.btn_zxcode)
    Button btnZxcode;
    @BindView(R.id.btn_voice)
    Button btnVoice;
    @BindView(R.id.btn_xunfei)
    Button btnXunfei;
    @BindView(R.id.btn_baidu)
    Button btnBaidu;
    @BindView(R.id.btn_rxjava)
    Button btnRxjava;
    @BindView(R.id.btn_carmera)
    Button btnCarmera;

    public static Intent makeIntent(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, MainActivity.class);
        bundle.putString("key", "value");
        intent.putExtras(bundle);

        return intent;
    }

    public static Intent makeIntent(Activity activity, String data) {
        Intent intent = new Intent(activity, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", data);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent makeIntent(Activity activity, String data, boolean flag) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("key", data);

        return intent;
    }

    public static Intent makeIntent(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_jump_first, R.id.btn_jump_second, R.id.btn_zxcode, R.id.btn_voice, R.id.btn_xunfei, R.id.btn_baidu, R.id.btn_rxjava,R.id.btn_carmera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jump_first:
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_jump_second:
                Intent intents = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intents);
                break;
            case R.id.btn_zxcode:
                Intent intentc = new Intent(MainActivity.this, ZMainActivity.class);
                startActivity(intentc);
                break;
            case R.id.btn_voice:
                Intent intentv = new Intent(MainActivity.this, TextToSpeechActivity.class);
                startActivity(intentv);
                break;
            case R.id.btn_xunfei:
                Intent intentxunfei = new Intent(MainActivity.this, XunfeiVoiceActivity.class);
                startActivity(intentxunfei);
                break;
            case R.id.btn_baidu:
                Intent intentbaidu = new Intent(MainActivity.this, BaiduVoiceActivity.class);
                startActivity(intentbaidu);
                break;
            case R.id.btn_rxjava:
                Intent intentjava = new Intent(MainActivity.this, RxjavaActivity.class);
                startActivity(intentjava);
                break;
            case R.id.btn_carmera:
                Intent carmera = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(carmera);
                break;
        }
    }
}
