package com.github.yablonski.majordom.source;

import android.content.Context;

import com.github.yablonski.majordom.CoreApplication;
import com.github.yablonski.majordom.auth.OAuthHelper;

import java.io.InputStream;

/**
 * Created by Acer on 05.12.2014.
 */
public class NewsDataSource extends HttpDataSource {

    public static final String KEY = "NewsDataSource";

    public static NewsDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        String signUrl = OAuthHelper.signUrl(p);
        return super.getResult(signUrl);
    }

}
