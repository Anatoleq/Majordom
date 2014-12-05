package com.github.yablonski.majordom.source;

import android.content.Context;

import com.github.yablonski.majordom.CoreApplication;

/**
 * Created by Acer on 03.12.2014.
 */
public class SharedPreferencesDataSource {
    public static final String KEY = "HttpDataSource";

    public static HttpDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }
}
