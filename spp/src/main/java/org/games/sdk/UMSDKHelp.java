package org.games.sdk;

import android.app.Application;

//import com.umeng.analytics.MobclickAgent;
//import com.umeng.commonsdk.UMConfigure;

//insert class

public class UMSDKHelp {

//insert var
    private static Application mAppContent;
    private static UMSDKHelp mInstace = null;

    public static UMSDKHelp getInstance() {
        if (null == mInstace) {
            mInstace = new UMSDKHelp();
        }
        return mInstace;
    }

    public void init(Application content, String key) {
        //insert code
        mAppContent = content;

        //设置LOG开关，默认为false
//        UMConfigure.setLogEnabled(false);
//        try {
//            Class<?> aClass = Class.forName("com.umeng.commonsdk.UMConfigure");
//            Field[] fs = aClass.getDeclaredFields();
//            for (Field f : fs) {
//                Log.e("xxxxxx", "ff=" + f.getName() + "   " + f.getType().getName());
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
//        UMConfigure.init(mAppContent, key, "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
//                null);
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }
}
