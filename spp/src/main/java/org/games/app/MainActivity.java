package org.games.app;

import static android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.game.BuildConfig;
import org.game.R;
import org.game.aj.AJModule;
import org.game.base.IJSMethod;
import org.game.base.NativeJsCorrespondent;
import org.game.fcm.FCMModule;
import org.game.jpush.JPushModule;
import org.game.zalo.ZaloModule;
import org.games.sdk.FacebookSDK;
import org.games.sdk.UploadHandler;
import org.games.util.AndroidUtil;
import org.games.util.NativeJavaClass;
import org.games.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

//insert class

public class MainActivity extends Activity {

    //insert var

    public static MainActivity g_this;

    private List<WebView> webViews = new ArrayList<>();
    private ViewGroup fl_webView;
    private WebView mWebView;
    private UploadHandler uploadHandler;
    public View mErrorPageView;

    public boolean isInternalWindow;

    public void setFullscreen() {
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        //专门设置一下状态栏导航栏背景颜色为透明，凸显效果。
        setNavigationStatusColor(Color.TRANSPARENT);

        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(attributes);
        }
    }

    public void setNavigationStatusColor(int color) {
        //VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(color);
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setFullscreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.hasAJ) {
            new AJModule().init(this);
        }
        if (BuildConfig.hasZalo) {
            new ZaloModule().init(this);
        }
        if (BuildConfig.hasFCM) {
            new FCMModule().init(this);
        }

        if (BuildConfig.hasJG) {
            new JPushModule().init(this);
        }

        setFullscreen();
        //insert code
        // Workaround in
        // https://stackoverflow.com/questions/16283079/re-launch-of-activity-on-home-button-but-only-the-first-time/16447508
//        if (!isTaskRoot()) {
//            // Android launched another instance of the root activity into an existing task
//            // so just quietly finish and go away, dropping the user back into the activity
//            // at the top of the stack (ie: the last state of this task)
//            // Don't need to finish it again since it's finished in super.onCreate .
//            return;
//        }
        g_this = this;

        // DO OTHER INITIALIZATION BELOW
        // SDKWrapper.getInstance().init(this);
