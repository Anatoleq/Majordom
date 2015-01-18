package com.github.yablonski.majordom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends ActionBarActivity {

    final String LOG_TAG = "myLogs";

    private ListView listView;
    public String[] menu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.menu,
                android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        menu = getResources().getStringArray(R.array.menu);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
                //TODO create enum's
                switch (position) {
                    case 0:
                        Intent newActivity0 = new Intent(MenuActivity.this,BookActivity.class);
                        startActivity(newActivity0);
                        break;
                    case 1:
                        Intent intentPackages = new Intent(MenuActivity.this,PackagesActivity.class);
                        startActivity(intentPackages);
                        break;
                    case 2:
                        Intent intentReports = new Intent(MenuActivity.this,ReportsActivity.class);
                        startActivity(intentReports);
                        break;
                    case 3:
                        Intent intentComplaints = new Intent(MenuActivity.this,ComplaintsActivity.class);
                        startActivity(intentComplaints);
                        break;
                    case 4:
                        Intent newActivity4 = new Intent(MenuActivity.this,NewsActivity.class);
                        startActivity(newActivity4);
                        break;
                    case 5:
                        Intent intent = new Intent(MenuActivity.this, NewsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        // Nothing do!
                }
            }
        });
    }
}