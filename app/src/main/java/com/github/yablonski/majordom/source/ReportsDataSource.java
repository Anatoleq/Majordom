package com.github.yablonski.majordom.source;

import android.content.Context;

import com.github.yablonski.majordom.CoreApplication;
import com.github.yablonski.majordom.auth.OAuthHelper;

import java.io.InputStream;

/**
 * Created by Acer on 29.01.2015.
 */
public class ReportsDataSource extends HttpDataSource {

    public static final String KEY = "ReportsDataSource";

    public static ReportsDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        String signUrl = OAuthHelper.signUrl(p);
        return super.getResult(signUrl);
    }
}
