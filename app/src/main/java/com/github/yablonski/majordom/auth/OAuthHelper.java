package com.github.yablonski.majordom.auth;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.github.yablonski.majordom.Api;
import com.github.yablonski.majordom.CoreApplication;
import com.github.yablonski.majordom.LoginActivity;
import com.github.yablonski.majordom.StartActivity;
import com.github.yablonski.majordom.processing.StringProcessor;

import org.apache.http.auth.AuthenticationException;

/**
 * Created by Acer on 22.11.2014.
 */
public class OAuthHelper {

    //public static final String KEY = "OAuthHelper";

    public static interface Callbacks {
        void onError(Exception e);
        void onSuccess();
    }

    //public static OAuthHelper get(Context context) {
    //    return CoreApplication.get(context, KEY);
    //}

    private static String sToken;
    //private static String token;
    //public static Context mContext;
    public static final String REDIRECT_URL = "http://melvillestrada.com/blank.html";
    public static final String AUTHORIZATION_URL = "http://melvillestrada.com/lib/userAndroid.php";
    private static final String TAG = OAuthHelper.class.getSimpleName();
    public static SharedPreferences sharedPreferences;
    public static final String PREF_FILE_NAME = "PrefFile";

    //static Context mContext;
    //Context context = getActivity(activity);


    public static String sign(String url) {

        //sToken = PreferenceManager.METADATA_KEY_PREFERENCES;
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.);
        sToken = sharedPreferences.getString(PREF_FILE_NAME,"");
        Log.d(TAG, "token " + sToken);
        if (url.contains("?")) {
            return url + "&access_token="+sToken;
        } else {
            return url + "?access_token="+sToken;
        }
    }

    public static boolean isLogged(Context mContext) {
        //SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.class);
        //SharedPreferences sharedPreferences1 = getSharedPreferences(LoginActivity.class);
        //sToken = spref.getString(PREF_FILE_NAME,"");
        sToken = PreferenceManager.METADATA_KEY_PREFERENCES;
        return !TextUtils.isEmpty(sToken);
    }

    public static boolean proceedRedirectURL(Activity activity, String url, Callbacks callbacks) {
        if (url.startsWith(REDIRECT_URL)) {
            Uri uri = Uri.parse(url); //Creates a Uri from a file - https://oauth.vk.com/blank.html -> https://oauth.vk.com/
            String fragment = uri.getFragment(); //Gets the encoded fragment part of this URI, everything after the '#'
            Uri parsedFragment = Uri.parse("http://temp.com?" + fragment);
            String accessToken = parsedFragment.getQueryParameter("access_token"); //Searches the query string for the first value with the given key and interprets it
            // as a boolean value.
            if (!TextUtils.isEmpty(accessToken)) { //if accessToken is not empty
                Log.d(TAG, "token " + accessToken);
                //TODO save token to the secure store
                //sharedPreferences = getSharedPreferences(mContext);
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PREF_FILE_NAME, accessToken);
                editor.commit();
                callbacks.onSuccess();
                //TODO create account in account manager
                return true;
            } else {
                //TODO check access denied/finish
            }
        }
        return false;
    }
}
