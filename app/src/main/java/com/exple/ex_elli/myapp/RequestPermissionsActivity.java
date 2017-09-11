package com.exple.ex_elli.myapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observer;

public class RequestPermissionsActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE = 10000 ;
    private String[] permissions = {Manifest.permission.READ_CONTACTS,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permissions);
    }
    private void initData(){
        //静态初始化数组：方法一
        String cats[] = new String[] {
                "Tom","Sam","Mimi"
        };

        //静态初始化数组：方法二
        String dogs[] = {"Jimmy","Gougou","Doggy"};

        //动态初始化数据
        String books[] = new String[2];
        books[0] = "Thinking in Java";
        books[1] = "Effective Java";

    }

    private void getRxPermission(){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(aBoolean){
                            Toast.makeText(RequestPermissionsActivity.this, "同意权限", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RequestPermissionsActivity.this, "拒绝权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void click(View view){

        if (Build.VERSION.SDK_INT < 23){
            //do what you want not request permission
            return;
        }
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted  = checkPermissionAllGranted(permissions);

        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted){
            doBackup();
            return;
        }
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {

            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSION_REQUEST_CODE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(this,permissions,MY_PERMISSION_REQUEST_CODE);
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_CODE){
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            if (grantResults.length > 0){
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        isAllGranted = false;
                        break;
                    }
                }
            }

            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                doBackup();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions){
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                // 只要有一个权限没有被授予, 则直接返回 false
//                授予: PackageManager.PERMISSION_GRANTED
//                拒绝: PackageManager.PERMISSION_DENIED
                return false;
            }
        }
        return true;
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                startActivity(intent);
                startActivityForResult(intent, 123);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123){
            boolean isAllGranted  = checkPermissionAllGranted(permissions);

            // 如果这3个权限全都拥有, 则直接执行备份代码
            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                doBackup();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    private void doBackup(){
        Toast.makeText(this,"do something",Toast.LENGTH_SHORT).show();
    }
}
