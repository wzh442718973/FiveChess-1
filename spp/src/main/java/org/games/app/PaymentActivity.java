//package org.games.app;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.razorpay.Checkout;
//import com.razorpay.PaymentData;
//import com.razorpay.PaymentResultWithDataListener;
//
//import org.games.util.NativeJavaClass;
//import org.json.JSONException;
//import org.json.JSONObject;
//
////insert class
//
//public class PaymentActivity extends Activity implements PaymentResultWithDataListener {
//
//    //insert var
//
//    private static final String TAG = PaymentActivity.class.getSimpleName();
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //insert code
//
//        String appId = this.getIntent().getStringExtra("appId");
//        String orderId = this.getIntent().getStringExtra("orderId");
//        String orderAmount = this.getIntent().getStringExtra("orderAmount");
//        String customerPhone = this.getIntent().getStringExtra("customerPhone");
//        String customerEmail = this.getIntent().getStringExtra("customerEmail");
//        String orderNote = this.getIntent().getStringExtra("orderNote");
//        String method = this.getIntent().getStringExtra("method");
//
//        /*
//         To ensure faster loading of the Checkout form,
//          call this method as early as possible in your checkout flow.
//         */
//        Checkout.preload(getApplicationContext());
//
//        Log.d(TAG, "onCreate");
//
//        startPayment(appId, orderId, orderAmount, orderNote, customerPhone, customerEmail, method);
//
//
//    }
//
//    public void startPayment(String appId, String orderId, String orderAmount, String orderNote, String customerPhone, String customerEmail, String method) {
//        //insert code
//
//        /*
//          You need to pass current activity in order to let Razorpay create CheckoutActivity
//         */
//        final Activity activity = this;
//
//        final Checkout co = new Checkout();
//        co.setKeyID(appId);
//
//        try {
//            JSONObject options = new JSONObject();
//            options.put("name", "");
//            //options.put("description", "Demoing Charges");
//            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("currency", orderNote);
//            options.put("amount", orderAmount);
//            options.put("order_id", orderId);
//            JSONObject preFill = new JSONObject();
//            preFill.put("email", customerEmail);
//            preFill.put("contact", customerPhone);
//            preFill.put("method", method);
//
//            options.put("prefill", preFill);
//
//            co.open(activity, options);
//        } catch(Exception e) {
//            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onPaymentSuccess(String s, PaymentData paymentData) {
//        //insert code
//
//        Log.d(TAG, "onPaymentSuccess:" + s);
//        final String orderid = paymentData.getOrderId();
//        final String paymentId = paymentData.getPaymentId();
//        final String signature = paymentData.getSignature();
//        Log.d(TAG, "orderid:" + orderid);
//        Log.d(TAG, "paymentId:" + paymentId);
//        Log.d(TAG, "signature:" + signature);
//        MainActivity.g_this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                JSONObject json = new JSONObject();
//                try {
//                    json.put("orderid", orderid);
//                    json.put("paymentId", paymentId);
//                    json.put("signature", signature);
//                } catch(JSONException e) {
//                    e.printStackTrace();
//                }
//                NativeJavaClass.callJs("RazorPayResult", json.toString());
//
//            }
//        });
//        this.finish();
//    }
//
//    @Override
//    public void onPaymentError(int i, String s, PaymentData paymentData) {
//        //insert code
//
//        Log.d(TAG, "onPaymentError in onPaymentError:" + i + ",response:" + s);
//        this.finish();
//    }
//}
