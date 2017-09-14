package com.exple.ex_elli.myapp.javaclass;

/**
 * 项目名称：MyApplication
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/8/30 0:48
 * 修改人：ex-lixuyang
 * 修改时间：2017/8/30 0:48
 * 修改备注：
 */

public class Singon {

    //饿汉式写法
    private static final Singon ourInstance = new Singon();

    public static Singon getInstance() {
        return ourInstance;
    }

    private Singon() {
    }

    /*懒汉式写法（线程安全）

    * private static Singleton instance;
    private Singleton (){}
    public static synchronized Singleton getInstance() {
    if (instance == null) {
        instance = new Singleton();
    }
    return instance;
    }

    * */

    /*
    懒汉写法（线程不安全）
    *
    * private static Singleton singleton;
    private Singleton() {
    }
    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
    *
    *
    * */

    /*
    *静态内部类
    * private static class SingletonHolder {
    private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton (){}
    public static final Singleton getInstance() {
    return SingletonHolder.INSTANCE;
    }

    *
    * */

    /*
    *双重校验锁
    *
    * private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
    if (singleton == null) {
        synchronized (Singleton.class) {
        if (singleton == null) {
            singleton = new Singleton();
        }
        }
    }
    return singleton;
    }

    *
    *
    * */
}
