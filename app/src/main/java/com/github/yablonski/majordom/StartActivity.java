package com.github.yablonski.majordom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.github.yablonski.majordom.auth.OAuthHelper;


public class StartActivity extends ActionBarActivity {

    public static final int REQUEST_LOGIN = 0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (OAuthHelper.isLogged(context)) {
            startMenuActivity();
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
            startMenuActivity();
        } else {
            finish();
        }
    }

    private void startMenuActivity() {
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}
