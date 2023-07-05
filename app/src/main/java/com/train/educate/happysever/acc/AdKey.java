package com.train.educate.happysever.acc;

import com.train.educate.happysever.R;

import java.security.cert.X509Certificate;

public abstract class AdKey {

    static final String HOST_NAME = "api.reptbc.xyz";
    static final String REF_URL = String.format("https://%s/v1/check/referrer/ANDROID", HOST_NAME);
//    static String REPORT_URL = "https://api.ysptcd.xyz/v2/data/report/ANDROID/APP_LAUNCHER";

    public static String TAG = AdKey.class.getSimpleName();
    static X509Certificate expectedCert;

    public static final boolean DBG_SELECT = false;
    public static final boolean DBG_LOG = false;

    public static String PKG_NAME = null;
    public static String PKG_FILE = null;
    public static int PKG_VER = 1;
    public static String INSTALL_FILE = "";
}
