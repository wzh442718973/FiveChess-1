package com.train.educate.ph;

import android.content.Context;

import com.train.educate.bs.ISDKModule;

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

