package com.exple.ex_elli.myapp.yunzhisheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.exple.ex_elli.myapp.R;
import com.orhanobut.logger.Logger;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechSynthesizerListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YunzhishengActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String OFFLINE_TTS = "本地合成";
    @BindView(R.id.btn_click)
    Button btnClick;
    private ArrayList<String> mFunctionsArray;

    private SpeechSynthesizer speechSynthesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_yunzhisheng);
        ButterKnife.bind(this);
        Logger.init("AAAAAAAA");
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.status_bar_main);
        initFunctionArray();
        ((ListView) findViewById(R.id.lv_functions)).setAdapter(new FunctionsAdapter());
        USCSpeechCompound.getInstance().init(getApplication());
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        Object tag = view.getTag();
        if (tag.equals(OFFLINE_TTS)) {
            intent = new Intent(this, TTSOfflineActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }

    }

    private void test() {
        speechSynthesizer = new SpeechSynthesizer(this, Config.appKey, Config.secret);
        speechSynthesizer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_NET);
        speechSynthesizer.setTTSListener(new SpeechSynthesizerListener() {
            @Override
            public void onEvent(int i) {
                Logger.i(i+"");
            }

            @Override
            public void onError(int i, String s) {
                Logger.i(i+"---"+s);
            }
        });
        speechSynthesizer.init(null);
        speechSynthesizer.playText("您有一笔收款到帐了");
    }

    @OnClick(R.id.btn_click)
    public void onViewClicked() {
//        test();
        testLocal();
    }

    private void testLocal() {
        USCSpeechCompound.getInstance().speak("一笔收款");
    }

    private class FunctionsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mFunctionsArray.size();
        }

        @Override
        public Object getItem(int arg0) {
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(YunzhishengActivity.this).inflate(R.layout.button_list_item, null);
                holder = new ViewHolder();
                holder.btn = (Button) convertView.findViewById(R.id.btn);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.btn.setText(mFunctionsArray.get(position));
            holder.btn.setTag(mFunctionsArray.get(position));
            holder.btn.setOnClickListener(YunzhishengActivity.this);
            return convertView;
        }
    }

    public final class ViewHolder {
        public Button btn;
    }

    private void initFunctionArray() {
        mFunctionsArray = new ArrayList<String>();
        mFunctionsArray.add(OFFLINE_TTS);
    }
}
