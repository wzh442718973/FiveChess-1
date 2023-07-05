//package org.games.app;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.BatteryManager;
//
//import org.games.util.Util;
//
////insert class
//
//public class BatteryBroadcastReceiver extends BroadcastReceiver{
//
//    //insert var
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        //insert code
//
//        // TODO Auto-generated method stub
//        //判断它是否是为电量变化的Broadcast Action
//        if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
//            //获取当前电量
//            int level = intent.getIntExtra("level", 0);
//            //电量的总刻度
//            int scale = intent.getIntExtra("scale", 100);
//            //把它转成百分比
//            Util.batteryLevel =  level * 100 / scale;
//
//            //电池状态，返回是一个数字
//            // BatteryManager.BATTERY_STATUS_CHARGING 表示是充电状态
//            // BatteryManager.BATTERY_STATUS_DISCHARGING 放电中
//            // BatteryManager.BATTERY_STATUS_NOT_CHARGING 未充电
//            // BatteryManager.BATTERY_STATUS_FULL 电池满
//            //Log.d("Battery", "" + intent.getIntExtra("status",BatteryManager.BATTERY_STATUS_UNKNOWN));
//            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
//            if(status==BatteryManager.BATTERY_STATUS_CHARGING)
//            {
//            	Util.batteryStatusCharging = true;
//            }
//            else
//            {
//            	Util.batteryStatusCharging = false;
//            }
//        }
//        else if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
//        	Util.batteryStatusCharging = true;
//        } else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
//        	Util.batteryStatusCharging = false;
//        }
//    }
//}
