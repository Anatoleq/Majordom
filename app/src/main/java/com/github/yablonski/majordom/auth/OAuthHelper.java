package com.github.yablonski.majordom.auth;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;

import com.github.yablonski.majordom.Api;

import org.apache.http.auth.AuthenticationException;

/**
 * Created by Acer on 22.11.2014.
 */
public class OAuthHelper {

    public static interface Callbacks {
        void onError(Exception e);

        void onSuccess(String sAuthToken);
    }

    public static String sAuthToken;
    public static final String TOKEN = "token";

    public static boolean proceedRedirectURL(Activity activity, String url, Callbacks callbacks) {
        if (url.startsWith(Api.REDIRECT_URL)) {
            Uri uri = Uri.parse(url); //Creates a Uri from a file - https://oauth.vk.com/blank.html -> https://oauth.vk.com/
            String fragment = uri.getFragment(); //Gets the encoded fragment part of this URI, everything after the '#'
            Uri parsedFragment = Uri.parse("http://temp.com?" + fragment);
            String accessToken = parsedFragment.getQueryParameter("access_token"); //Searches the query string for the first value with the given key and interprets it
            // as a boolean value.
            if (!TextUtils.isEmpty(accessToken)) {
                callbacks.onSuccess(accessToken);
                return true;
            } else {
                String error = parsedFragment.getQueryParameter("error");
                String errorDescription = parsedFragment.getQueryParameter("error_description");
                String errorReason = parsedFragment.getQueryParameter("error_reason");
                if (!TextUtils.isEmpty(error)) {
                    callbacks.onError(new AuthenticationException(error + ", reason : " + errorReason + "(" + errorDescription + ")"));
                    return false;
                } else {
                }
            }
        }
        return false;
    }

    public static String signUrl(String url) {
        if (url.contains("?")) {
            return url + "&token=" + sAuthToken;
        } else {
            return url + "?token=" + sAuthToken;
        }
    }
}
