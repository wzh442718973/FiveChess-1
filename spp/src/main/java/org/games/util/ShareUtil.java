package org.games.util;


//insert class

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import org.games.app.MainActivity;

import java.io.File;

public class ShareUtil {

    //insert var

    /**
     * 分享文字
     *
     * @param packageName
     * @param content
     * @param title
     * @param subject
     */
    public static void shareText(String packageName, String className, String content, String title, String subject) {
        //insert code

        Context context = MainActivity.g_this;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //		if(null != className && null != packageName && !TextUtils.isEmpty(className) && !TextUtils.isEmpty(packageName)){
        //
        //		}else {
        //			if(null != packageName && !TextUtils.isEmpty(packageName)){
        //				intent.setPackage(packageName);
        //			}
        //		}
        if(stringCheck(className) && stringCheck(packageName)) {
            ComponentName componentName = new ComponentName(packageName, className);
            intent.setComponent(componentName);
        } else if(stringCheck(packageName)) {
            intent.setPackage(packageName);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);
        if(null != title && !TextUtils.isEmpty(title)) {
            intent.putExtra(Intent.EXTRA_TITLE, title);
        }
        if(null != subject && !TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        intent.putExtra(Intent.EXTRA_TITLE, title);
        Intent chooserIntent = Intent.createChooser(intent, "分享到：");
        context.startActivity(chooserIntent);
    }

    /**
     * 分享网页
     */
    public static void shareUrl(String packageName, String className, String content, String title, String subject) {
        //insert code

        Context context = MainActivity.g_this;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //		if(null != className && null != packageName && !TextUtils.isEmpty(className) && !TextUtils.isEmpty(packageName)){
        //
        //		}else {
        //			if(null != packageName && !TextUtils.isEmpty(packageName)){
        //				intent.setPackage(packageName);
        //			}
        //		}
        if(stringCheck(className) && stringCheck(packageName)) {
            ComponentName componentName = new ComponentName(packageName, className);
            intent.setComponent(componentName);
        } else if(stringCheck(packageName)) {
            intent.setPackage(packageName);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);
        if(null != title && !TextUtils.isEmpty(title)) {
            intent.putExtra(Intent.EXTRA_TITLE, title);
        }
        if(null != subject && !TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        intent.putExtra(Intent.EXTRA_TITLE, title);
        Intent chooserIntent = Intent.createChooser(intent, "分享到：");
        context.startActivity(chooserIntent);
    }

    /**
     * 分享图片
     */
    public static void shareImg(String packageName, String className, File file) {
        //insert code

        Context context = MainActivity.g_this;
        if(file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            if(stringCheck(packageName) && stringCheck(className)) {
                intent.setComponent(new ComponentName(packageName, className));
            } else if(stringCheck(packageName)) {
                intent.setPackage(packageName);
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent chooserIntent = Intent.createChooser(intent, "分享到:");
            context.startActivity(chooserIntent);
        } else {
            Toast.makeText(context, "文件不存在", 1000).show();
        }
    }

    public static void shareImg(String packageName, String className, int resId) {
        //insert code
        Context context = MainActivity.g_this;
        if(resId > 0) {
            Uri uri = AndroidUtil.getResUri(context, resId);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            if(stringCheck(packageName) && stringCheck(className)) {
                intent.setComponent(new ComponentName(packageName, className));
            } else if(stringCheck(packageName)) {
                intent.setPackage(packageName);
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent chooserIntent = Intent.createChooser(intent, "分享到:");
            context.startActivity(chooserIntent);
        } else {
            Toast.makeText(context, "文件不存在", 1000).show();
        }
    }

    /**
     * 分享音乐
     */
    public static void shareAudio(String packageName, String className, File file) {
        //insert code

        Context context = MainActivity.g_this;
        if(file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("audio/*");
            if(stringCheck(packageName) && stringCheck(className)) {
                intent.setComponent(new ComponentName(packageName, className));
            } else if(stringCheck(packageName)) {
                intent.setPackage(packageName);
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            Intent chooserIntent = Intent.createChooser(intent, "分享到:");
            context.startActivity(chooserIntent);
        } else {
            Toast.makeText(context, "文件不存在", 1000).show();
        }
    }

    /**
     * 分享视频
     */
    public static void shareVideo(String packageName, String className, File file) {
        setIntent("video/*", packageName, className, file);
    }

    public static void setIntent(String type, String packageName, String className, File file) {
        //insert code

        Context context = MainActivity.g_this;
        if(file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType(type);
            if(stringCheck(packageName) && stringCheck(className)) {
                intent.setComponent(new ComponentName(packageName, className));
            } else if(stringCheck(packageName)) {
                intent.setPackage(packageName);
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            Intent chooserIntent = Intent.createChooser(intent, "分享到:");
            context.startActivity(chooserIntent);
        } else {
            Toast.makeText(context, "文件不存在", 1000).show();
        }
    }

    /**
     * 分享多张图片和文字至朋友圈
     *
     * @param title
     * @param packageName
     * @param className
     * @param file        图片文件
     */
    public static void shareImgToWXCircle(String title, String packageName, String className, File file) {
        //insert code

        Context context = MainActivity.g_this;
        if(file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            ComponentName comp = new ComponentName(packageName, className);
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra("Kdescription", title);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 是否安装分享app
     *
     * @param packageName
     */
    public static boolean checkInstall(String packageName) {
        Context context = MainActivity.g_this;
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "请先安装应用app", 1500).show();
            return false;
        }
    }

    /**
     * 跳转官方安装网址
     */
    public static void toInstallWebView(String url) {
        //insert code

        Context context = MainActivity.g_this;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static boolean stringCheck(String str) {
        //insert code

        if(null != str && !TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }
}
