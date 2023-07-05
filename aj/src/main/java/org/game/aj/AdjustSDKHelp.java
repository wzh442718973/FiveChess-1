package org.game.aj;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.OnAttributionChangedListener;

import org.json.JSONObject;


//insert class

public class AdjustSDKHelp {

    //insert var

    private static Application mAppContent;
    private static AdjustSDKHelp mInstace = null;

    public static int AdJustCount = 0;
    public static String AdjustData = "";

    public static AdjustSDKHelp getInstance() {
        if(null == mInstace) {
            mInstace = new AdjustSDKHelp();
        }
        return mInstace;
    }

    public void init(Application application, String adjust_key, String preInstall_key) {
        //insert code

        mAppContent = application;
        String environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(mAppContent, adjust_key, environment);
        config.setUrlStrategy(AdjustConfig.URL_STRATEGY_CHINA);
        mAppContent.registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution attribution) {
                setAdjustData(attribution);
            }
        });
        if(preInstall_key != "") {
            config.setDefaultTracker(preInstall_key);
        }
        Adjust.onCreate(config);
    }

    public void setAdjustData(AdjustAttribution attribution) {
        try {
            JSONObject mJsonobjData = new JSONObject();
            mJsonobjData.put("trackerName", attribution.trackerName);
            mJsonobjData.put("trackerToken", attribution.trackerToken);
            mJsonobjData.put("adid", attribution.adid);
            mJsonobjData.put("clickLabel", attribution.clickLabel);

            AdjustData = "" + mJsonobjData.toString();
        } catch(Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void logEvent(String eventType, String jsonData, String CurrencyType) {
        //insert code

        //String ajtoken = App.getAdJustKey();

        if(!eventType.equals("")) {
            AdjustEvent adjustEvent = new AdjustEvent(eventType);
            if(!CurrencyType.equals("")) {
                adjustEvent.setRevenue(Double.valueOf(jsonData), CurrencyType);
            }
            // adjustEvent.setOrderId("{OrderId}");
            Adjust.trackEvent(adjustEvent);
        }
    }

    public String getAdJustKey() {
        //insert code

        return AdjustData;
    }

    private static final class AdjustLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityResumed(Activity activity) {
            //insert code

            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //insert code

            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(Activity activity) {
            //insert code

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            //insert code

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            //insert code

        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            //insert code

        }

        @Override
        public void onActivityStarted(Activity activity) {
            //insert code

        }
        //...
    }
}
