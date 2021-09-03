package com.example.mytest.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 9. 01. 김대광	최초 작성
 * 2021. 9. 02. 김대광  runCameraVideo 추가
 * </pre>
 *
 * <pre>
 * 앱 정보 가져오기 유틸
 * </pre>
 *
 * @author 김대광
 * @Description : minSdkVersion 26 / targetSdkVersion 30
 */
public class WebViewCameraUtil {

    private static final String TAG = "AppInfoUtil";

    private WebViewCameraUtil() {
    }

    private static class LazyHolder {
        private static final WebViewCameraUtil INSTANCE = new WebViewCameraUtil();
    }

    public static WebViewCameraUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private Uri uriCameraImage = null;

    public Uri runCameraImage(boolean isCapture, Activity activity, int fileReqCode) {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "sample.png");

        // File 객체의 URI를 얻는다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String providerAuthorities = this.context.getPackageName() + ".provider";

            uriCameraImage = FileProvider.getUriForFile(this.context, providerAuthorities, file);
        } else {
            uriCameraImage = Uri.fromFile(file);
        }
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uriCameraImage);

        if (!isCapture) {
            // 선택 팝업 : 갤러리, 포토 띄우기 (이미지만)
            Intent pickIntent = new Intent(Intent.ACTION_PICK);
            pickIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            pickIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            String pickTitle = "사진 가져올 방법을 선택하세요.";
            Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);

            // 선택 팝업에 카메라 포함
             chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]
             { intentCamera });

            // FIXME : deprecated ...
            activity.startActivityForResult(chooserIntent, fileReqCode);
        } else {
            // FIXME : deprecated ...
            activity.startActivityForResult(intentCamera, fileReqCode);
        }

        return uriCameraImage;
    }

    public Uri runCameraVideo(boolean isCapture, Activity activity, int fileReqCode) {
        Intent intentCamera = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "sample.mp4");

        // File 객체의 URI를 얻는다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String providerAuthorities = this.context.getPackageName() + ".provider";

            uriCameraImage = FileProvider.getUriForFile(this.context, providerAuthorities, file);
        } else {
            uriCameraImage = Uri.fromFile(file);
        }
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uriCameraImage);

        if (!isCapture) {
            // 선택 팝업 : 갤러리, 포토 띄우기 (동영상만)
            Intent pickIntent = new Intent(Intent.ACTION_PICK);
            pickIntent.setType(MediaStore.Video.Media.CONTENT_TYPE);
            pickIntent.setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

            String pickTitle = "사진 가져올 방법을 선택하세요.";
            Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);

            // 선택 팝업에 카메라 포함
            //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[] { intentCamera });

            // FIXME : deprecated ...
            activity.startActivityForResult(chooserIntent, fileReqCode);
        } else {
            // FIXME : deprecated ...
            activity.startActivityForResult(intentCamera, fileReqCode);
        }

        return uriCameraImage;
    }

}
