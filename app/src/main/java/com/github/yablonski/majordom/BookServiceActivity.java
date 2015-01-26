package com.github.yablonski.majordom;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BookServiceActivity extends ActionBarActivity {

    private int id, titleId;
    private String sTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        titleId = intent.getIntExtra(BookActivity.sBookKey,id);
        sTitle = getString(titleId);
        setTitle(sTitle);
        setContentView(R.layout.activity_bookservice);
    }

}
