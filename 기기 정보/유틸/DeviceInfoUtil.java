package com.example.mytest.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 8. 29. 김대광	최초 작성
 * </pre>
 *
 * <pre>
 * 기기 단말정보 가져오기 유틸
 * </pre>
 *
 * @author 김대광
 * @Description	: minSdkVersion 26 / targetSdkVersion 30
 */
public class DeviceInfoUtil {

    private static final String TAG = "DeviceInfoUtil";

    private DeviceInfoUtil() {
    }

    private static class LazyHolder {
        private static final DeviceInfoUtil INSTANCE = new DeviceInfoUtil();
    }

    public static DeviceInfoUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * device id 가져오기
     * @param context
     * @return
     */
    public String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * device 제조사 가져오기
     * @return
     */
    public String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * device 브랜드 가져오기
     * @return
     */
    public String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * device 모델명 가져오기
     * @return
     */
    public String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * device Android OS 버전 가져오기
     * @return
     */
    public String getDeviceOs() {
        return Build.VERSION.RELEASE;
    }

    /**
     * device SDK 버전 가져오기
     * @return
     */
    public int getDeviceSdk() {
        return Build.VERSION.SDK_INT;
    }

}
