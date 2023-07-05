package org.games.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import org.games.app.MainActivity;

import androidx.annotation.Nullable;

public class NetworkUtils {

    public static final int NETWORK_TYPE_UNAVAILABLE = -1;
    public static final int NETWORK_TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
    public static final int NETWORK_TYPE_WIFI = ConnectivityManager.TYPE_WIFI;

    /**
     * @return isNetworkConnected
     * @author ddj
     */
    public static boolean isNetworkConnected() {
        NetworkInfo networkInfo = null;
        try {
            ConnectivityManager connMgr = (ConnectivityManager) MainActivity.g_this.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connMgr.getActiveNetworkInfo();
        } catch(Exception e) {
            Log.e("NetworkUtils", "isNetworkConnected: " + e.getLocalizedMessage());
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    public static int getNetworkType(Context connection) {
        ConnectivityManager cm = (ConnectivityManager) connection.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null) {
            return NETWORK_TYPE_UNAVAILABLE;
        }
        NetworkInfo netInfo = null;
        try {
            netInfo = cm.getActiveNetworkInfo();
        } catch(Exception e) {
            Log.e("NetworkUtils", "getNetworkType: " + e.getLocalizedMessage());
        }
        if(netInfo != null && netInfo.isAvailable()) {
            if(netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_TYPE_WIFI;
            } else if(netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_TYPE_MOBILE;
            } else {
                return NETWORK_TYPE_UNAVAILABLE;
            }
        }
        return NETWORK_TYPE_UNAVAILABLE;
    }

    public static boolean isWapNetwork() {
        return !TextUtils.isEmpty(getProxyHost());
    }

    @SuppressWarnings("deprecation")
    public static String getProxyHost() {
        return System.getProperty("http.proxyHost");
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo wifiNetworkInfo = getNetworkInfo(context, ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo != null && wifiNetworkInfo.isConnected();
    }

    public static boolean isMobileConnected(Context context) {
        NetworkInfo wifiNetworkInfo = getNetworkInfo(context, ConnectivityManager.TYPE_MOBILE);
        return wifiNetworkInfo != null && wifiNetworkInfo.isConnected();
    }

    /**
     * Unknown network class.
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks.
     */
    public static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks.
     */
    public static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks.
     */
    public static final int NETWORK_CLASS_4_G = 3;
    /**
     * Class of broadly defined "WIFI" networks.
     */
    public static final int NETWORK_CLASS_WIFI = 5;


    /**
     * Return general class of network type, such as "3G" or "4G". In cases
     * where classification is contentious, this method is conservative.
     */
    public static int getNetworkClassByType(int networkType) {
        switch(networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case 16:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case 17:
                return NETWORK_CLASS_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
            case 18:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }


    public static @Nullable
    NetworkInfo getNetworkInfo(Context context, int networkType) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        try {
            networkInfo = connectivityManager.getNetworkInfo(networkType);
        } catch(Exception e) {

        }
        return networkInfo;
    }
}
