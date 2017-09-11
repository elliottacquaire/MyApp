package com.exple.ex_elli.myapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.exple.ex_elli.myapp.R;
import com.exple.ex_elli.myapp.ThreeActivity;
import com.exple.ex_elli.myapp.entries.Student;
import com.exple.ex_elli.myapp.eventbuspackage.MessageEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.btn_jump)
    Button btnJump;
    Unbinder unbinder;
    @BindView(R.id.tv_eventbus)
    TextView tvEventbus;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_jump)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), ThreeActivity.class);

//        getActivity().startActivity(intent);
//        getActivity().startActivityForResult(intent, 100);
        startActivityForResult(intent, 100);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Student student) {
        String message = student.getName() + student.getStudentId() + student.getCourseList().get(2).getCourseName();
        tvEventbus.setText(message);
        Logger.i("harvic", "onEventMainThread收到了消息：" + message);
    }

    // Called in the same thread (default)
// ThreadMode is optional here
//    如果使用onEvent作为订阅函数，那么该事件在哪个线程发布出来的，onEvent就会在这个线程中运行，
//    也就是说发布事件和接收事件线程在同一个线程。使用这个方法时，在onEvent方法中不能执行耗时操作，
//    如果执行耗时操作容易导致事件分发延迟。
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessage(MessageEvent event) {
        tvEventbus.setText(event.message);
        Logger.d("harvic", "onEventMainThread收到了消息：" + event.message);
    }

//    那么如果事件是在UI线程中发布出来的，
//    那么onEventBackground就会在子线程中运行，如果事件本来就是子线程中发布出来的，
//    那么onEventBackground函数直接在该子线程中执行。
//    BackgroundThread：事件的处理会在一个后台线程中执行，对应的函数名是onEventBackgroundThread，
//    虽然名字是BackgroundThread，事件处理是在后台线程，但事件处理时间还是不应该太长，
//    因为如果发送事件的线程是后台线程，会直接执行事件，如果当前线程是UI线程，
//    事件会被加到一个队列中，由一个线程依次处理这些事件，如果某个事件处理时间太长，会阻塞后面的事件的派发或处理。
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageback(MessageEvent event){

    }

//    事件处理会在单独的线程中执行，主要用于在后台线程中执行耗时操作，每个事件会开启一个线程（有线程池），但最好限制线程的数目。
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void omMessageasy(MessageEvent event){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            switch (requestCode) {
                case 100:
                    if (data != null) {
                        String second = data.getStringExtra("three");
                        tvShow.setText(second);
                    } else {
                        tvShow.setText("nothing to passby");
                    }

                    break;
            }
        }

    }
}
