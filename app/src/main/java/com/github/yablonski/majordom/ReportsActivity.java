package com.github.yablonski.majordom;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ReportsActivity extends ActionBarActivity {

    public static final  String TAG = "my_TAG";
    private int id, titleId;
    private String title, message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        titleId = intent.getIntExtra(MenuActivity.sKey,id);
        title = getString(titleId);
        setTitle(title);
        setContentView(R.layout.activity_reports);
        switch (titleId){
            case R.string.main_menu_reports:
                message = "Please write your report here.\nWe will solve it as soon as possible.";
                break;
            case R.string.main_menu_complaints:
                message = "Please write your complaint here.\nWe will solve it as soon as possible.";;
                break;
        }
        TextView titleReportTextView = (TextView) findViewById(R.id.titleReportTextView);
        titleReportTextView.setText(message);
    }

}
