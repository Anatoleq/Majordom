package com.github.yablonski.majordom;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.github.yablonski.majordom.VkLoginActivity;

/**
 * Created by IstiN on 29.10.2014.
 */
public class VkOAuthHelper {

    /*protected Context context;

    public VkOAuthHelper(Context context){
        this.context = context.getApplicationContext();
    }*/

    public static SharedPreferences getSharedPreferences (Context context) {
        return context.getSharedPreferences("FILE", 0);
    }

    //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
    private static String sToken;
    public static final String REDIRECT_URL = "http://melville.webatu.com/blank.html";
    public static final String AUTHORIZATION_URL = "http://melville.webatu.com/lib/userAndroid.php";
    private static final String TAG = VkOAuthHelper.class.getSimpleName(); //Returns the simple name of a member or local class, or null otherwise.
    SharedPreferences sharedPreferences;
    //static final String SAVED_TEXT = "saved_text";
    //private Context mcontext;
    //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    //SharedPreferences prefs = context.getSharedPreferences(context,Context.MODE_PRIVATE);

    public static String sign(String url) {
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
        //https://oauth.vk.com/blank.html#
        //fragment: access_token=token&expires_in=0&user_id=308327
        //https://oauth.vk.com/blank.html#error=
        if (url.startsWith(REDIRECT_URL)) {
            Uri uri = Uri.parse(url); //Creates a Uri from a file - https://oauth.vk.com/blank.html -> https://oauth.vk.com/
            String fragment = uri.getFragment(); //Gets the encoded fragment part of this URI, everything after the '#'
            Uri parsedFragment = Uri.parse("http://temp.com?" + fragment);
            String accessToken = parsedFragment.getQueryParameter("access_token"); //Searches the query string for the first value with the given key and interprets it
            // as a boolean value.
            if (!TextUtils.isEmpty(accessToken)) { //if accessToken is not empty
                Log.d(TAG, "token " + accessToken);
                //https://api.vk.com/method/getProfiles?uid=197192020&access_token=dea5911e0b716edff10b4e4318db77749dd7cec7a839192056dd35e2c5cf1ab0b1fd83fbcc4d49c82cf8f
                //TODO save token to the secure store
                /*Create a subclass of Application, e.g. public class MyApp extends Application {...
                    Set the android:name attribute of your <application> tag in the AndroidManifest.xml
                    to point to your new class, e.g. android:name="MyApp" (so the class is recognized by Android)
                    In the onCreate() method of your app instance, save your context (e.g. this) to a static field
                    named app and create a static method that returns this field, e.g. getApp().
                    You then can use this method later to get a context of your application and therefore get your shared preferences.*/
                //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString(SAVED_TEXT, accessToken);
                //editor.commit();
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