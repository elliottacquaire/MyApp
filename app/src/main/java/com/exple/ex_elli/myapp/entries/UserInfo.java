package com.exple.ex_elli.myapp.entries;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/8/31 22:45
 * 修改人：ex-lixuyang
 * 修改时间：2017/8/31 22:45
 * 修改备注：
 */

public class UserInfo implements Serializable{
    private String name;
    private String userId;
    private int age;
    private String desc;
    private List<Persion> persionList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Persion> getPersionList() {
        return persionList;
    }

    public void setPersionList(List<Persion> persionList) {
        this.persionList = persionList;
    }
}
