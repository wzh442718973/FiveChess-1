//package org.games.util;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//import android.os.PowerManager;
//
//import org.games.app.App;
//import org.games.app.BatteryBroadcastReceiver;
//
//import java.io.ByteArrayOutputStream;
//
////insert class
//
//public class Util {
//
//    //insert var
//
//    PowerManager.WakeLock mWakeLock;
//
//    BatteryBroadcastReceiver receiver = new BatteryBroadcastReceiver();
//    IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//    public static int batteryLevel = 0;
//    public static boolean batteryStatusCharging = false;
//
//
//    private static Util mInstace = null;
//
//    public static Util getInstance() {
//        if (null == mInstace) {
//            mInstace = new Util();
//        }
//        return mInstace;
//    }
//
//    public void init() {
//        //insert code
//
//        PowerManager pm = (PowerManager) App.getInstance().getSystemService(Context.POWER_SERVICE);
//        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");
//        mWakeLock.acquire();
//
//        App.getInstance().registerReceiver(receiver, filter);
//    }
//
//    public void onResume() {
//        //insert code
//
//        if (mWakeLock == null) {
//            PowerManager pm = (PowerManager) App.getInstance().getSystemService(Context.POWER_SERVICE);
//            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");
//            mWakeLock.acquire();
//        }
//    }
//
//    public void onPause() {
//        //insert code
//
//        if (mWakeLock != null) {
//            mWakeLock.release();
//            mWakeLock = null;
//        }
//    }
//
//    public void onDestroy() {
//        //insert code
//
//        if (mWakeLock != null) {
//            mWakeLock.release();
//        }
//        App.getInstance().unregisterReceiver(receiver);
//    }
//
//    //获取电量信息
//    public static int getBatteryLevel() {
//        //insert code
//
//        return batteryLevel;
//    }
//
//    //获取是否充电
//    public static boolean getBatteryStatusCharging() {
//        //insert code
//
//        return batteryStatusCharging;
//    }
//
//    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
//        //insert code
//
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        bmp.compress(CompressFormat.PNG, 100, output);
//        if (needRecycle) {
//            bmp.recycle();
//        }
//
//        byte[] result = output.toByteArray();
//        try {
//            output.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//}
