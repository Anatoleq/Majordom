package com.github.yablonski.majordom.source;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

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

    public static HttpDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(String p) throws Exception {
        URI uri = new URI(p);
        Uri parsedFragment = Uri.parse(p);
        String message = parsedFragment.getQueryParameter("message");
        if (!TextUtils.isEmpty(message)) {
            String params = uri.getQuery();
            String path = uri.getPath();
            String cleanUrl = Api.BASE_PATH + path;
            byte[] postData = params.getBytes(Charset.forName("UTF-8"));
            int postDataLength = postData.length;
            URL url = new URL(cleanUrl);
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
