package com.github.yablonski.majordom;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookActivity extends ActionBarActivity {

    String[] bookmenu;
    ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        mListView = (ListView) findViewById(R.id.mListView);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.booking,
                android.R.layout.simple_list_item_1);
        mListView.setAdapter(adapter);
        bookmenu = getResources().getStringArray(R.array.booking);
    }

}
