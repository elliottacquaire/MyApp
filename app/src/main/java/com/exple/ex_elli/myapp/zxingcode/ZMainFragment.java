package com.exple.ex_elli.myapp.zxingcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.exple.ex_elli.myapp.R;
import com.exple.ex_elli.myapp.service.MyService;
import com.exple.ex_elli.myapp.zxingcode.zxing.activity.CaptureActivity;
import com.exple.ex_elli.myapp.zxingcode.zxing.encoding.EncodingHandler;
import com.google.zxing.WriterException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link} interface
 * to handle interaction events.
 * Use the {@link ZMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int RESULT_CODE_QR_SCAN = 0xA1;
    @BindView(R.id.btn_scancode)
    Button btnScancode;
    Unbinder unbinder;

    @BindView(R.id.img_codeshow)
    ImageView imgCodeshow;
    @BindView(R.id.btn_createcode)
    Button btnCreatecode;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ZMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZMainFragment newInstance(String param1, String param2) {
        ZMainFragment fragment = new ZMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_zmain, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_CODE_QR_SCAN) { //RESULT_OK = -1
                if (data!=null && data.getExtras()!=null){
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("qr_scan_result");
                    Toast.makeText(getActivity(), scanResult, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MyService.class);
                    getActivity().startService(intent);
                }

            }
        }

    }

    @OnClick({R.id.btn_createcode, R.id.btn_scancode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_createcode:
                //        生成二维码
                try {
                    Bitmap mBitmap = EncodingHandler.createQRCode("www.baidu.com", 300);//300表示宽高
                    imgCodeshow.setImageBitmap(mBitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_scancode:
                //扫描二维码调用
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
