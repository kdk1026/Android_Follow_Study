package com.example.mytest.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 8. 29. 김대광	최초 작성
 * </pre>
 *
 * <pre>
 * 앱 정보 가져오기 유틸
 * </pre>
 *
 * @author 김대광
 * @Description	: minSdkVersion 26 / targetSdkVersion 30
 */
public class AppInfoUtil {

    private static final String TAG = "AppInfoUtil";

    private AppInfoUtil() {
    }

    private static class LazyHolder {
        private static final AppInfoUtil INSTANCE = new AppInfoUtil();
    }

    public static AppInfoUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * app id 가져오기
     * @return
     */
    public String getAppId() {
        String appId = "";
        PackageManager packageManager = this.context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.context.getPackageName(), 0);
            appId = packageInfo.applicationInfo.loadDescription(packageManager) + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appId;
    }

    /**
     * app name 가져오기
     * @return
     */
    public String getAppName() {
        String appName = "";
        PackageManager packageManager = this.context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.context.getPackageName(), 0);
            appName = packageInfo.applicationInfo.loadLabel(packageManager) + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appName;
    }

    /**
     * package name 가져오기
     * @return
     */
    public String getPackageName() {
        String packageName = "";
        PackageManager packageManager = this.context.getPackageManager();

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            packageName = packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageName;
    }

    /**
     * app version 가져오기
     * @return
     */
    public String getVersion() {
        String versionName = "";
        PackageManager packageManager = this.context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.context.getPackageName(), 0);
            versionName = packageInfo.versionName + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    /**
     * app version code 가져오기
     * @return
     */
    public int getVersionCode() {
        int versionCode = 1;
        PackageManager packageManager = this.context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

}
