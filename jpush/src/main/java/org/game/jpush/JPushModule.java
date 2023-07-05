package org.game.jpush;

import android.content.Context;

import org.game.base.ISDKModule;

import cn.jpush.android.api.JPushInterface;

/**
 * @author gaoyu
 * @date 2021/11/1
 */
public class JPushModule implements ISDKModule {


    @Override
    public void init(Context context) {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context.getApplicationContext());
    }
}

