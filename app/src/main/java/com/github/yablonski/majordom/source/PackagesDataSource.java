package com.github.yablonski.majordom.source;

import android.content.Context;

import com.github.yablonski.majordom.CoreApplication;
import com.github.yablonski.majordom.bo.Packages;

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
        //String signUrl = OAuthHelper.sign(p);
        String signUrl = p;
        //String versionValue = Uri.parse(signUrl).getQueryParameter(Api.VERSION_PARAM);
        //if (TextUtils.isEmpty(versionValue)) {
        //    signUrl = signUrl + "&" + Api.VERSION_PARAM + "=" + Api.VERSION_VALUE;
        //}
        return super.getResult(signUrl);
    }

}
