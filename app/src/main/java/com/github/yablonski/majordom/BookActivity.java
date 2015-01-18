package com.github.yablonski.majordom;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class BookActivity extends ListActivity {

    private String[] bookmenu;
    private final String LOG_TAG = "myLogs";

    int[] images = { R.drawable.bulb3, R.drawable.poor, R.drawable.elevator,
            R.drawable.parking, R.drawable.moving};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookmenu = getResources().getStringArray(R.array.booking);
        AdapterBook adapter = new AdapterBook(this, bookmenu, images);
        setListAdapter(adapter);
        ListView mListView = getListView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
                switch (position) {
                    case 0:
                        Intent newActivity0 = new Intent(BookActivity.this,ElectricActivity.class);
                        startActivity(newActivity0);
                        break;
                    case 1:
                        Intent newActivity1 = new Intent(BookActivity.this,NewsActivity.class);
                        startActivity(newActivity1);
                        break;
                    case 2:
                        Intent newActivity2 = new Intent(BookActivity.this,BookActivity.class);
                        startActivity(newActivity2);
                        break;
                    case 3:
                        Intent newActivity3 = new Intent(BookActivity.this,NewsActivity.class);
                        startActivity(newActivity3);
                        break;
                    case 4:
                        Intent intent = new Intent(BookActivity.this, NewsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        // Nothing do!
                }
            }
        });
    }
}
