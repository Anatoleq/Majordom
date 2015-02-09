package com.github.yablonski.majordom.source;

import android.content.Context;
import android.util.Log;

import com.github.yablonski.majordom.CoreApplication;
import com.github.yablonski.majordom.auth.OAuthHelper;

import java.io.InputStream;

/**
 * Created by Acer on 25.11.2014.
 */
public class UserDataSource extends HttpDataSource {

    public static final String KEY = "UserDataSource";

    public static UserDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        String signUrl = OAuthHelper.signUrl(p);
        Log.d("UDS", signUrl);
        return super.getResult(signUrl);
    }
}
