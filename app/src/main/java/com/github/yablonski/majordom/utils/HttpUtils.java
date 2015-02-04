package com.github.yablonski.majordom.utils;

import android.util.Log;

import com.github.yablonski.majordom.Api;
import com.github.yablonski.majordom.source.DataSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 01.02.2015.
 */
public class HttpUtils implements DataSource<InputStream, String> {

    List<NameValuePair> nameValuePair;
    InputStream is;

    public static String getEncodedRequest(String plainText) {
        try {
            return URLEncoder.encode(plainText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return plainText;
        }
    }
    @Override
    public InputStream getResult(String postData) throws Exception {

        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost(Api.REPORTS_GET);

        //Post Data
        /*List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
        nameValuePair.add(new BasicNameValuePair("type", "test_user"));
        nameValuePair.add(new BasicNameValuePair("message", "123456789"));
        nameValuePair.add(new BasicNameValuePair("token", "123456789"));*/

        //Encoding POST data
        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            // write response to log
            Log.d("Http Post Response:", response.toString());
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
        return is;

    }

    /*@Override
    public InputStream getResult(String p) throws Exception {
        URL url = new URL(p);
        Log.d(TAG, "url " + url);
        return url.openStream();
    }*/
}
