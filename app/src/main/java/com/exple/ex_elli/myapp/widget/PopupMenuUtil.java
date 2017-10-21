package com.exple.ex_elli.myapp.widget;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exple.ex_elli.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * name patient-android
 * time 2017/7/11.
 * Created by Administrator
 * d escribe describe
 */

public class PopupMenuUtil {
    private static final String TAG = "PopupMenuUtil";

    public static PopupMenuUtil getInstance() {
        return MenuUtilHolder.INSTANCE;
    }

    private static class MenuUtilHolder {
        public static PopupMenuUtil INSTANCE = new PopupMenuUtil();
    }

    private View rootVew;
    private PopupWindow popupWindow;

    private RelativeLayout rlClick;
    private ImageView img_add,img_writeDiary,img_post,img_diagnose;

    private List<ImageView> buttonItems = new ArrayList<ImageView>();

    int radius = 100 ,angleValue = 120;
    private int point_y;

    /**
     * 创建 popupWindow 内容
     *
     * @param context context
     */
    private void _createView(final Context context) {
        rootVew = LayoutInflater.from(context).inflate(R.layout.popup_menu, null);
        popupWindow = new PopupWindow(rootVew,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置为失去焦点 方便监听返回键的监听
        popupWindow.setFocusable(false);

        // 如果想要popupWindow 遮挡住状态栏可以加上这句代码
        popupWindow.setClippingEnabled(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);

        initLayout(context);
    }

    /**
     * dp转化为px
     *
     * @param context  context
     * @param dipValue dp value
     * @return 转换之后的px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 初始化 view
     */
    private void initLayout(Context context) {
        rlClick = (RelativeLayout) rootVew.findViewById(R.id.pop_rl_click);
        img_add = (ImageView) rootVew.findViewById(R.id.img_add);
        img_writeDiary = (ImageView) rootVew.findViewById(R.id.img_writeDiary);
        img_post = (ImageView) rootVew.findViewById(R.id.img_post);
        img_diagnose = (ImageView) rootVew.findViewById(R.id.img_diagnose);

        rlClick.setOnClickListener(new MViewClick(0, context));
        img_writeDiary.setOnClickListener(new MViewClick(1, context));
        img_post.setOnClickListener(new MViewClick(2, context));
        img_diagnose.setOnClickListener(new MViewClick(3, context));

        point_y = dip2px(context,55)/2;
//        radius = ScreenUtil.getScreenWidth(context)/3;


    }

     private  float getXValue(int angle)
     {
         return  (float)(0+Math.sin(angle*Math.PI/360)*radius);
     }

    private  float getYValue(int angle)
    {

        return  (float)(point_y + Math.cos(angle*Math.PI/360)*radius);
    }

    /**
     * 点击事件
     */
    private class MViewClick implements View.OnClickListener {

        public int index;
        public Context context;

        public MViewClick(int index, Context context) {
            this.index = index;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if (index == 0) {
                _rlClickAction();
            }else
            {

                    if (index ==1){
                        _rlClickAction();


//                        Intent intent = new Intent(context, WriteDailyActivity.class);
//                        context.startActivity(intent);
                    }else if (index ==2){
                        _rlClickAction();

//                        context.startActivity(new Intent(context, QAPublishActivity.class));
//                        context.startActivity(new Intent(context, PostCardActivity.class));
                    }else if (index ==3){
                        _rlClickAction();

//                        context.startActivity(new Intent(context, DoctorDiagnosisActivity.class));
                    }



            }

        }
    }

    Toast toast = null;

    /**
     * 防止toast 多次被创建
     *
     * @param context context
     * @param str     str
     */
    private void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    /**
     * 刚打开popupWindow 执行的动画
     */
    private void _openPopupWindowAction() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img_add, "rotation", 0f, 135f);
        objectAnimator.setDuration(300);
        objectAnimator.start();

//        _startAnimation(img_writeDiary, 300,-(float) Math.sin(45)*radius,-(float) Math.sin(45)*radius);
//        _startAnimation(img_post, 300, 0,-radius);
//        _startAnimation(img_diagnose, 300, (float) Math.sin(45)*radius,-(float) Math.sin(45)*radius);


        _startAnimation(img_writeDiary, 300,-getXValue(angleValue),-getYValue(angleValue));
        _startAnimation(img_post, 300, 0,-(radius+point_y));
        _startAnimation(img_diagnose, 300, getXValue(angleValue),-getYValue(angleValue));

    }


    /**
     * 关闭 popupWindow执行的动画
     */
    public void _rlClickAction() {
        if (img_add != null && rlClick != null) {

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img_add, "rotation", 135f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.start();

            _closeAnimation(img_writeDiary, 300, 0,0);
            _closeAnimation(img_post, 300, 0,0);
            _closeAnimation(img_diagnose, 300, 0,0);

            rlClick.postDelayed(new Runnable() {
                @Override
                public void run() {
                    _close();
                }
            }, 300);

        }
    }


    /**
     * 弹起 popupWindow
     *
     * @param context context
     * @param parent  parent
     */
    public void _show(Context context, View parent) {
        _createView(context);
        int height =  getNavigationBarHeight(context);
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, -height);
            _openPopupWindowAction();
        }
    }
    //获取虚拟导航栏高度
    private int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        boolean isExit = navigationBarExist((Activity) context);
        if (isExit){
            return height;
        }else {
            return 0;
        }

    }
    //判断导航栏是否存在
    public static boolean navigationBarExist(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealMetrics(realDisplayMetrics);
        }

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    /**
     * 关闭popupWindow
     */

    public void _close() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * @return popupWindow 是否显示了
     */
    public boolean _isShowing() {
        if (popupWindow == null) {
            return false;
        } else {
            return popupWindow.isShowing();
        }
    }

    /**
     * 关闭 popupWindow 时的动画
     *
     * @param view     mView
     * @param duration 动画执行时长
     * @param distanceX     平移量
     */
    private void _closeAnimation(View view, int duration, float distanceX,float distanceY) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", distanceY);
        ObjectAnimator.ofFloat(view,"scaleX",1f,0).setDuration(duration).start();
        ObjectAnimator.ofFloat(view,"scaleY",1f,0).setDuration(duration).start();
        animY.setDuration(duration);
        animY.start();
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", distanceX);
        animX.setDuration(duration);
        animX.start();
//        ObjectAnimator animR = ObjectAnimator.ofFloat(view, "rotation", 0f, 135,0);
//        animR.setDuration(duration);
//        animR.start();
    }

    /**
     * 启动动画
     *
     * @param view     view
     * @param duration 执行时长
     * @param distanceX 执行的轨迹数
     */
    private void _startAnimation(View view, int duration, float distanceX,float distanceY) {
        ObjectAnimator.ofFloat(view,"scaleX",0,1f).setDuration(duration).start();
        ObjectAnimator.ofFloat(view,"scaleY",0,1f).setDuration(duration).start();
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", distanceY);
        animY.setDuration(duration);
        animY.start();
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", distanceX);
        animX.setDuration(duration);
        animX.start();
//        ObjectAnimator animR = ObjectAnimator.ofFloat(view, "rotation", 0,135);
//        animR.setDuration(duration);
//        animR.start();
    }
}
