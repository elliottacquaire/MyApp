package com.exple.ex_elli.myapp.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exple.ex_elli.myapp.R;
import com.exple.ex_elli.myapp.widget.view.MyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerVIewActivity extends AppCompatActivity {

    @BindView(R.id.mytextview)
    MyTextView mytextview;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.btn_swaprecycler)
    Button btnSwaprecycler;
    @BindView(R.id.btn_clickk)
    Button btnClickk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        textView.setSelected(true);
        mytextview.setText("ssssdfadfasdfasdfasdfasdfasdf于是自私让爱变成煎熬\n" +
                "付出了所有却让彼此想逃跑\n" +
                "上天让我们相遇得太早\n" +
                "对于缘份却又给得太少\n" +
                "才让我们只能陷在回忆中懊恼\n" +
                "你我都找到新的依靠\n" +
                "过去对错已不再重要\n" +
                "只是我们都清楚地知道\n" +
                "心里还有个划不完的句号\n" +
                "只怪你和我相爱得太早\n" +
                "对于幸福又了解的太少\n" +
                "于是自私让爱变成煎熬\n" +
                "付出了所有却让彼此想逃跑\n");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);

    }

    @OnClick({R.id.btn_swaprecycler, R.id.btn_clickk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_swaprecycler:
                startActivity(new Intent(RecyclerVIewActivity.this,SwipeRecyclerViewActivity.class));
                break;
            case R.id.btn_clickk:
                break;
        }
    }
}
