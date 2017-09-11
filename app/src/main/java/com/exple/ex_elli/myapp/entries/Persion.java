package com.exple.ex_elli.myapp.entries;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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

public class Persion implements Parcelable {
    private String PersionId;
    private int age;
    private long birdthTime;
    private String name;
    private List<Course> courseList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PersionId);
        dest.writeInt(this.age);
        dest.writeLong(this.birdthTime);
        dest.writeString(this.name);
        dest.writeList(this.courseList);
    }

    public Persion() {
    }

    protected Persion(Parcel in) {
        this.PersionId = in.readString();
        this.age = in.readInt();
        this.birdthTime = in.readLong();
        this.name = in.readString();
        this.courseList = new ArrayList<Course>();
        in.readList(this.courseList, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Persion> CREATOR = new Parcelable.Creator<Persion>() {
        public Persion createFromParcel(Parcel source) {
            return new Persion(source);
        }

        public Persion[] newArray(int size) {
            return new Persion[size];
        }
    };
}
