package org.games.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import org.games.app.MainActivity;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by dd on 2017/10/31.
 */

//insert class
public class AndroidUtil {

    //insert var

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    private static String advertisingId = "";
    private static Handler myhandler = new Handler();

    public static String getDeviceId() {
        //insert code
        Context context = MainActivity.g_this;
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        try{
            if(!"9774d56d682e549c".equals(androidId)) {
                UUID uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                return uuid.toString();
            } else {
                UUID uuid=  UUID.randomUUID();
                return uuid.toString();
            }

        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }


    public static String getDeviceName() {
        //insert code

        return android.os.Build.MODEL;
    }


    public static Uri getResUri(Context context, int resId) {
        //insert code

        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resId);
    }

    public static int getResourceId(String packageName, String className, String idName) {
        //insert code

        int id = 0;
        try {
            Class<?> cls = Class.forName(packageName + ".R$" + className);
            id = cls.getField(idName).getInt(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getMetaDataFromAppication(String key) {
        //insert code

        Context context = MainActivity.g_this;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static int getMetaDataIntFromAppication(String key) {
        //insert code

        Context context = MainActivity.g_this;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getInt(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static boolean getMetaDataBooleanFromAppication(String key) {
        //insert code

        Context context = MainActivity.g_this;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getBoolean(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isProbablyAnEmulator() {
        //insert code

        Context context = MainActivity.g_this;
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canCallPhone = intent.resolveActivity(context.getPackageManager()) != null;
        SensorManager sensorManager = (SensorManager) context.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // 获取光线传感器
        boolean ret = false;
        if(!canCallPhone || lightSensor == null) {
            ret = true;
        }
        return ret;
    }

    public static int getVersionCode() {
        //insert code

        Context context = MainActivity.g_this;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void initAdid() {
        //insert code

        Log.d("AdvertisingIdClient", "initAdid");

        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();//增加部分
                try {
                    AdvertisingIdClient.AdInfo adInfo = AdvertisingIdClient
                            .getAdvertisingIdInfo(MainActivity.g_this);
                    advertisingId = adInfo.getId();
                    boolean optOutEnabled = adInfo.isLimitAdTrackingEnabled();
                    Log.d("AdvertisingIdClient", "advertisingId:" + advertisingId);
                    Log.d("AdvertisingIdClient", "optOutEnabled:" + optOutEnabled);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                myhandler.sendEmptyMessage(0);
                Looper.loop();//增加部分
            }
        }).start();
    }
    public static String getAdvertisingId() {
        //insert code

        return advertisingId;
    }

}
