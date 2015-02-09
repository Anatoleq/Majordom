package com.github.yablonski.majordom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

import com.github.yablonski.majordom.auth.EncryptManager;
import com.github.yablonski.majordom.auth.OAuthHelper;

/**
 * Created by Acer on 22.11.2014.
 */

public class StartActivity extends ActionBarActivity {

    public static final int REQUEST_LOGIN = 0;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToken = getToken();
        if (TextUtils.isEmpty(mToken)) {
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
        } else {
            startMainActivity();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
            if (data.hasExtra(OAuthHelper.TOKEN)) {
                mToken = data.getStringExtra(OAuthHelper.TOKEN);
                saveToken(mToken);
                startMainActivity();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
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
        mToken = PreferenceManager.getDefaultSharedPreferences(this).getString(OAuthHelper.TOKEN, "");
        if (!TextUtils.isEmpty(mToken))
            try {
                mToken = EncryptManager.decrypt(this, mToken);
                OAuthHelper.authToken = mToken;
            } catch (Exception e) {
                e.printStackTrace();
                mToken = "";
            }
        return mToken;
    }
}
