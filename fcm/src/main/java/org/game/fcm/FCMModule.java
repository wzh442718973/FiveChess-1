package org.game.fcm;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import org.game.base.ISDKModule;

/**
 * @author gaoyu
 * @date 2021/11/1
 */
public class FCMModule implements ISDKModule {

    public static final String CHANNEL = "FCM";

    @Override
    public void init(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL, "notify", NotificationManager.IMPORTANCE_HIGH);
            context.getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);

            if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") ==
                        PackageManager.PERMISSION_GRANTED) {
                } else {
                    Activity activity = (Activity) context;
                    activity.requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 0);
                }
            }
        }

    }
}

