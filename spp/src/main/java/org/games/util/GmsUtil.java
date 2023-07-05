package org.games.util;

import android.content.Context;

//insert class

public class GmsUtil {

    //insert var

    private static final String TAG = "GmsUtil";
    private static final GmsUtil mGmsUtil = new GmsUtil();

    private GmsUtil() {
        //insert code

    }

    public static GmsUtil getInstance() {
        return mGmsUtil;
    }

    public boolean isGooglePlayServicesAvailable(Context context) {
        //insert code
//        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
//        if (googleApiAvailability != null) {
//            int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
//            return resultCode == ConnectionResult.SUCCESS;
//        }
        return false;
    }
}
