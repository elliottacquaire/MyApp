package com.exple.ex_elli.myapp.entries;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/8/31 22:51
 * 修改人：ex-lixuyang
 * 修改时间：2017/8/31 22:51
 * 修改备注：
 */

public class Student implements Parcelable {
    private String studentId;
    private int age;
    private long birdthTime;
    private String name;
    private List<Course> courseList;

    public Student() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.studentId);
        dest.writeInt(this.age);
        dest.writeLong(this.birdthTime);
        dest.writeString(this.name);
        dest.writeList(this.courseList);
    }

    protected Student(Parcel in) {
        this.studentId = in.readString();
        this.age = in.readInt();
        this.birdthTime = in.readLong();
        this.name = in.readString();
        this.courseList = new ArrayList<Course>();
        in.readList(this.courseList, List.class.getClassLoader());
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getBirdthTime() {
        return birdthTime;
    }

    public void setBirdthTime(long birdthTime) {
        this.birdthTime = birdthTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
