package com.example.testapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.core.app.ActivityCompat;

/**
 * <pre>
 * 안드로이드 파일 유틸
 *      - 파일 읽기 : 텍스트 파일이 아닌 경우에는 해당 Activity 에서 만들어야 할 듯? 종류가 너무 많아서 유틸화 불가
 * </pre>
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    private static final int BUFFER_SIZE = 4096;

    private FileUtil() {
    }

    private static class LazyHolder {
        private static final FileUtil.External INSTANCE_EX = new FileUtil.External();
        private static final FileUtil.Inner INSTANCE_IN = new FileUtil.Inner();
    }

    public static FileUtil.External getExternalInstance() {
        return LazyHolder.INSTANCE_EX;
    }

    public static FileUtil.Inner getInnerInstance() {
        return LazyHolder.INSTANCE_IN;
    }

    /**
     * 외부 저장소
     */
    public static class External {
        public static final int PERMISSIONS_REQUEST = 1;

        /**
         * 권한 팝업 요청
         * 
         * @param context
         * @param activity
         */
        public void checkPermission(Context context, Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // Android 11
                if (!Environment.isExternalStorageManager()) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);

                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                }
            } else {
                // below Android 11
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(context,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    String[] permissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE };
                    ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST);
                }
            }
        }

        /**
         * 외부 저장소 파일
         * 
         * @param filePath
         * @param hasPermission
         * @param environmentDirectory
         * 
         *                             <pre>
         *      : Environment.DIRECTORY_ALARMS ~ Environment.DIRECTORY_MUSIC
         *                             </pre>
         * 
         * @return
         */
        public File getExternalFile(String filePath, boolean hasPermission, String environmentDirectory) {
            File file = null;

            if (hasPermission) {
                file = Environment.getExternalStoragePublicDirectory(filePath);
            } else {
                file = new File(Environment.getExternalStoragePublicDirectory(environmentDirectory).getAbsolutePath(),
                        filePath);
            }

            return file;
        }

        /**
         * 폴더 생성
         * 
         * @param dir
         * @return
         */
        public boolean makeDir(File dir) {
            if (dir == null) {
                return false;
            }

            if (!dir.exists()) {
                return dir.mkdirs();
            }

            return true;
        }

        /**
         * 텍스트 내용을 해당 경로에 파일로 생성
         * 
         * @param file
         * @param text
         */
        public void writeFile(File file, String text) {
            // 안드로이드는 try-with-resources 사용을 못하나 보구만...

            OutputStream os = null;
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(file);
                os = fos;

                os.write(text.getBytes());
                os.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 파일을 문자열로 읽음
         * 
         * @param file
         * @return
         */
        public String readFile(File file) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream is = null;
            FileInputStream fis = null;

            try {
                if (file.exists()) {
                    fis = new FileInputStream(file);
                    is = new BufferedInputStream(fis);

                    int nRead = 0;
                    byte[] buffer = new byte[BUFFER_SIZE];

                    while ((nRead = is.read(buffer)) != -1) {
                        bos.write(buffer, 0, nRead);
                    }

                    bos.flush();
                    is.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bos.toString();
        }

        /**
         * <pre>
         * 파일 삭제
         *  - 폴더안의 파일들을 제거한 뒤 대상 폴더제거
         * </pre>
         * 
         * @param filePath
         * @param hasPermission
         * @param environmentDirectory
         */
        public void removeFile(String filePath, boolean hasPermission, String environmentDirectory) {
            File file = null;

            if (hasPermission) {
                file = Environment.getExternalStoragePublicDirectory(filePath);
            } else {
                file = new File(Environment.getExternalStoragePublicDirectory(environmentDirectory).getAbsolutePath(),
                        filePath);
            }

            if (file.isDirectory()) {
                File[] files = file.listFiles();

                for (File f : files) {
                    removeFile(f.getAbsolutePath(), hasPermission, environmentDirectory);
                    Log.d(TAG, "파일이 삭제되었습니다.");
                }

                file.delete();
                Log.d(TAG, "파일이 삭제되었습니다.");

            } else {
                file.delete();
                Log.d(TAG, "파일이 삭제되었습니다.");
            }
        }
    }

    /**
     * 내부 저장소
     */
    public static class Inner {
        /**
         * 내부 저장소 경로 가져오기
         * 
         * @param context
         * @return
         */
        public String getFilePath(Context context) {
            return context.getFilesDir().getAbsolutePath();
        }

        /**
         * 파일 경로 가져오기
         * 
         * @param context
         * @param fileNm
         * @return
         */
        public String getFilePath(Context context, String fileNm) {
            return context.getFileStreamPath(fileNm).getAbsolutePath();
        }

        /**
         * 텍스트 내용을 파일로 생성
         * 
         * @param context
         * @param fileNm
         * @param text
         */
        public void writeFile(Context context, String fileNm, String text) {
            try {
                FileOutputStream fos = context.openFileOutput(fileNm, context.MODE_PRIVATE);

                fos.write(text.getBytes());
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 파일을 문자열로 읽음
         * 
         * @param context
         * @param fileNm
         * @return
         */
        public String readFile(Context context, String fileNm) {
            String sTxt = "";
            try {
                FileInputStream fis = context.openFileInput(fileNm);

                byte[] buffer = new byte[BUFFER_SIZE];
                fis.read(buffer);
                fis.close();

                sTxt = new String(buffer).trim();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return sTxt;
        }

        /**
         * 파일 삭제
         * 
         * @param context
         * @param fileNm
         * @return
         */
        public boolean removeFile(Context context, String fileNm) {
            File dir = context.getFilesDir();
            File file = new File(dir, fileNm);
            return file.delete();
        }
    }

}
