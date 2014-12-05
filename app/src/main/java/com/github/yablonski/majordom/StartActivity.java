package com.github.yablonski.majordom;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.github.yablonski.majordom.utils.AuthUtils;


public class StartActivity extends ActionBarActivity {

    public static final int REQUEST_LOGIN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AuthUtils.isLogged()) {
            startNewsActivity();
        } else {
            startActivityForResult(new Intent(this, VkLoginActivity.class), REQUEST_LOGIN);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
            startNewsActivity();
        } else {
            finish();
        }
    }

    private void startNewsActivity() {
        startActivity(new Intent(this, NewsActivity.class));
        finish();
    }
}
