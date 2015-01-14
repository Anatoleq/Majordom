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


    public static interface Callbacks {
        void onError(Exception e);
        void onSuccess(String token);
    }

    public static Context mContext;
    public static final String TOKEN = "token";
    public static final String REDIRECT_URL = "http://melvillestrada.com/blank.html";
    public static final String AUTHORIZATION_URL = "http://melvillestrada.com/lib/userAndroid.php";
    public static final String TAG = OAuthHelper.class.getSimpleName();


    public static boolean proceedRedirectURL(Activity activity, String url, Callbacks callbacks) {
        if (url.startsWith(REDIRECT_URL)) {
            Uri uri = Uri.parse(url); //Creates a Uri from a file - https://oauth.vk.com/blank.html -> https://oauth.vk.com/
            String fragment = uri.getFragment(); //Gets the encoded fragment part of this URI, everything after the '#'
            Uri parsedFragment = Uri.parse("http://temp.com?" + fragment);
            String accessToken = parsedFragment.getQueryParameter("access_token"); //Searches the query string for the first value with the given key and interprets it
            // as a boolean value.
            if (!TextUtils.isEmpty(accessToken)) {
                Log.d(TAG, "token " + accessToken);
                callbacks.onSuccess(accessToken);
                return true;
            } else {
                String error = parsedFragment.getQueryParameter("error");
                String errorDescription = parsedFragment.getQueryParameter("error_description");
                String errorReason = parsedFragment.getQueryParameter("error_reason");
                if (!TextUtils.isEmpty(error)) {
                    callbacks.onError(new AuthenticationException(error+", reason : " + errorReason +"("+errorDescription+")"));
                    return false;
                } else {
                }
            }
        }
        return false;
    }
}
