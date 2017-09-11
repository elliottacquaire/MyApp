package com.exple.ex_elli.myapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.exple.ex_elli.myapp.FirstActivity;
import com.exple.ex_elli.myapp.MainActivity;
import com.exple.ex_elli.myapp.R;
import com.exple.ex_elli.myapp.SecondActivity;
import com.exple.ex_elli.myapp.entries.Course;
import com.exple.ex_elli.myapp.entries.Student;
import com.exple.ex_elli.myapp.eventbuspackage.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {


    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.btn_jump)
    Button btnJump;
    Unbinder unbinder;
    @BindView(R.id.btn_jump_other)
    Button btnJumpOther;

    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_jump,R.id.btn_jump_other,R.id.btn_eventbus})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_jump:
                Intent mIntent = new Intent();
                mIntent.putExtra("three", "from three");
                mIntent.putExtra("change01", "1000");
                mIntent.putExtra("change02", "2000");
                // 设置结果，并进行传送
                getActivity().setResult(0, mIntent);
                getActivity().finish();
                break;
            case R.id.btn_jump_other:
                Intent intents = new Intent(getActivity(), MainActivity.class);
                startActivity(intents);
                EventBus.getDefault().post(new Student());
                break;
            case R.id.btn_eventbus:
                Student student = new Student();
                student.setStudentId("100");
                student.setName("Jack");
                student.setAge(20);
                List<Course> list = new ArrayList<>();
                for (int i=0;i<5;i++){
                    Course course = new Course();
                    course.setCourseId(10000+i);
                    course.setCourseName("English"+i);
                    course.setLimitNum(70+i);
                    list.add(course);
                }

                student.setCourseList(list);

                EventBus.getDefault().post(student);
                EventBus.getDefault().post(new MessageEvent("eventbus_cccccc"));

                break;
        }


    }

}
