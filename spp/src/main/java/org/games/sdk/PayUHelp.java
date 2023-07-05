//package org.games.sdk;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.example.webgame.R;
//import com.payumoney.core.PayUmoneyConstants;
//import com.payumoney.core.PayUmoneySdkInitializer;
//import com.payumoney.core.entity.TransactionResponse;
//import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
//import com.payumoney.sdkui.ui.utils.ResultModel;
//
//import org.games.app.MainActivity;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//
////insert class
//
//public class PayUHelp {
//
//    //insert var
//
//    private static String mKEY = "";
//    private static String Tag = "PayUHelp";
//    public static Activity mAppContent;
//    private static PayUHelp mInstace = null;
//    private static PayUmoneySdkInitializer.PaymentParam mPaymentParams;
//    public static PayUHelp getInstance() {
//        if (null == mInstace){
//            mInstace = new PayUHelp();
//        }
//        return mInstace;
//    }
//
//    public void init(Activity AppContent){
//        //insert code
//
//        this.mAppContent = AppContent;
//    }
//
//    public void initKey(String key){
//        //insert code
//
//        mKEY = "rzp_test_BSUHF5CLjVvytg";
//        Log.e(Tag, "init,key = " + key);
//    }
//
//    public void goPay(final String txnId,final String key,final String MerchantId,final String hash,final String amount,final String ProductName,final String customerName,final String customerPhone,final String customerEmail,final String otherUdfData,final String surl,final String furl) {
//        //insert code
//
////        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
////                PayUmoneySdkInitializer.PaymentParam.Builder();
////                 builder.setAmount(amount)                          // Payment amount
////                .setTxnId(txnId)                                             // Transaction ID
////                .setPhone(customerPhone)                                           // User Phone number
////                .setProductName("productName")                   // Product Name or description
////                .setFirstName(customerName)                              // User First name
////                .setEmail(customerEmail)                                            // User Email ID
////                .setsUrl(surl)                    // Success URL (surl)
////                .setfUrl(furl)                     //Failure URL (furl)
////                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
////                .setKey(key)                        // Merchant key
////                .setMerchantId(MerchantId);             // Merchant ID
////
//        Log.e(Tag,"goto");
//        MainActivity.g_this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                PayUmoneySdkInitializer.PaymentParam.Builder builder = new
//                        PayUmoneySdkInitializer.PaymentParam.Builder();
//                builder.setAmount(amount)                          // Payment amount
//                        .setTxnId(txnId)                                             // Transaction ID
//                        .setPhone(customerPhone)                                           // User Phone number
//                        .setProductName(ProductName)                   // Product Name or description
//                        .setFirstName(customerName)                              // User First name
//                        .setEmail(customerEmail)                                            // User Email ID
//                        .setsUrl(surl)                    // Success URL (surl)
//                        .setfUrl(furl)                         // Integration environment - true (Debug)/ false(Production)
//                        .setKey(key)
//                        .setIsDebug(false)// Merchant key
//                        .setUdf5(otherUdfData)// Merchant key
//                        .setMerchantId(MerchantId);             // Merchant ID
//
//                try {
//                    PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();
//
//                    //paymentParam = calculateServerSideHashAndInitiatePayment1(paymentParam);
//
//                    //calculateServerSideHashAndInitiatePayment1(paymentParam);
//                    paymentParam.setMerchantHash(hash);
//                    Log.e(Tag, hash);
//                    // Invoke the following function to open the checkout page.
//                    PayUmoneyFlowManager.startPayUMoneyFlow(
//                            paymentParam,
//                            MainActivity.g_this,
//                            R.style.AppTheme_default,true);
//
//                } catch (Exception e) {
//                    Toast.makeText(MainActivity.g_this, "PayU exception:" + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//
////
////        MainActivity.g_this.runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    Intent intent= new Intent(MainActivity.g_this, PayUActivity.class);
////                    intent.putExtra("txnId", txnId);  //放入数据
////                    intent.putExtra("key", key);  //放入数据
////                    intent.putExtra("MerchantId", MerchantId);  //放入数据
////                    intent.putExtra("hash", hash);  //放入数据
////                    intent.putExtra("amount", amount);  //放入数据
////                    intent.putExtra("customerName", customerName);  //放入数据
////                    intent.putExtra("customerPhone", customerPhone);  //放入数据
////                    intent.putExtra("customerEmail", customerEmail);  //放入数据
////                    intent.putExtra("surl", surl);  //放入数据
////                    intent.putExtra("furl", furl);  //放入数据
////
////                    MainActivity.g_this.startActivity(intent);
////
////                } catch (ActivityNotFoundException e) {
////                    // TODO: handle exception
////                    Toast.makeText(MainActivity.g_this, "Razor pay exception!", Toast.LENGTH_LONG).show();
////                }
////
////            }
////        });
//
////        try {
////            PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();
////            paymentParam.setMerchantHash(hash);
////
////            // Invoke the following function to open the checkout page.
////            PayUmoneyFlowManager.startPayUMoneyFlow(
////                    paymentParam,
////                    mAppContent,
////                    1,true);
////
////        } catch (Exception e) {
////            Toast.makeText(MainActivity.g_this, "Razor pay exception!", Toast.LENGTH_LONG).show();
////        }
//        //declare paymentParam object
//
//        //        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
////            MainActivity.g_this.runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////                    Toast.makeText(MainActivity.g_this, "Your phone not support!", Toast.LENGTH_LONG).show();
////                }
////            });
////            return;
////        }
////        MainActivity.g_this.runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    Intent intent= new Intent(MainActivity.g_this, PaymentActivity.class);
////                    intent.putExtra("appId", appId);  //放入数据
////                    intent.putExtra("orderId", orderId);  //放入数据
////                    intent.putExtra("orderAmount", orderAmount);  //放入数据
////                    intent.putExtra("orderNote", orderNote);  //放入数据
////                    intent.putExtra("customerPhone", customerPhone);  //放入数据
////                    intent.putExtra("customerEmail", customerEmail);  //放入数据
////
////                    MainActivity.g_this.startActivity(intent);
////
////                } catch (ActivityNotFoundException e) {
////                    // TODO: handle exception
////                    Toast.makeText(MainActivity.g_this, "Razor pay exception!", Toast.LENGTH_LONG).show();
////                }
////
////            }
////        });
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //insert code
//
//        // Result Code is -1 send from Payumoney activity
//        Log.e("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
//
//        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == MainActivity.RESULT_OK && data != null) {
//            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );
//            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
//
//
//            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
//
//                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){
//                    //Success Transaction
//                    Log.e(Tag, "Success Transaction!");
//                    Toast.makeText(MainActivity.g_this, "Success Transaction!", Toast.LENGTH_LONG).show();
//                } else{
//                    //Failure Transaction
//                    Log.e(Tag, "Failure Transaction!");
//                    Toast.makeText(MainActivity.g_this, "Failure Transaction!", Toast.LENGTH_LONG).show();
//                }
//
//                // Response from Payumoney
//                String payuResponse = transactionResponse.getPayuResponse();
//
//                // Response from SURl and FURL
//                String merchantResponse = transactionResponse.getTransactionDetails();
//            }  else if (resultModel != null && resultModel.getError() != null) {
//                Log.e(Tag, "Error response : " + resultModel.getError().getTransactionResponse());
//            } else {
//                Log.e(Tag, "Both objects are null!");
//            }
//        }
//    }
//
//    private static  PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {
//        //insert code
//
//        StringBuilder stringBuilder = new StringBuilder();
//        HashMap<String, String> params = paymentParam.getParams();
//        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
//        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");
//
//        stringBuilder.append("eflv7ObItq");
//
//        String hash = hashCal(stringBuilder.toString());
//        Log.e(Tag, hash);
//        paymentParam.setMerchantHash(hash);
//
//        return paymentParam;
//    }
//
//    public static String hashCal(String str) {
//        //insert code
//
//        byte[] hashseq = str.getBytes();
//        StringBuilder hexString = new StringBuilder();
//        try {
//            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
//            algorithm.reset();
//            algorithm.update(hashseq);
//            byte messageDigest[] = algorithm.digest();
//            for (byte aMessageDigest : messageDigest) {
//                String hex = Integer.toHexString(0xFF & aMessageDigest);
//                if (hex.length() == 1) {
//                    hexString.append("0");
//                }
//                hexString.append(hex);
//            }
//        } catch (NoSuchAlgorithmException ignored) {
//        }
//        return hexString.toString();
//    }
//
//
//}
