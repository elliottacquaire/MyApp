package com.exple.ex_elli.myapp.entries;

import java.io.Serializable;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/8/31 22:53
 * 修改人：ex-lixuyang
 * 修改时间：2017/8/31 22:53
 * 修改备注：
 */

public class Course implements Serializable{
    private static final long serialVersionUID = -6744480206188178710L;
    private int courseId;
    private String courseName;
    private long chooseTime;
    private int limitNum;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(long chooseTime) {
        this.chooseTime = chooseTime;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }
}
