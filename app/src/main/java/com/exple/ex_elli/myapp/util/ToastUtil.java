package com.exple.ex_elli.myapp.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/15 17:56
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/15 17:56
 * 修改备注：
 */

public class ToastUtil implements ToastFunctions {
    private Toast toast = null;

    @Override
    public void immediateToast(Context context, String text, int duration) {
        toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void ToastLocation(Context context, String text, int duration,
                              int gravity) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    @Override
    public void ToastWithPic(Context context, String text, int duration,
                             int resId) {
        toast = Toast.makeText(context, text, duration);
        // 通过Toast创建一个LinearLayout
        LinearLayout layout = (LinearLayout) toast.getView();
        // 创建一个ImageView对象，并添加到LinearLayout中
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resId);
        // 添加
        layout.addView(imageView);
        // 显示Toast
        toast.show();
    }

    @Override
    public void ToastBySelf(Context context, String text, int duration, View view) {
        // Toast
        toast = new Toast(context);
        // 设置Toast显示时长
        toast.setDuration(duration);
        // 设置Toast的View对象
        toast.setView(view);
        // 显示Toast
        toast.show();
    }
}
