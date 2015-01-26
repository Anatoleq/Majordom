package com.github.yablonski.majordom.source;

import android.content.Context;

import com.github.yablonski.majordom.CoreApplication;

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
        String signUrl = p;
        return super.getResult(signUrl);
    }
}
