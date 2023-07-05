# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\developSoftware\Android\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Proguard Cocos2d-x-lite for release
-keep public class org.cocos2dx.** { *; }
-dontwarn org.cocos2dx.**

# Proguard Apache HTTP for release
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**

# Proguard okhttp for release
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

-keep class okio.** { *; }
-dontwarn okio.**


# Proguard Android Webivew for release. you can comment if you are not using a webview
-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient

-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient

# keep anysdk for release. you can comment if you are not using anysdk
-keep public class com.anysdk.** { *; }

-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn com.anysdk.**

-dontwarn com.android.installreferrer


-keep class com.adjust.sdk.** { *; }
-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}
-keep public class com.android.installreferrer.** { *; }

-keep public class com.adjust.sdk.** { *; }

# 保持基本组件不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference


#-keep class com.android.vending.billing.**
#-keep class com.android.vending.billing.** { *; }

# Facebook
#-keep class com.facebook.** {*;}
#-keep interface com.facebook.** {*;}
#-keep enum com.facebook.** {*;}
#-keepnames class com.facebook.FacebookActivity
#-keepnames class com.facebook.CustomTabActivity
#
#-keepnames class com.android.installreferrer.api.InstallReferrerClient
#-keepnames class com.android.installreferrer.api.InstallReferrerStateListener
#-keepnames class com.android.installreferrer.api.ReferrerDetails
#
#-keep class com.facebook.core.Core
#
## keep class names and method names used by reflection by InAppPurchaseEventManager
#-keep public class com.android.vending.billing.IInAppBillingService {
#    public <methods>;
#}
#-keep public class com.android.vending.billing.IInAppBillingService$Stub {
#    public <methods>;
#}

#极光
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.service.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

-keepclassmembers class * {    @android.webkit.JavascriptInterface <methods>;}
-keepattributes JavascriptInterface
-keepattributes *Annotation*

-optimizations !method/inlining/*
-keepclasseswithmembers class * {  public void onPayment*(...);}

-dontwarn com.payumoney.**
-keep class com.payumoney.** { *; }

-dontwarn com.facebook.**
-keep class com.facebook.** { *; }

-dontwarn com.paytm.**
-keep class com.paytm.** { *; }

-dontwarn easypay.**
-keep class easypay.** { *; }

-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keep class com.zing.zalo.**{ *; }
-keep enum com.zing.zalo.**{ *; }
-keep interface com.zing.zalo.**{ *; }

-obfuscationdictionary dict.txt
-classobfuscationdictionary dict.txt
-packageobfuscationdictionary dict.txt

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}