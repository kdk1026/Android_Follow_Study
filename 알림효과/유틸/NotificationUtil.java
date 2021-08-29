package com.example.mytest.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.example.mytest.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 8. 29. 김대광	최초 작성
 * </pre>
 *
 * <pre>
 * 알림바 유틸
 *  - 채널 ID/명, 아이콘 이미지만 변경해서 사용 가능할 듯
 * </pre>
 *
 * @author 김대광
 * @Description	: minSdkVersion 26 / targetSdkVersion 30
 */
public class NotificationUtil {

    private static final String TAG = "NotificationUtil";

    private NotificationUtil() {
    }

    private static class LazyHolder {
        private static final NotificationUtil INSTANCE = new NotificationUtil();
    }

    public static NotificationUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static final String CHANNEL_ID = "kdk_channel";
    private static final String CHANNEL_NAME = "kdk";

    private Context context;
    private NotificationManager notificationManager;

    public void setContext(Context context) {
        this.context = context;
    }

    private void createNotificationChannel(boolean vibration) {
        notificationManager = context.getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);

            if ( !vibration ) {
                channel.setVibrationPattern(new long[]{0}); // 진동 무음
                channel.enableVibration(true); // 진동 무음
            }

            notificationManager.createNotificationChannel(channel);
        }
    }

    public void makeNotify(boolean vibration, int notifyId, String title, String content) {
        this.createNotificationChannel(vibration);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        notificationManager.notify(notifyId, notification);
    }

    public void stopNotify(int notifyId, boolean cancelAll) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);

        notificationManager.cancel(notifyId);

        if ( cancelAll ) {
            notificationManager.cancelAll();
        }
    }

}
