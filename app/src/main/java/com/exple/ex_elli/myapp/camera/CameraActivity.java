package com.exple.ex_elli.myapp.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.exple.ex_elli.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.exple.ex_elli.myapp.camera.camerautil.CameraUtil.getOutputMediaFileUri;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;

    @BindView(R.id.opercarmar)
    Button opercarmar;
    @BindView(R.id.opercshexiang)
    Button opercshexiang;

    private Uri fileUri;
    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }


    @OnClick({R.id.opercarmar, R.id.opercshexiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.opercarmar:
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(1); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, 100);
                break;
            case R.id.opercshexiang:

                /*
                *
                * MediaStore.EXTRA_OUTPUT - 该关键字和拍照使用的关键字一样，意思就是制定一个路径和文件名来构建一个U日对象来保存录像结果，同样录像结果会以Intent.getData()的方法返回Uri对象。
                MediaStore.EXTRA_VIDEO_QUALITY - 该关键字用于指定拍摄的录像质量，参数0表示低质量，参数1表示高质量。
                MediaStore.EXTRA_DURATION_LIMIT - 该关键之用于指定拍摄的录像的时间限制，单位是秒。
                MediaStore.EXTRA_SIZE_LIMIT - 该关键字用于指定拍摄的录像文件大小限制，单位值byte。
                * */

                Intent intent1 = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                fileUri = getOutputMediaFileUri(2);  // create a file to save the video
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

                intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

                // start the Video Capture Intent
                startActivityForResult(intent1, 200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    Toast.makeText(this, "Image saved to:\n" +
                            data.getData(), Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
                break;
            case 200:
                if (resultCode == RESULT_OK) {
                    // Video captured and saved to fileUri specified in the Intent
                    Toast.makeText(this, "Video saved to:\n" +
                            data.getData(), Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the video capture
                } else {
                    // Video capture failed, advise user
                }
                break;
            default:
                break;
        }
    }

    /*
    * 检测和访问相机 - 首先代码检测该设备相机是否存在，如果存在才能请求访问设备相机.
    创建一个预览来显示相机图像 - 在你的布局中使用SurfaceView控件，然后在代码中继承SurfaceHolder.Callback接口并且实现接口中的方法来显示来自相机的图像信息。
    设置相机基本参数 - 根据需求设置相机预览尺寸，图片大小，预览方向，图片方向等。
    设置拍照录像监听 - 当用户按下按钮时调用Camera#takePicture或者MediaRecorder#start()来进行拍照或录像。
    文件保存 - 当拍照结束或者录像视频结束时，需要开启一个后台线程去保存图片或者视频文件。
    释放相机资源 - Camera硬件是一个共享资源，所以你必须小心的编写你的应用代码来管理相机资源。一般在Activity的生命周期的onResume中开机相机，在onPause中释放相机。
    * */
    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    /**
     * 一旦你可以成功访问相机设备，你可以使用Camera#getParameters()方法来获取相机参数信息，可以根据
     返回值 Camera.Parameters 类来查看当前camea支持哪些参数设置等。当使用API 9或者更高时，
     你可以使用Camera.getCameraInfo()静态方法来获取前后camera的ID，以及camera数据流的方向和是否能禁止拍照快门声音标记
     * get current camera info
     *
     * @param cameraId current camera id
     * @return camera info
     */
    public static Camera.CameraInfo getCameraInfo(int cameraId) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        return cameraInfo;
    }
}
