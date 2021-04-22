package com.sochat.activity.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Utility {
    public static String getCountryCodeByService() {
        String response = null;
        String reqUrl = "https://ipinfo.io/country";
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(Utility.class.getName(), "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(Utility.class.getName(), "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(Utility.class.getName(), "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(Utility.class.getName(), "Exception: " + e.getMessage());
        }
        return response;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            return sb.toString();
    }

    public static String getCountryCode(Activity activity){
        TelephonyManager tm = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        return countryCodeValue;

//        or

//        String locale = activity.getBaseContext().getResources().getConfiguration().locale.getCountry();
//        return locale;
    }


}
