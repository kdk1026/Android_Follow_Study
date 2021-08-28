package com.example.testapp.util;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 8. 28. 김대광	최초 작성
 * </pre>
 *
 * <pre>
 * Geocoder 유틸
 * </pre>
 *
 * @author 김대광
 * @Description	: minSdkVersion 26 / targetSdkVersion 30
 */
public class AddressUtil {

    private static final String TAG = "AddressUtil";

    private AddressUtil() {
    }

    private static class LazyHolder {
        private static final AddressUtil INSTANCE = new AddressUtil();
    }

    public static AddressUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getCurrentAddress(Activity activity, double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 100);
        } catch (IOException e) {
            // 네트워크 문제
            Toast.makeText(activity, "Geocoder 서비스 아용불가", Toast.LENGTH_LONG).show();
            return "GPS 좌표에 이상이 있습니다.";
        }

        if ( addresses == null || addresses.size() == 0 ) {
            Toast.makeText(activity, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

}
