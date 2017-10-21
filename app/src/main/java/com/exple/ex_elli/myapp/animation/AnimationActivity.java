package com.exple.ex_elli.myapp.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.exple.ex_elli.myapp.R;
import com.exple.ex_elli.myapp.widget.PopupMenuUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.img_myview)
    ImageView myView;
    @BindView(R.id.btn_click)
    Button btnClick;

    private float radius = 200f;
    private int width = 100;
    private int height = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        framAnimation();
    }

    private void framAnimation() {
        ImageView animationImg1 = (ImageView) findViewById(R.id.img);
        animationImg1.setImageResource(R.drawable.frame_anim1);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) animationImg1.getDrawable();
        animationDrawable1.start();

    }

    private void startAnimations() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img, "translationX", 50, 100, -200);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(myView, "alpha", 1.0f, 0.5f, 0.8f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(myView, "scaleY", 0.0f, 2.0f);
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(myView, "rotation", 0, 360);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(myView, "translationX", 100, 400);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(myView, "translationY", 100, 750);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
//                set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
        set.setDuration(4000);
        set.start();

    }

    private void propertyValuesHolder(View view){
        PropertyValuesHolder pvhx = PropertyValuesHolder.ofFloat("alpha",1f,0f,1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view,pvhx,pvhY,pvhZ).setDuration(1000).start();
    }

    @OnClick({R.id.btn, R.id.img_add, R.id.btn_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Intent intent = new Intent(AnimationActivity.this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.img_add:
                PopupMenuUtil.getInstance()._show(AnimationActivity.this, imgAdd);
                break;
            case R.id.btn_click:
                startAnimations();
                break;
        }
    }
}
