package com.github.yablonski.majordom;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.yablonski.majordom.bo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialActivity extends ActionBarActivity {

    public static String LOG_TAG = "my_log";
    TextView testTextView;
    Button startButton;
    //User p = new User();
    //public String firstName = "1. ";
    //Map<String, String> map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        new LongOperation() {
            /*User p = new User();
            String firstName = "1. ";*/
            @Override
            public void onPostExecute(String result) {
                super.onPostExecute(result);
                JSONObject dataJsonObj = null;
                String firstName = "";
                TextView testTextView = (TextView) findViewById(R.id.testTextView);

                try {
                    dataJsonObj = new JSONObject(result);
                    JSONArray response = dataJsonObj.getJSONArray("response");

                    // 1. достаем инфо о втором друге - индекс 1
                    JSONObject userInfo = response.getJSONObject(0);
                    firstName = userInfo.getString("first_name");
                    Log.d(LOG_TAG, "имя: " + firstName);
                    testTextView.setText("Привет, "+firstName);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            /*GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            User[] p = gson.fromJson(result, User[].class);
            //String firstName = p.getFirst_name();*/
                //TextView testTextView = (TextView) findViewById(R.id.testTextView);

            }
        }.execute("");
    }
}

class LongOperation extends AsyncTask<String, Void, String> {

    //User p = new User();

    @Override
    protected String doInBackground(String... params) {
        String str = "error";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("https://api.vk.com/method/users.get?user_id=197192020&fields=first_name");
            HttpResponse response = client.execute(request);
            HttpEntity resEntity = response.getEntity();
            //InputStream content = resEntity.getContent();
            if (resEntity != null) {
                str = EntityUtils.toString(resEntity);
                //Reader reader = new InputStreamReader(content);
                //GsonBuilder gsonBuilder = new GsonBuilder();
                //Gson gson = gsonBuilder.create();
                //User[] p = gson.fromJson(reader, User[].class);
                //String firstName = p.getFirst_name();
                //content.close();
            }
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (ClientProtocolException cpe) {
            cpe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return str;
    }

    @Override
    protected void onPostExecute(String result) {
        //Gson gson = new Gson();
        //Type listType = new TypeToken<List<String>>() {}.getType();
        //List<String> response = gson.fromJson(result, listType);*/
        //User p = gson.fromJson(result, User.class);
        //might want to change "executed" for the returned string passed into onPostExecute() but that is upto you
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
