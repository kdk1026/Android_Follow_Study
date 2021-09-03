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

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.FileProvider;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 9. 01. 김대광	최초 작성
 * 2021. 9. 02. 김대광   runCameraVideo 추가
 * 2021. 9. 03. 김대광   runMicrophone 추가 / deprecated 대응
 * </pre>
 *
 * <pre>
 * 웹뷰 파일 처리 유틸
 * </pre>
 *
 * @author 김대광
 * @Description : minSdkVersion 26 / targetSdkVersion 30
 */
public class WebViewFileUtil {

    private static final String TAG = "AppInfoUtil";

    private WebViewFileUtil() {
    }

    private static class LazyHolder {
        private static final WebViewFileUtil INSTANCE = new WebViewFileUtil();
    }

    public static WebViewFileUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private Uri fileUri = null;

    /**
     * 카메라 이미지 처리
     * @param isCapture
     * @param activity
     * @param fileChooserResult
     * @return
     */
    public Uri runCameraImage(boolean isCapture, Activity activity, ActivityResultLauncher<Intent> fileChooserResult) {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "sample.png");

        // File 객체의 URI를 얻는다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String providerAuthorities = this.context.getPackageName() + ".provider";

            fileUri = FileProvider.getUriForFile(this.context, providerAuthorities, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

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

            fileChooserResult.launch(chooserIntent);
        } else {
            fileChooserResult.launch(intentCamera);
        }

        return fileUri;
    }

    /**
     * 카메라 캠코더 처리
     * @param isCapture
     * @param activity
     * @param fileChooserResult
     * @return
     */
    public Uri runCameraVideo(boolean isCapture, Activity activity, ActivityResultLauncher<Intent> fileChooserResult) {
        Intent intentCamera = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "sample.mp4");

        // File 객체의 URI를 얻는다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String providerAuthorities = this.context.getPackageName() + ".provider";

            fileUri = FileProvider.getUriForFile(this.context, providerAuthorities, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (!isCapture) {
            // 선택 팝업 : 갤러리, 포토 띄우기 (동영상만)
            Intent pickIntent = new Intent(Intent.ACTION_PICK);
            pickIntent.setType(MediaStore.Video.Media.CONTENT_TYPE);
            pickIntent.setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

            String pickTitle = "동영상 가져올 방법을 선택하세요.";
            Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);

            // 선택 팝업에 카메라 포함
            //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[] { intentCamera });

            fileChooserResult.launch(chooserIntent);
        } else {
            fileChooserResult.launch(intentCamera);
        }

        return fileUri;
    }

    /**
     * 음성 녹음 처리
     * @param isCapture
     * @param activity
     * @param fileChooserResult
     * @return
     */
    public Uri runMicrophone(boolean isCapture, Activity activity, ActivityResultLauncher<Intent> fileChooserResult) {
        Intent intentSpeech = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);

        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "sample.m4a");

        // File 객체의 URI를 얻는다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String providerAuthorities = this.context.getPackageName() + ".provider";

            fileUri = FileProvider.getUriForFile(this.context.getApplicationContext(), providerAuthorities, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        intentSpeech.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (!isCapture) {
            // 선택 팝업 : 사운드 선택기
            Intent pickIntent = new Intent(Intent.ACTION_PICK);
            pickIntent.setType(MediaStore.Audio.Media.CONTENT_TYPE);
            pickIntent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);

            String pickTitle = "사운드 가져올 방법을 선택하세요.";
            Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);

            // 선택 팝업에 음성 녹음 포함
            //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[] { intentSpeech });

            fileChooserResult.launch(chooserIntent);
        } else {
            fileChooserResult.launch(intentSpeech);
        }

        return fileUri;
    }

}
