package com.github.yablonski.majordom.source;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.github.yablonski.majordom.Api;
import com.github.yablonski.majordom.CoreApplication;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Acer on 25.11.2014.
 */
public class HttpDataSource implements DataSource<InputStream, String> {

    public static final String KEY = "HttpDataSource";
    public static final String TAG = "HttpDS";

    public static HttpDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        //Log.d(TAG, "url " + p);
        URI uri = new URI(p);
        Uri parsedFragment = Uri.parse(p);
        String message = parsedFragment.getQueryParameter("message");
        String params = uri.getQuery();
//        Log.d(TAG, "query " + params);
//        Log.d(TAG, "message " + message);
        if (!TextUtils.isEmpty(message)) {
//        Log.d(TAG, "url post " + uri);
            byte[] postData = params.getBytes(Charset.forName("UTF-8"));
            int postDataLength = postData.length;
            URL url = new URL(Api.REPORTS_GET);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            OutputStream openStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(openStream, "UTF-8"));
            writer.write(params);
            writer.flush();
            writer.close();
            openStream.close();
            connection.connect();
            Log.d(TAG, "url " + url);
            return connection.getInputStream();
        } else {
            URL url = new URL(p);
            return url.openStream();
        }
    }

    public static void close(Closeable in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
