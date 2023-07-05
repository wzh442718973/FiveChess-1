//package org.games.sdk;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.ApplicationInfo;
//import android.util.Log;
//
//
//import org.games.util.NativeJavaClass;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
////insert class
//
//public class GPHelp {
//
//    //insert var
//
//    private static final String TAG = "GPHelp";
//    private static final boolean IS_DEBUG = true;
//    private static BillingClient mBillingClient;
//    private static BillingClient.Builder builder;
//    private PurchasesUpdatedListener mPurchasesUpdatedListener = new MyPurchasesUpdatedListener();
//    private MyConsumeResponseListener mConsumeResponseListener = new MyConsumeResponseListener();
//
//    private String mSku = "1001";
//    private String goods_number;//  商品ID，游戏提供
//    //订单号
//    private String mOrderID;
//    //用户信息
//    private String mUserID;
//
//    private int mInitCode = 100;
//
//    private static final GPHelp mGPhelp = new GPHelp();
//
//    private Activity mContext = null;
//
//    private GPHelp() {
//    }
//
//    public static GPHelp getInstance() {
//        return mGPhelp;
//    }
//
//    public boolean init(Activity context) {
//        //insert code
//
//        String str = this.isApkInDebug(context) ? "debug" : "release";
//        log(str);
//
//        mContext = context;
//        if(true) {
//            builder = BillingClient.newBuilder(context);
//            builder.enablePendingPurchases();
//            mBillingClient = builder.setListener(mPurchasesUpdatedListener).build();
//            if(!mBillingClient.isReady()) {
//                mBillingClient.startConnection(new BillingClientStateListener() {
//                    @Override
//                    public void onBillingSetupFinished(BillingResult billingResult) {
//                        //insert code
//
//                        if(billingResult != null && billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                            // getLTOrderID();
//                            mInitCode = BillingClient.BillingResponseCode.OK;
//                            log("onBillingSetupFinished.");
//                        } else {
//                            int code = billingResult.getResponseCode();
//                            String str = billingResult == null ? "result is null" : "code " + code;
//                            log("onBillingSetup error, msg: " + str);
//                            mInitCode = code;
//                        }
//                    }
//
//                    @Override
//                    public void onBillingServiceDisconnected() {
//                        //insert code
//
//                        log("BillingServiceDisconnected!");
//                    }
//                });
//            } else {
//                log("mBillingClient is not ready!");
//                return false;
//            }
//            return true;
//        }
//        return false;
//    }
//
//    public static boolean isApkInDebug(Context context) {
//        //insert code
//
//        try {
//            ApplicationInfo info = context.getApplicationInfo();
//            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
//        } catch(Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    void release() {
//        //insert code
//
//        if(mBillingClient != null && mBillingClient.isReady()) {
//            mBillingClient.endConnection();
//        }
//    }
//
//    public void purchase(String uid, String orderid, String productid) {
//        //insert code
//
//        String strMsg = "run purchase,uid:" + uid + ",orderid:" + orderid + ",productid:" + productid;
//        mUserID = uid;
//        mOrderID = orderid;
//        mSku = productid;
//        log(strMsg);
//        if(mInitCode != BillingClient.BillingResponseCode.OK) {
//            String str = "Billing service init error, msg: code " + mInitCode;
//            log(str);
//            toast(str);
//            return;
//        }
//        if(mBillingClient.isReady()) {
//            //            this.queryPurchases();
//
//            List<String> skuList = new ArrayList<>();
//            skuList.add(mSku);
//            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
//            params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
//            mBillingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
//                @Override
//                public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
//                    if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
//                        String str = "purchase resp success, skuDetailsList: " + skuDetailsList.toString();
//                        log(str);
//                        if(skuDetailsList.size() == 0) {
//                            toast("purchase failed,sku list is null.");
//                            return;
//                        }
//                        for(SkuDetails skuDetails : skuDetailsList) {
//                            String sku = skuDetails.getSku();
//                            log("sku = " + sku);
//                            if(mSku.equals(sku)) {
//                                BillingFlowParams purchaseParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).setObfuscatedAccountId(mUserID).setObfuscatedProfileId(mOrderID).build();
//                                BillingResult result = mBillingClient.launchBillingFlow(mContext, purchaseParams);
//                                String debugMessage = result.getDebugMessage();
//                                int responseCode = billingResult.getResponseCode();
//                                log("launchBillingFlow: BillingResponse " + responseCode + " " + debugMessage);
//                            }
//                        }
//                    } else {
//                        String str = "purchase resp error, code:" + billingResult.getResponseCode() + ",msg:" + billingResult.getDebugMessage();
//                        log(str);
//                        toast(str);
//                    }
//                }
//            });
//
//        } else {
//            String str = "Billing client is not ready";
//            log(str);
//            toast(str);
//        }
//    }
//
//    private List<Purchase> queryPurchases() {
//        //insert code
//
//        if(mBillingClient == null) {
//            return null;
//        }
//        if(!mBillingClient.isReady()) {
//            return null;
//        } else {
//            Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
//            if(purchasesResult != null) {
//                if(purchasesResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                    List<Purchase> purchaseList = purchasesResult.getPurchasesList();
//                    if(purchaseList != null) {
//                        for(Purchase purchase : purchaseList) {
//                            consumeAsync(purchase.getPurchaseToken());
//                        }
//                    }
//                    return purchaseList;
//                }
//            }
//
//        }
//        return null;
//    }
//
//    /**
//     * Google购买商品回调接口(订阅和内购都走这个接口)
//     */
//    private class MyPurchasesUpdatedListener implements PurchasesUpdatedListener {
//
//        @Override
//        public void onPurchasesUpdated(@NonNull BillingResult result, @Nullable List<Purchase> list) {
//            //insert code
//
//            int responseCode = result.getResponseCode();
//            if(responseCode == BillingClient.BillingResponseCode.OK && list != null) {
//                String str = "purchase success, list:" + list.toString();
//                log(str);
//                for(Purchase purchase : list) {
//                    String sku = purchase.getSku();
//                    String strLog = "purchase product id:" + sku;
//                    log(strLog);
//                    if(sku != null) {
//                        String getPackageName = purchase.getPackageName();
//                        String token = purchase.getPurchaseToken();
//                        AccountIdentifiers account = purchase.getAccountIdentifiers();
//                        String orderid = account == null ? "" : account.getObfuscatedProfileId();
//                        JSONObject json = new JSONObject();
//                        try {
//                            json.put("responseCode", responseCode);
//                            json.put("getPackageName", getPackageName);
//                            json.put("token", token);
//                            json.put("sku", sku);
//                            json.put("orderid", orderid);
//                        } catch(JSONException e) {
//                            e.printStackTrace();
//                        }
//                        String rStr = "googlePayCallBack: " + json.toString();
//                        log(rStr);
//                        NativeJavaClass.callJs("googlePayCallBack", json.toString());
//                    } else {
//                        String strMsg = "get productid error:" + list.toString();
//                        log(strMsg);
//                        toast(strMsg);
//                    }
//                }
//            } else {
//                String str = "purchase call back error, code:" + responseCode + ",msg:" + result.getDebugMessage();
//                log(str);
//                JSONObject json = new JSONObject();
//                try {
//                    json.put("responseCode", responseCode);
//                } catch(JSONException e) {
//                    e.printStackTrace();
//                }
//                NativeJavaClass.callJs("googlePayCallBack", json.toString());
//                toast(str);
//            }
//        }
//    }
//
//    private class MyConsumeResponseListener implements ConsumeResponseListener {
//
//        @Override
//        public void onConsumeResponse(@NonNull BillingResult result, @NonNull String s) {
//            //insert code
//
//            int responseCode = result.getResponseCode();
//            String str;
//            if(responseCode == BillingClient.BillingResponseCode.OK) {
//                str = "consume response success.";
//                JSONObject json = new JSONObject();
//                NativeJavaClass.callJs("consumeResponse", json.toString());
//            } else {
//                str = "consume response error, code:" + responseCode + ",msg:" + result.getDebugMessage();
//                toast(str);
//            }
//            log(str);
//        }
//    }
//
//    /**
//     * 补单操作
//     */
//    public void addOrder() {
//        mBillingClient = BillingClient.newBuilder(mContext).setListener(mPurchasesUpdatedListener).enablePendingPurchases().build();
//        mBillingClient.startConnection(new BillingClientStateListener() {
//            @Override
//            public void onBillingSetupFinished(BillingResult billingResult) {
//                //insert code
//
//                if(billingResult != null) {
//                    if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                        Purchase.PurchasesResult mResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
//                        for(int i = 0; i < mResult.getPurchasesList().size(); i++) {
//                            Purchase p = mResult.getPurchasesList().get(i);
//                            if(p.isAcknowledged()) {//服务器验证
//                                consumeAsync(p.getPurchaseToken());
//                            } else {//服务器未验证走验证流程
//                                String token = p.getPurchaseToken();
//                                JSONObject json = new JSONObject();
//                                try {
//                                    json.put("token", token);
//                                } catch(JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                NativeJavaClass.callJs("addOrder", json.toString());
//                            }
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onBillingServiceDisconnected() {
//                //insert code
//
//            }
//        });
//    }
//
//    public void consumeAsync(String purchaseToken) {
//        //insert code
//
//        log("consumeAsync, purchaseToken " + purchaseToken);
//        if(mBillingClient == null) {
//            log("consumeAsync client is not ready!");
//            return;
//        }
//        ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(purchaseToken).build();
//        mBillingClient.consumeAsync(consumeParams, mConsumeResponseListener);
//    }
//
//    private static void log(String msg) {
//        if(IS_DEBUG) {
//            Log.e(TAG, msg);
//        }
//    }
//
//    public void toast(String str) {
//        //insert code
//
//        JSONObject json = new JSONObject();
//        NativeJavaClass.callJs("removeLoading", json.toString());
//
//        try {
//            json.put("str", str);
//        } catch(JSONException e) {
//            e.printStackTrace();
//        }
//        NativeJavaClass.callJs("toast", json.toString());
//    }
//}
