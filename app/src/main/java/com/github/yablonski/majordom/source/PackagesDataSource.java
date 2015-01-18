package com.github.yablonski.majordom.source;

import android.content.Context;
import android.util.Log;

import com.github.yablonski.majordom.CoreApplication;
import com.github.yablonski.majordom.auth.OAuthHelper;

import java.io.InputStream;

/**
 * Created by Acer on 12.01.2015.
 */
public class PackagesDataSource extends HttpDataSource {

    public static final String KEY = "PackagesDataSource";

    public static PackagesDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        String signUrl = OAuthHelper.signUrl(p);
        return super.getResult(signUrl);
    }

}
