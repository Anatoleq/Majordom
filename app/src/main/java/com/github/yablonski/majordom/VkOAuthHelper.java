package com.github.yablonski.majordom;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.github.yablonski.majordom.VkLoginActivity;

/**
 * Created by IstiN on 29.10.2014.
 */
public class VkOAuthHelper {

    /*public static SharedPreferences getSharedPreferences (Context context) {
        return context.getSharedPreferences("FILE", 0);
    }*/

    private static String sToken;
    public static final String REDIRECT_URL = "http://melville.webatu.com/blank.html";
    public static final String AUTHORIZATION_URL = "http://melville.webatu.com/lib/userAndroid.php";
    private static final String TAG = VkOAuthHelper.class.getSimpleName();
    static SharedPreferences sharedPreferences;
    static final String SAVED_TEXT = "saved_text";
    public static final String PREF_FILE_NAME = "PrefFile";

    public static String sign(String url) {
        String stoken = sharedPreferences.getString(SAVED_TEXT, "");
        Log.d(TAG, "token " + stoken);
        //sToken = savedText;
        if (url.contains("?")) {
            return url + "&access_token="+sToken;
        } else {
            return url + "?access_token="+sToken;
        }
    }

    public static boolean isLogged() {
        return !TextUtils.isEmpty(sToken);
    }

    public static boolean proceedRedirectURL(Activity activity, String url) {
        if (url.startsWith(REDIRECT_URL)) {
            Uri uri = Uri.parse(url); //Creates a Uri from a file - https://oauth.vk.com/blank.html -> https://oauth.vk.com/
            String fragment = uri.getFragment(); //Gets the encoded fragment part of this URI, everything after the '#'
            Uri parsedFragment = Uri.parse("http://temp.com?" + fragment);
            String accessToken = parsedFragment.getQueryParameter("access_token"); //Searches the query string for the first value with the given key and interprets it
            // as a boolean value.
            if (!TextUtils.isEmpty(accessToken)) { //if accessToken is not empty
                Log.d(TAG, "token " + accessToken);
                //TODO save token to the secure store - done
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SAVED_TEXT, accessToken);
                editor.commit();
                //TODO create account in account manager
                return true;
            } else {
                //TODO check access denied/finish
                //#error=access_denied&error_reason=user_denied&error_description=User denied your request
            }
        }
        return false;
    }
}