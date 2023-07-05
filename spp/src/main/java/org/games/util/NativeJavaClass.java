package org.games.util;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.game.BuildConfig;
import org.game.base.NativeJsCorrespondent;
import org.games.app.App;
import org.games.app.DownloadAgent;
import org.games.app.MainActivity;
import org.games.sdk.FacebookSDK;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.List;


//insert class

public class NativeJavaClass {

    //insert var

    public static String APP_ID = "";
    private static String strRoomID = "";
    private static String Tag = "NativeJavaClass";

    public static String getBatteryLevel() {
        //insert code

        return "100";
//        return String.valueOf(Util.getInstance().getBatteryLevel());
    }

    public static String getBatteryStatusCharging() {
        //insert code

//        return String.valueOf(Util.getInstance().getBatteryStatusCharging());
        return "false";
    }

    private static String buildTransaction(final String type) {
        //insert code

        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static void share_app(String packageName, String className, int type, String title, String content) {
        //insert code

    }

    public static void WXShare(String strJson) {
        //insert code
        try {
            Log.e(Tag, "WXShare");
            JSONObject json = new JSONObject(strJson);
            String title = json.getString("title");
            String content = json.getString("content");
            String imgPath = json.getString("imgPath");
            String url = json.getString("url");
            boolean isSceneSession = json.getBoolean("isSceneSession");
            boolean isHaibao = json.getBoolean("isHaibao");
            ShareUtil.shareText("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI", content, title, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void shareWXSuccess() {
        //insert code

        NativeJavaClass.callJs("shareWXSuccess", "");
    }

    public static void setRoomID(String strJson) {
        //insert code
        try {
            JSONObject json = new JSONObject(strJson);
            String strID = json.getString("strRoomID");
            NativeJavaClass.strRoomID = strID;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getRoomID() {
        //insert code

        return NativeJavaClass.strRoomID;
    }

    public static String getPackageName() {
        //insert code
        return MainActivity.g_this.getPackageName();
    }

    public static String getVersionCode() {
        try {
            PackageManager localPackageManager = MainActivity.g_this.getPackageManager();
            PackageInfo packageInfo = localPackageManager.getPackageInfo(getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode + "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static String getDeviceId() {
        //insert code

        return AndroidUtil.getDeviceId();
    }

    public static void copyToClipBoard(String strJson) {
        //insert code
        try {
            JSONObject json = new JSONObject(strJson);
            final String content = json.getString("content");
            MainActivity.g_this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ClipboardManager myClipboard = (ClipboardManager) MainActivity.g_this.getSystemService(Context.CLIPBOARD_SERVICE);
                    myClipboard.setText(content);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void openWX() {
        //insert code

        MainActivity.g_this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    MainActivity.g_this.startActivity(intent);

                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    Toast.makeText(MainActivity.g_this, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_LONG);
                }

            }
        });

    }

    public static void openQQ(String strJson) {
        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            final String schemeUrl = json.getString("url");

            // 跳转之前，可以先判断手机是否安装QQ
            if (isQQClientAvailable(MainActivity.g_this)) {
                // 跳转到客服的QQ
                String url = schemeUrl;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                // 跳转前先判断Uri是否存在，如果打开一个不存在的Uri，App可能会崩溃
                if (isValidIntent(MainActivity.g_this, intent)) {
                    MainActivity.g_this.startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        //insert code

        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 Uri是否有效
     */
    public static boolean isValidIntent(Context context, Intent intent) {
        //insert code

        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }

    public static String getHostAddress(String strJson) {

        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            String strHost = json.getString("strHost");
            //域名查询
            String dottedQuadIpAddress = InetAddress.getByName(strHost).getHostAddress();
            return dottedQuadIpAddress;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            return "";
        }
    }

    public static String saveFileToGalley(String strJson) {
        //insert code
        JSONObject json = null;
        try {
            json = new JSONObject(strJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String picName = json.optString("strHost");
        String imageLocalPath = json.optString("imageLocalPath");

        boolean IsSave = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (MainActivity.g_this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //没有权限则申请权限
                MainActivity.g_this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                IsSave = false;
            } else {
                //有权限直接执行,docode()不用做处理
                IsSave = true;
            }
        } else {
            //小于6.0，不用申请权限，直接执行
            IsSave = true;
        }

        if (IsSave) {
            Context context = MainActivity.g_this;
            File file = new File(imageLocalPath);
            try {
                String uriString = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), picName, null);

                File file1 = new File(getRealPathFromURI(Uri.parse(uriString), context));
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file1));
                context.sendBroadcast(intent);
                return file1.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return file.getAbsolutePath();
        }

        return "";
    }

    private static void saveQR(String jsonStr) {
        boolean IsSave;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (MainActivity.g_this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //没有权限则申请权限
                MainActivity.g_this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                IsSave = false;
            } else {
                //有权限直接执行,docode()不用做处理
                IsSave = true;
            }
        } else {
            //小于6.0，不用申请权限，直接执行
            IsSave = true;
        }
        if (!IsSave) {
            return;
        }

        String data;
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            data = jsonObject.getString("str");
            if (data.startsWith("data:")) {
                data = data.substring(data.indexOf(",") + 1);
            }

            byte[] bytes = Base64.decode(data, Base64.DEFAULT);
            save2Gallery(bytes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void save2Gallery(byte[] bytes) {
        ContentValues values = new ContentValues();
        ContentResolver cr = App.getInstance().getContentResolver();

        long date = System.currentTimeMillis() / 1000;
        values.put(MediaStore.Images.Media.DATE_ADDED, date);
        values.put(MediaStore.Images.Media.DATE_MODIFIED, date);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");


        File galleryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(galleryPath, System.currentTimeMillis() + ".png");
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".png");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
            uri = cr.insert(MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), values);
        } else {
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            uri = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }

        try {
            OutputStream imageOut = cr.openOutputStream(uri);
            try {
                IOUtil.out4byte(bytes, imageOut);
            } finally {
                imageOut.close();
            }
        } catch (Exception e) {
            if (uri != null) {
                cr.delete(uri, null, null);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 0);
            cr.update(uri, values, null, null);
        } else {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
            App.getInstance().sendBroadcast(intent);
        }
    }

    private static String getRealPathFromURI(Uri contentUri, Context context) {
        //insert code

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String fileStr = cursor.getString(column_index);
        cursor.close();
        return fileStr;
    }


    public static String isNetworkAvailable() {
        //insert code

        boolean ret = false;
        Context context = MainActivity.g_this;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {

        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        ret = true;
                        break;
                    }
                }
            }
        }
        return String.valueOf(ret);
    }


    public static String getChannel() {
        //insert code

        String str = ChannelUtil.getChannel(MainActivity.g_this);
        return str;
    }

    public static void downloadApp(String strJson) {
        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            String url = json.getString("url");
            UpdateApp updateApp = new UpdateApp(MainActivity.g_this, url, new DownloadAgent(MainActivity.g_this));
            updateApp.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void ShowBackIndexBtn() {
        //insert code

        MainActivity.g_this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppFunctionHelp.getInstance().showHomeButton();
            }
        });


    }

    public static void HideBackIndexBtn() {
        //insert code

        AppFunctionHelp.getInstance().hideHomeButton();

    }

    public static void ShowFacebookBtn() {
        //insert code

    }

    public static void HideFacebookBtn() {
        //insert code

    }

    public static void PhoneVibrator(String strJson) {
        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            String Time = json.getString("time");
            Vibrator vibrator = (Vibrator) MainActivity.g_this.getSystemService(MainActivity.g_this.VIBRATOR_SERVICE);
            vibrator.vibrate(Long.parseLong(Time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void backHome() {
        //insert code

        System.out.println("backHome1");
        NativeJavaClass.callJs("backHall", "");
    }

    public static void loginFaceBook() {
        //insert code

        FacebookSDK.getInstance().login(MainActivity.g_this);
    }

    public static void FaceBookLogEvent(String strJson) {

        try {
            JSONObject json = new JSONObject(strJson);
            String EventStr = json.getString("typetoken");
            String NumberData = json.getString("tObjData");
            String CurrencyType = json.getString("tCurrencyType");
            double Num = Double.valueOf(NumberData);

            FacebookSDK.getInstance().FbLogEvent(EventStr, Num, CurrencyType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void FacebookShareUrl(String strJson) {
        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            String url = json.optString("url");
            String Des = json.optString("des", "");
            FacebookSDK.getInstance().shareUrl(url, Des);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void WhatsAppShareUrl(String strJson) {
        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            String url = json.getString("url");
            AppFunctionHelp.getInstance().WhatsAppShareUrl(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getContentFromClipBoard() {
        ClipboardManager myClipboard = (ClipboardManager) MainActivity.g_this.getSystemService(Context.CLIPBOARD_SERVICE);
        if (!myClipboard.hasPrimaryClip()) {
            return "";
        }
        ClipData data = myClipboard.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);
        final String content;
        if (item.getText() != null) {
            content = item.getText().toString();
        } else {
            content = "";
        }
        return content;
    }

    public static void WhatsAppOpenNumMessages(String strJson) {
        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            String url = json.getString("tel");
            AppFunctionHelp.getInstance().WhatsAppOpenNumMessages(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setOrientation(String strJson) {
        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            String dir = json.getString("dir");
            Log.e(Tag, "setOrientation: " + dir);

            AppFunctionHelp.getInstance().setOrientation(dir);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void trackEvent(String strJson) {
        //insert code

        //        try {
        //            JSONObject json = new JSONObject(strJson);
        //            final String eventType = json.getString("type");
        //            final String jsonData = json.getString("jsonData");
        //            MainActivity.g_this.runOnUiThread(new Runnable() {
        //                @Override
        //                public void run() {
        //                    AppsflyerHelp.getInstance().trackEvent(eventType, jsonData);
        //                }
        //            });
        //        } catch(JSONException e) {
        //            e.printStackTrace();
        //        }
    }

    public static String getMediaSource() {
        //insert code

        //        return AppsflyerHelp.getInstance().getMediaSource();
        return null;
    }

    public static void goPay(String strJson) {
        //insert code
        try {
            JSONObject json = new JSONObject(strJson);
            String txnId = json.getString("txnId");
            String key = json.getString("key");
            String MerchantId = json.getString("MerchantId");
            String hash = json.getString("hash");
            String amount = json.getString("amount");
            String orderNote = json.getString("orderNote");
            String customerName = json.getString("customerName");
            String customerPhone = json.getString("customerPhone");
            String customerEmail = json.getString("customerEmail");
            String notifyUrl = json.getString("notifyUrl");
            String surl = json.getString("surl");
            String furl = json.getString("furl");
            //            PayUHelp.getInstance().goPay(txnId, key, MerchantId, hash, amount, orderNote, customerName, customerPhone, customerEmail, notifyUrl, surl, furl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void razorPay(String strJson) {
        //insert code

        try {
            JSONObject json = new JSONObject(strJson);
            int id = json.getInt("id");
            String method = json.getString("method");
            String appId = json.getString("appId");
            String orderId = json.getString("orderId");
            String orderAmount = json.getString("orderAmount");
            String orderNote = json.getString("orderNote");
            String customerName = json.getString("customerName");
            String customerPhone = json.getString("customerPhone");
            String customerEmail = json.getString("customerEmail");
            String notifyUrl = json.getString("notifyUrl");
            //            RazorPayHelp.getInstance().goPay(id, method, appId, orderId, orderAmount, orderNote, customerName, customerPhone, customerEmail, notifyUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void logEvent(String strJson) {
        //insert code

    }

    public static void googlePay(String strJson) {
        //insert code
        //        try {
        //            JSONObject json = new JSONObject(strJson);
        //            String uid = json.getString("uid");
        //            String orderid = json.getString("orderid");
        //            String productid = json.getString("productId");
        //            GPHelp.getInstance().purchase(uid, orderid, productid);
        //        } catch(JSONException e) {
        //            e.printStackTrace();
        //        }
    }

    public static void PaytmPay(String strJson) {
        //insert code
        try {
            JSONObject json = new JSONObject(strJson);
            String orderid = json.getString("orderid");
            String mid = json.getString("mid");
            String txnToken = json.getString("txnToken");
            String amount = json.getString("amount");
            String callbackurl = json.getString("callbackurl");
            String orderNote = json.getString("orderNote");
            //            PaytmPayHelp.getInstance().goPay(orderid, mid, txnToken, amount, callbackurl, orderNote);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void googlePayCallBack(final String jsCode) {
        // TODO: 2021/4/22 没改
        callJs(jsCode, jsCode);
    }

    public static void googlePayConsume(String strJson) {
        //insert code

        //        try {
        //            JSONObject json = new JSONObject(strJson);
        //            String purchaseToken = json.getString("purchaseToken");
        //            GPHelp.getInstance().consumeAsync(purchaseToken);
        //        } catch(JSONException e) {
        //            e.printStackTrace();
        //        }
    }

    public static void googlePayAddOrder() {
        //insert code

        //        GPHelp.getInstance().addOrder();
    }

    public static String isProbablyAnEmulator() {
        //insert code

        return String.valueOf(AndroidUtil.isProbablyAnEmulator());
    }

    public static String getAdvertisingId() {
        return AndroidUtil.getAdvertisingId();
    }

    public static void getInstall(String strJson) {
        //insert code

    }

    public static void registerWakeup() {

        //insert code

    }

    public static void onBackKey() {
        NativeJavaClass.callJs("onBackKey", "");
    }


    public static void LineAppShareUrl(String jsons) {
        try {
            JSONObject json = new JSONObject(jsons);
            String url = json.optString("url");
            String Des = json.optString("des", "");

            String scheme = "line://msg/text/" + url;
            Uri uri = Uri.parse(scheme);
            MainActivity.g_this.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException e) {
            MainActivity.g_this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.g_this, "LineApp have not been installed.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String callJava(String method, String strJson) {
        switch (method) {
            case "getBatteryLevel":
                return NativeJavaClass.getBatteryLevel();
            case "getContentFromClipBoard":
                return NativeJavaClass.getContentFromClipBoard();
            case "LineAppShareUrl":
                NativeJavaClass.LineAppShareUrl(strJson);
                break;
            case "getBatteryStatusCharging":
                return NativeJavaClass.getBatteryStatusCharging();
            case "loginFaceBook":
                NativeJavaClass.loginFaceBook();
                break;
            case "WXShare":
                NativeJavaClass.WXShare(strJson);
                break;
            case "FacebookShareUrl":
                NativeJavaClass.FacebookShareUrl(strJson);
                break;
            case "WhatsAppShareUrl":
                NativeJavaClass.WhatsAppShareUrl(strJson);
                break;
            case "WhatsAppOpenNumMessages":
                NativeJavaClass.WhatsAppOpenNumMessages(strJson);
                break;
            case "getRoomID":
                return NativeJavaClass.getRoomID();
            case "setRoomID":
                NativeJavaClass.setRoomID(strJson);
                break;
            case "getPackageName":
                return NativeJavaClass.getPackageName();
            case "getApkVersion":
                return getVersionCode();
            case "getPlatformVersion":
                return BuildConfig.platformVersion + "";
            case "exitApp":
                System.exit(0);
                return "";
            case "getDeviceId":
                return NativeJavaClass.getDeviceId();
            case "copyToClipBoard":
                NativeJavaClass.copyToClipBoard(strJson);
                break;
            case "openWX":
                NativeJavaClass.openWX();
                break;
            case "openQQ":
                NativeJavaClass.openQQ(strJson);
                break;
            case "getHostAddress":
                return NativeJavaClass.getHostAddress(strJson);
            case "saveFileToGalley":
                return NativeJavaClass.saveFileToGalley(strJson);
            case "saveQR":
                NativeJavaClass.saveQR(strJson);
                break;
            case "isNetworkAvailable":
                return NativeJavaClass.isNetworkAvailable();
            case "getChannel":
                return NativeJavaClass.getChannel();
            case "downloadApp":
                NativeJavaClass.downloadApp(strJson);
                break;
            case "ShowBackIndexBtn":
            case "showHomeButton":
                NativeJavaClass.ShowBackIndexBtn();
                break;
            case "HideBackIndexBtn":
            case "hideHomeButton":
                NativeJavaClass.HideBackIndexBtn();
                break;
            case "ShowFacebookBtn":
                NativeJavaClass.ShowFacebookBtn();
                break;
            case "HideFacebookBtn":
                NativeJavaClass.HideFacebookBtn();
                break;
            case "setOrientation":
                NativeJavaClass.setOrientation(strJson);
                break;
            case "getInstall":
                NativeJavaClass.getInstall(strJson);
                break;
            case "PhoneVibrator":
                NativeJavaClass.PhoneVibrator(strJson);
                break;
            case "razorPay":
                NativeJavaClass.razorPay(strJson);
                break;
            case "goPay":
                NativeJavaClass.goPay(strJson);
                break;
            case "getMediaSource":
                return NativeJavaClass.getMediaSource();
            case "getAdvertisingId":
                return NativeJavaClass.getAdvertisingId();
            case "isProbablyAnEmulator":
                return NativeJavaClass.isProbablyAnEmulator();
            case "trackEvent":
                NativeJavaClass.trackEvent(strJson);
                break;
            case "logEvent":
                NativeJavaClass.logEvent(strJson);
                break;
            case "FaceBookLogEvent":
                NativeJavaClass.FaceBookLogEvent(strJson);
                break;
            case "googlePay":
                NativeJavaClass.googlePay(strJson);
                break;
            case "googlePayConsume":
                NativeJavaClass.googlePayConsume(strJson);
                break;
            case "googlePayAddOrder":
                NativeJavaClass.googlePayAddOrder();
                break;
            case "PaytmPay":
                break;
            case "GetGalleyPermission":
                NativeJavaClass.GetGalleyPermission();
                break;
            case "openInternalWindow":
                NativeJavaClass.openInternalWindow(strJson);
                break;
            default:
                Log.e(Tag, "callJava error, methon: " + method);
                break;
        }
        return "";
    }

    public static void openInternalWindow(String strJson) {
        try {
            JSONObject json = new JSONObject(strJson);
            MainActivity.g_this.isInternalWindow = json.optBoolean("value", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void GetGalleyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (MainActivity.g_this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //没有权限则申请权限
                MainActivity.g_this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                //有权限直接执行,docode()不用做处理
            }
        } else {
            //小于6.0，不用申请权限，直接执行
        }
    }

    public static void callJs(String method, String strJson) {
        NativeJsCorrespondent.getInstance().callJS(method, strJson);
    }

    public static void notifyEvent(String action, int value) {
        NativeJsCorrespondent.getInstance().callJS("notifyEvent", "{\"action\":\"" + action + "\",\"value\":" + value + "}");
    }

}
