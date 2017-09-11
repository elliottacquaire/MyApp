package com.exple.ex_elli.myapp.javaclass;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/1 0:43
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/1 0:43
 * 修改备注：
 */

public class Single {
    private static final Single ourInstance = new Single();

    public static Single getInstance() {
        return ourInstance;
    }

    private Single() {
    }
}
