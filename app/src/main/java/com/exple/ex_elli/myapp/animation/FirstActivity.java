package com.exple.ex_elli.myapp.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exple.ex_elli.myapp.R;
import com.exple.ex_elli.myapp.widget.PopupView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.img_add)
    ImageView imgAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test, R.id.img_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
                break;
            case R.id.img_add:
                PopupView.getInstance()._show(FirstActivity.this, imgAdd);
                break;
        }
    }
}
