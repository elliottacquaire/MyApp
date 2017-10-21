package com.exple.ex_elli.myapp.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/25 21:54
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/25 21:54
 * 修改备注：
 */

public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写isFocused方法，让其一直返回true
    public boolean isFocused() {
        return true;
    }

}
