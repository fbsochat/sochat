package com.sochat.activity.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.sochat.activity.MyApplication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

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

    public static String getDate(long milliSeconds, String dateFormat) {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getRandomNumber(){
        Random rand = new Random();
        int num = rand.nextInt(9000000) + 1000000;
        String randomNumber = Integer.toString(num);
        return randomNumber;
    }

    public static void setCurrentUser(Activity activity,String userId) {

        SharedPreferences myPrefs = activity.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(Constants.USER_ID, userId);
        prefsEditor.apply();
    }

    public static void setCurrentUserName(String userName) {

        SharedPreferences myPrefs = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(Constants.USER_NAME, userName);
        prefsEditor.apply();
    }

    public static String getSharedPreferencesUserName(){
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString(Constants.USER_NAME, "");
        return userName;
    }

    public static String getSharedPreferencesUserId(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString(Constants.USER_ID, "");
        return userId;
    }

    public static String getSharedPreferencesUserId(){
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString(Constants.USER_ID, "");
        return userId;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void displayRoundImageFromUrl(Context mContext, String profileUrl, ImageView profileImage) {

    }

    public static void setCurrentUserGroupName(String groupName) {

        SharedPreferences myPrefs = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(Constants.GROUP_NAME, groupName);
        prefsEditor.apply();
    }

    public static String getSharedPreferencesUserGroupName(){
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String groupName = sharedPreferences.getString(Constants.GROUP_NAME, "");
        return groupName;
    }

    public static void setCurrentUserGroupWelcomeNote(String welcomeNote) {

        SharedPreferences myPrefs = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(Constants.WELCOME_NOTE, welcomeNote);
        prefsEditor.apply();
    }

    public static String getSharedPreferencesGroupWelcomeNote(){
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String groupName = sharedPreferences.getString(Constants.WELCOME_NOTE, "");
        return groupName;
    }

    public static void setCurrentUserGroupMembers(String members) {

        SharedPreferences myPrefs = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(Constants.MEMBERS, members);
        prefsEditor.apply();
    }

    public static String getSharedPreferencesGroupMembers(){
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String groupName = sharedPreferences.getString(Constants.MEMBERS, "");
        return groupName;
    }

}
