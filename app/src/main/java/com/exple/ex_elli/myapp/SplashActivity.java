package com.exple.ex_elli.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.btn_click_jump)
    Button btnClickJump;
    @BindView(R.id.tv_show)
    TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_click_jump)
    public void onViewClicked() {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("key","value");
        intent.putExtras(bundle);
        startActivity(MainActivity.makeIntent(SplashActivity.this,bundle));
    }
}
