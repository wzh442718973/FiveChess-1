package org.game.zalo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.zing.zalo.zalosdk.oauth.FeedData;
import com.zing.zalo.zalosdk.oauth.OpenAPIService;
import com.zing.zalo.zalosdk.oauth.ZaloPluginCallback;
import com.zing.zalo.zalosdk.oauth.ZaloSDKApplication;

import org.game.base.INativeMethod;
import org.game.base.ISDKModule;
import org.game.base.NativeJsCorrespondent;
import org.json.JSONObject;

/**
 * @author gaoyu
 * @date 2021/11/1
 */
public class ZaloModule implements ISDKModule {

    private Handler handler;

    @Override
    public void init(Context context) {
        handler = new Handler();

        ZaloSDKApplication.wrap((Application) context.getApplicationContext());

        NativeJsCorrespondent.getInstance().registerNativeMethod("zaloShareCallback", new INativeMethod() {
            @Override
            public String invoke(String strJson) {
                if(strJson != null) {
                    try {
                        JSONObject json = new JSONObject(strJson);
                        final String link = json.optString("link", "");
                        final String msg = json.optString("msg", "");

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                FeedData feed = new FeedData();
                                feed.setMsg(msg);
                                feed.setLink(link);
                                OpenAPIService.getInstance().share(context, feed, new ZaloPluginCallback() {
                                    @Override
                                    public void onResult(boolean b, int i, String s, String s1) {

                                    }
                                });
                            }
                        });

                    } catch(Exception e1) {
                        e1.printStackTrace();
                    }
                }
                return null;
            }
        });
    }
}

