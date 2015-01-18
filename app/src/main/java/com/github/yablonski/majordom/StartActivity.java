package com.github.yablonski.majordom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.github.yablonski.majordom.auth.EncryptManager;
import com.github.yablonski.majordom.auth.OAuthHelper;

/**
 * Created by Acer on 22.11.2014.
 */

public class StartActivity extends ActionBarActivity {

    public static final int REQUEST_LOGIN = 0;
    public static final String TAG = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
        } else {
            startMenuActivity();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
            if (data.hasExtra(OAuthHelper.TOKEN)) {
                String token = data.getStringExtra(OAuthHelper.TOKEN);
                saveToken(token);
                startMenuActivity();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private void startMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveToken(String token) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        if (TextUtils.isEmpty(token)) {
            prefEditor.remove(OAuthHelper.TOKEN);
        } else {
            try {
                token = EncryptManager.encrypt(this, token);
                prefEditor.putString(OAuthHelper.TOKEN, token);
            } catch (Exception e) {
                e.printStackTrace();
                prefEditor.remove(OAuthHelper.TOKEN);
            }
        }
        prefEditor.apply();
    }

    private String getToken() {
        String token = PreferenceManager.getDefaultSharedPreferences(this).getString(OAuthHelper.TOKEN, "");
        if (!TextUtils.isEmpty(token))
            try {
                token = EncryptManager.decrypt(this, token);
                OAuthHelper.sToken = token;
            } catch (Exception e) {
                e.printStackTrace();
                token = "";
            }
        return token;
    }
}
