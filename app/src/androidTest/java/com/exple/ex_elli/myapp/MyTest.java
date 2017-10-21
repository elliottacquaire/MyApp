package com.exple.ex_elli.myapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.orhanobut.logger.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/15 15:47
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/15 15:47
 * 修改备注：
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MyTest {

    @Test
    public void test1(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.exple.ex_elli.myapp", appContext.getPackageName());
        Logger.i("AAAAAAAAAAAAAAAAAAAaa");
        assertEquals("result:",123,100+33);
    }
    @Test
    public void test2(){
        boolean result = "18210741899".matches("\\d{11}");
        Log.i("tag", "#####:" + result);
        assertEquals("result:", result, true);
    }
}
