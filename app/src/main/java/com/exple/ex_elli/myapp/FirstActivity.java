package com.exple.ex_elli.myapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.exple.ex_elli.myapp.fragment.FirstFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener {

    @BindView(R.id.tv_first)
    TextView tvFirst;

    private static final String TAG = FirstActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        FirstFragment firstFragment = new FirstFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, firstFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                String first = data.getStringExtra("three");
                tvFirst.setText(first);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
