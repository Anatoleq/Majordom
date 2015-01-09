package com.github.yablonski.majordom.source;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.github.yablonski.majordom.Api;
import com.github.yablonski.majordom.CoreApplication;
import com.github.yablonski.majordom.auth.OAuthHelper;

import java.io.InputStream;

/**
 * Created by Acer on 25.11.2014.
 */
public class UserDataSource extends HttpDataSource {

    private static final String TAG = UserDataSource.class.getSimpleName();

    public static final String KEY = "UserDataSource";

    public static UserDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        //Context context = getUserDataSource();
        String signUrl = p;
        /*String signUrl = OAuthHelper.sign(p);
        Log.d(TAG, "sign url " + signUrl);
        String versionValue = Uri.parse(signUrl).getQueryParameter(Api.VERSION_PARAM);
        if (TextUtils.isEmpty(versionValue)) {
            signUrl = signUrl + "&" + Api.VERSION_PARAM + "=" + Api.VERSION_VALUE;
        }*/
        return super.getResult(signUrl);
    }
}
