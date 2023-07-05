//package org.games.sdk;
//
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.os.Build;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import com.paytm.pgsdk.PaytmOrder;
//import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
//import com.paytm.pgsdk.TransactionManager;
//import com.razorpay.Checkout;
//
//import org.games.app.MainActivity;
//
//
////insert class
//
//public class PaytmPayHelp {
//
//    //insert var
//
//    private static String mKEY = "";
//    private static String Tag = "PaytmPayHelp";
//    Integer ActivityRequestCode = 2;
//    Activity mAppContent;
//    private static PaytmPayHelp mInstace = null;
//    public static PaytmPayHelp getInstance() {
//        if (null == mInstace){
//            mInstace = new PaytmPayHelp();
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
//    public void goPay(final String orderid, final String mid,final String txnToken,final String amount,final String callbackurl,final String orderNote) {
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
//                    PaytmOrder paytmOrder = new PaytmOrder(orderid, mid, txnToken, amount, callbackurl);
//                    TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
//
//                        @Override
//                        public void onTransactionResponse(Bundle bundle) {
//                            Toast.makeText(MainActivity.g_this, "Response (onTransactionResponse) : "+bundle.toString(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void networkNotAvailable() {
//
//                        }
//
//                        @Override
//                        public void onErrorProceed(String s) {
//
//                        }
//
//                        @Override
//                        public void clientAuthenticationFailed(String s) {
//
//                        }
//
//                        @Override
//                        public void someUIErrorOccurred(String s) {
//
//                        }
//
//                        @Override
//                        public void onErrorLoadingWebPage(int i, String s, String s1) {
//
//                        }
//
//                        @Override
//                        public void onBackPressedCancelTransaction() {
//
//                        }
//
//                        @Override
//                        public void onTransactionCancel(String s, Bundle bundle) {
//
//                        }
//                    });
//
//                    //transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
//                    transactionManager.startTransaction(MainActivity.g_this, ActivityRequestCode);
//                } catch (ActivityNotFoundException e) {
//                    // TODO: handle exception
//                    Toast.makeText(MainActivity.g_this, "PaytmPayHelp exception!", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//    }
//}
