package com.exple.ex_elli.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.exple.ex_elli.myapp.entries.event.TestEvent;
import com.exple.ex_elli.myapp.service.BackService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JavaConstructActivity extends AppCompatActivity {

    @BindView(R.id.btn_even)
    Button btnEven;
    @BindView(R.id.btn_test)
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_construct);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void TestEvents(TestEvent event) {
        Toast.makeText(getApplication().getApplicationContext(), "dd-d-dd--d-d-d" + event.getCode(), Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_even, R.id.btn_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_even:
                Intent intentService = new Intent(JavaConstructActivity.this, BackService.class);
                startService(intentService);
                break;
            case R.id.btn_test:
                break;
        }
    }
}
