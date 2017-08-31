package com.exple.ex_elli.myapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exple.ex_elli.myapp.fragment.FirstFragment;
import com.exple.ex_elli.myapp.fragment.ThreeFragment;

public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        ThreeFragment threeFragment = new ThreeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content,threeFragment);
        fragmentTransaction.commit();
    }
}
