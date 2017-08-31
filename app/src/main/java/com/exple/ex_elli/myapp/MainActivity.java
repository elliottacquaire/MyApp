package com.exple.ex_elli.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_jump_first)
    Button btnJumpFirst;
    @BindView(R.id.btn_jump_second)
    Button btnJumpSecond;

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

    @OnClick({R.id.btn_jump_first, R.id.btn_jump_second})
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
        }
    }
}