//        Util.getInstance().init();

        // GVoice
        // GVoiceUtil.initEngine();

        // Scheme
        Intent intent = getIntent();
        String scheme = intent.getScheme();
        Uri uri = intent.getData();
        if (uri != null) {
            String id = uri.getQueryParameter("roomid");
            NativeJavaClass.setRoomID(id);
        }
        uploadHandler = new UploadHandler(this);
        FacebookSDK.getInstance().init(MainActivity.g_this);

        AndroidUtil.initAdid();
        //        PayUHelp.getInstance().init(MainActivity.g_this);
        //        GPHelp.getInstance().init(MainActivity.g_this);


        setContentView(R.layout.com_facebook_fragment_layout);
        AndroidBug5497Workaround.assistActivity(this);

        //        if("".equals(H5_URL) || H5_URL == null) {
        //            H5_URL = getString(R.string.h5_url);
        //        }

        mWebView = findViewById(R.id.web_view);
        mWebView.setBackgroundColor(0xff000000);
        fl_webView = findViewById(R.id.fl_webView);
        mErrorPageView = findViewById(R.id.error_page);
        initConfig(mWebView);
        //        webView.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                if(event.getAction() == MotionEvent.ACTION_DOWN) {
        //                    webView.evaluateJavascript("javascript:cc.mg.native_class.getJsBatteryLevelCallBack(1)", new ValueCallback<String>() {
        //                        @Override
        //                        public void onReceiveValue(String value) {
        //                            Log.i("NativeJavaClass", value);
        //                        }
        //                    });
        //                }
        //                return false;
        //            }
        //        });
        mWebView.setVisibility(View.GONE);

        UrlProvider.reqUrl(this, new CallBack() {
            @Override
            public void cb(String url) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String h5Url = url;
                        Log.i("h5Url", h5Url);
                        Uri h5Uri = Uri.parse(h5Url);
                        String orientation = h5Uri.getQueryParameter("orientation");
                        if ("h".equals(orientation)) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                        } else {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                        }
                        mWebView.setVisibility(View.VISIBLE);
                        loadUrl(h5Url);
                        //                        Intent intent = new Intent();
                        //                        intent.setAction("android.intent.action.VIEW");
                        //                        Uri content_url = Uri.parse(h5Url);
                        //                        intent.setData(content_url);
                        //                        startActivity(intent);
                        //                        finish();
                    }
                });
            }
        });

        NativeJsCorrespondent.getInstance().setJSMethod(new IJSMethod() {
            @Override
            public void invoke(String method, String strJson) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = String.format("javascript:cc.mg.native_class.callScript('%s','%s')", method, strJson);
                        mWebView.loadUrl(data);
                        //兼容h5
                        data = String.format("javascript:window.android.callScript('%s','%s')", method, strJson);
                        mWebView.loadUrl(data);
                    }
                });
            }
        });
    }

    private WebView addWebView() {
        WebView webView = new WebView(this);
        webView.setBackgroundColor(0xffffffff);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            CookieManager.getInstance().setAcceptCookie(true);
        }

        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        fl_webView.addView(webView, fp);
        webViews.add(webView);
        initConfig(webView);
        if (webViews.size() > 0) {
            NativeJavaClass.notifyEvent("visibilitychange", 0);
        }
        return webView;
    }

    private void initConfig(WebView webView) {
        webView.setInitialScale(0);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    if (url.endsWith(".apk")) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        startActivity(intent);
                    } else {
                        view.loadUrl(url);
                    }
                } else {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.ssl_error);
                builder.setPositiveButton(R.string.app_download_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sslErrorHandler.proceed();
                    }
                });
                builder.setNegativeButton(R.string.app_download_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sslErrorHandler.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                webView.stopLoading();
                webView.loadUrl("javascript:document.body.innerHtml=\" \"");
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message message) {
                WebView webView2;
                if (isInternalWindow) {
                    webView2 = addWebView();
                } else {
                    webView2 = new WebView(MainActivity.this);
                    webView2.getSettings().setJavaScriptEnabled(true);
                }
                ((WebView.WebViewTransport) message.obj).setWebView(webView2);
                message.sendToTarget();
                return true;
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return true;
            }

            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                removeWebView(webViews.indexOf(window));
            }

            public boolean onShowFileChooser(WebView mWebView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {

                return uploadHandler.onShowFileChooser(mWebView, filePathCallback, fileChooserParams);
            }
        });

        webView.addJavascriptInterface((App) getApplication(), "android");
        WebSettings mSettings = webView.getSettings();
        mSettings.setBuiltInZoomControls(false);
        mSettings.setSupportZoom(false);
        mSettings.setUseWideViewPort(true);
        mSettings.setJavaScriptEnabled(true);
        mSettings.setAllowContentAccess(true);
        mSettings.setAllowFileAccess(true);
        mSettings.setAllowFileAccessFromFileURLs(true);
        mSettings.setSupportMultipleWindows(true);
        mSettings.setLoadsImagesAutomatically(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager instance = CookieManager.getInstance();
            instance.setAcceptCookie(true);
            instance.setAcceptThirdPartyCookies(webView, true);
        }

        // mSettings.setSupportMultipleWindows(true);
        //mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mSettings.setAppCacheMaxSize(5 * 1048576);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mSettings.setBlockNetworkImage(false);

        mSettings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 12; zh-cn; 2109119BC Build/SKQ1.211006.001) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/89.0.4389.116 Mobile Safari/537.36 XiaoMi/MiuiBrowser/16.8.58 swan-mibrowser");

        // mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (Build.VERSION.SDK_INT >= 16) {
            mSettings.setAllowFileAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mSettings.setMediaPlaybackRequiresUserGesture(false);
        }
    }

    private void loadUrl(String url) {
        if (NetworkUtils.isNetworkConnected()) {
            //showDialog();
//            Map<String, String> map = new HashMap<>();
//            map.put("X-Requested-With", "");
            getCurrentWebView().loadUrl(url);
        } else {
            showErrorPage(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mErrorPageView.setVisibility(View.GONE);
                    getCurrentWebView().loadUrl(url);
                }
            });
        }
    }

    private WebView getCurrentWebView() {
        int childCount = fl_webView.getChildCount();
        if (childCount == 0) {
            return mWebView;
        } else {
            return (WebView) fl_webView.getChildAt(childCount - 1);
        }
    }

    protected void showErrorPage(View.OnClickListener errorPageClickLis) {
        if (!NetworkUtils.isNetworkConnected()) {
            ImageView errorIcon = (ImageView) mErrorPageView.findViewById(R.id.error_icon);
            errorIcon.setImageResource(R.drawable.empty_network_error);
            TextView errorText = (TextView) mErrorPageView.findViewById(R.id.error_message);
            errorText.setText(R.string.load_h5_failed);
            mErrorPageView.setOnClickListener(errorPageClickLis);
            mErrorPageView.setVisibility(View.VISIBLE);
        } else {
            ImageView errorIcon = (ImageView) mErrorPageView.findViewById(R.id.error_icon);
            errorIcon.setImageResource(R.drawable.empty_error_404);
            TextView errorText = (TextView) mErrorPageView.findViewById(R.id.error_message);
            errorText.setText(R.string.load_h5_404);
            mErrorPageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (WebView webView : webViews) {
            if (webView != null) {
                webView.onResume();
            }
        }
        if (mWebView != null) {
            mWebView.onResume();
        }
        // SDKWrapper.getInstance().onResume();
//        Util.getInstance().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (WebView webView : webViews) {
            if (webView != null) {
                webView.onPause();
            }
        }
        if (mWebView != null) {
            mWebView.onPause();
        }
        // SDKWrapper.getInstance().onPause();
//        Util.getInstance().onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FacebookSDK.getInstance().onActivityResult(MainActivity.g_this, requestCode, resultCode, data);
        uploadHandler.onActivityResult(requestCode, resultCode, data);
        // SDKWrapper.getInstance().onActivityResult(requestCode, resultCode, data);

        //        PayUHelp.getInstance().onActivityResult(requestCode, resultCode, data);

        //insert code

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // SDKWrapper.getInstance().onNewIntent(intent);

        //insert code

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //SDKWrapper.getInstance().onRestart();

        //insert code

    }

    @Override
    protected void onStop() {
        super.onStop();
        //SDKWrapper.getInstance().onStop();

        //insert code

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //SDKWrapper.getInstance().onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
        //insert code
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //SDKWrapper.getInstance().onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
        for (WebView webView : webViews) {
            webView.restoreState(savedInstanceState);
        }
        if (mWebView != null) {
            mWebView.restoreState(savedInstanceState);
        }
        //insert code

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //SDKWrapper.getInstance().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
        for (WebView webView : webViews) {
            webView.saveState(outState);
        }
        if (mWebView != null) {
            mWebView.saveState(outState);
        }
        //insert code

    }

    @Override
    protected void onStart() {
        //SDKWrapper.getInstance().onStart();
        super.onStart();

        //insert code

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (fl_webView != null) {
                int childCount = fl_webView.getChildCount();
                if (childCount != 0) {
                    WebView webView = getCurrentWebView();
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        removeWebView(childCount - 1);
                    }
                } else {
                    NativeJavaClass.onBackKey();
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void removeWebView(int index) {
        fl_webView.removeViewAt(index);
        WebView webView = webViews.remove(index);
        webView.destroy();
        if (webViews.size() == 0) {
            NativeJavaClass.notifyEvent("visibilitychange", 1);
        }
    }


//    private long backTime;

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (webView.canGoBack()) {
//                webView.goBack();
//            } else if (System.currentTimeMillis() - backTime > 2000) {
//                backTime = System.currentTimeMillis();
////                Toast.makeText(this, R.string.exit_tip, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode,event);
//    }

}
