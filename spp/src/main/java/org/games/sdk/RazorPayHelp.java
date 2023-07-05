//package org.games.sdk;
//
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.content.Intent;
//import android.os.Build;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.razorpay.Checkout;
//
//import org.games.app.MainActivity;
//import org.games.app.PaymentActivity;
//
////insert class
//
//public class RazorPayHelp {
//
//    //insert var
//
//    private static String mKEY = "";
//    private static String Tag = "RazorpayHelp";
//    Activity mAppContent;
//    private static RazorPayHelp mInstace = null;
//    public static RazorPayHelp getInstance() {
//        if (null == mInstace){
//            mInstace = new RazorPayHelp();
//        }
//        return mInstace;
//    }
//
//    public void init(Activity AppContent){
//        //insert code
//
//        Checkout.preload(AppContent.getApplicationContext());
//    }
//
//    public void initKey(String key){
//        //insert code
//
//        mKEY = "rzp_test_BSUHF5CLjVvytg";
//        Log.d(Tag, "init,key = " + key);
//    }
//
//    public void goPay(final int id, final String method,final String appId,final String orderId,final String orderAmount,final String orderNote,final String customerName,final String customerPhone,final String customerEmail,String notifyUrl) {
//        //insert code
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            MainActivity.g_this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(MainActivity.g_this, "Your phone not support!", Toast.LENGTH_LONG).show();
//                }
//            });
//            return;
//        }
//        MainActivity.g_this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Intent intent= new Intent(MainActivity.g_this, PaymentActivity.class);
//                    intent.putExtra("appId", appId);  //放入数据
//                    intent.putExtra("orderId", orderId);  //放入数据
//                    intent.putExtra("orderAmount", orderAmount);  //放入数据
//                    intent.putExtra("orderNote", orderNote);  //放入数据
//                    intent.putExtra("customerPhone", customerPhone);  //放入数据
//                    intent.putExtra("customerEmail", customerEmail);  //放入数据
//                    intent.putExtra("method", method);  //放入数据
//
//
//
//                    MainActivity.g_this.startActivity(intent);
//
//                } catch (ActivityNotFoundException e) {
//                    // TODO: handle exception
//                    Toast.makeText(MainActivity.g_this, "Razor pay exception!", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//    }
//}
