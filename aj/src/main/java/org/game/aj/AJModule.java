package org.game.aj;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.game.base.INativeMethod;
import org.game.base.ISDKModule;
import org.game.base.NativeJsCorrespondent;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author gaoyu
 * @date 2021/10/26
 */
public class AJModule implements ISDKModule {

    private Handler handler;

    @Override
    public void init(Context context) {
        handler = new Handler();

        String adjust_preinstallKey = BuildConfig.adjust_preinstallKey;
        String adjust_key = BuildConfig.adjust_key;
        AdjustSDKHelp.getInstance().init((Application) context.getApplicationContext(), adjust_key, adjust_preinstallKey);

        NativeJsCorrespondent.getInstance().registerNativeMethod("adjustlogEvent", new INativeMethod() {
            @Override
            public String invoke(String strJson) {
                try {
                    JSONObject json = new JSONObject(strJson);
                    final String eventType = json.getString("typetoken");
                    final String jsonData = json.getString("tObjData");
                    final String CurrencyType = json.getString("tCurrencyType");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AdjustSDKHelp.getInstance().logEvent(eventType, jsonData, CurrencyType);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        NativeJsCorrespondent.getInstance().registerNativeMethod("getAdJustKey", new INativeMethod() {
            @Override
            public String invoke(String strJson) {
                return AdjustSDKHelp.getInstance().getAdJustKey();
            }
        });

        NativeJsCorrespondent.getInstance().registerNativeMethod("getAll_preinstallKey", new INativeMethod() {
            @Override
            public String invoke(String strJson) {
                return context.getString(R.string.all_preinstallKey);
            }
        });
    }
}
