package com.cboy.rn.splashscreen;
import android.app.Activity;
import android.app.Dialog;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
    private static Dialog mSplashDialog;
    private static WeakReference<Activity> mActivity;
    private static Timer timer;
    /**
     * 打开启动屏
     */
    public static void show(final Activity activity,final boolean fullScreen) {
        if (activity == null) return;
        mActivity = new WeakReference<Activity>(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {

                    mSplashDialog = new Dialog(activity,fullScreen? R.style.SplashScreen_Fullscreen:R.style.SplashScreen_SplashTheme);
                    mSplashDialog.setContentView(R.layout.launch_screen);
                    mSplashDialog.setCancelable(false);

                    if (!mSplashDialog.isShowing()) {
                        mSplashDialog.show();
                    }

                    //5秒自动关闭闪屏对话框
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (mSplashDialog != null && mSplashDialog.isShowing()) {
                                mSplashDialog.dismiss();
                            }
                            timer.cancel();
                        }
                    }, 5000, 1000);
                }
            }
        });
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity) {
        show(activity,false);
    }

    /**
     * 关闭启动屏
     */
    public static void hide(Activity activity) {
        if (activity == null) activity = mActivity.get();
        if (activity == null) return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog != null && mSplashDialog.isShowing()) {
                    mSplashDialog.dismiss();
                }
            }
        });
    }
}
