package com.github.yablonski.majordom.source;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.github.yablonski.majordom.Api;
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
        //String signUrl = OAuthHelper.sign(p);
        String signUrl = p;
        //String versionValue = Uri.parse(signUrl).getQueryParameter(Api.VERSION_PARAM);
        //if (TextUtils.isEmpty(versionValue)) {
        //    signUrl = signUrl + "&" + Api.VERSION_PARAM + "=" + Api.VERSION_VALUE;
        //}
        return super.getResult(signUrl);
    }

}
