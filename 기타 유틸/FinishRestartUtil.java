package com.example.testapp.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 8. 25. 김대광	최초 작성
 * </pre>
 *
 * <pre>
 * 안드로이드 앱 종료, 재시작
 * </pre>
 *
 * @author 김대광
 * @Description	: minSdkVersion 26 / targetSdkVersion 30
 */
public class FinishRestartUtil {

    private static final String TAG = "FinishRestartUtil";

    private FinishRestartUtil() {
    }

    private static class LazyHolder {
        private static final FinishRestartUtil INSTANCE = new FinishRestartUtil();
    }

    public static FinishRestartUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * 앱을 재시작
     * @param context
     */
    public void restart(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    /**
     * 앱을 완전히 종료
     * @param activity
     * @param context
     */
    public void completelyExit(Activity activity, Context context) {
        activity.moveTaskToBack(true);
        activity.finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
