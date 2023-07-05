package org.games.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.game.base.NativeJsCorrespondent;
import org.games.util.NativeJavaClass;

//import org.games.sdk.UMSDKHelp;

//insert class

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    //insert var

    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;
//        CrashReport.initCrashReport(getApplicationContext(), "0d9c1e3ba7", false);

        //insert code

        initMetaData();

        //        忽略gp的sdk版本检测
        //        GooglePlayServicesUtilLight.enableUsingApkIndependentContext();

        //        AppsflyerHelp.getInstance().init(this, apps_flyer_key);

        //UMSDKHelp.getInstance().init(this, UMENG_APP_KEY);
        String pn = getCurrentProcessName(this);
        if(Build.VERSION.SDK_INT >= 28) {
            try {
                WebView.setDataDirectorySuffix(pn);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if(appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    private void initMetaData() {
    }

    @JavascriptInterface
    public static String callJava(String method, String strJson) {
        if(NativeJsCorrespondent.getInstance().hasMethod(method)) {
            return NativeJsCorrespondent.getInstance().callNative(method, strJson);
        } else {
            return NativeJavaClass.callJava(method, strJson);
        }
    }


}
