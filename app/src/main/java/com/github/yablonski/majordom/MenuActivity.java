package com.github.yablonski.majordom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends Activity {

    final String LOG_TAG = "myLogs";

    ListView listView;
    String[] menu;

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
                switch (position) {
                    case 0:
                        Intent newActivity0 = new Intent(MenuActivity.this,BookActivity.class);
                        startActivity(newActivity0);
                        break;
                    case 1:
                        Intent newActivity1 = new Intent(MenuActivity.this,NewsActivity.class);
                        startActivity(newActivity1);
                        break;
                    case 2:
                        Intent newActivity2 = new Intent(MenuActivity.this,BookActivity.class);
                        startActivity(newActivity2);
                        break;
                    case 3:
                        Intent newActivity3 = new Intent(MenuActivity.this,NewsActivity.class);
                        startActivity(newActivity3);
                        break;
                    case 4:
                        Intent newActivity4 = new Intent(MenuActivity.this,NewsActivity.class);
                        startActivity(newActivity4);
                        break;
                    case 5:
                        Intent newActivity5 = new Intent(MenuActivity.this,NewsActivity.class);
                        startActivity(newActivity5);
                        break;
                    case 6:
                        Intent newActivity6 = new Intent(MenuActivity.this,NewsActivity.class);
                        startActivity(newActivity6);
                        break;
                    default:
                        // Nothing do!
                }
            }
        });
    }
}