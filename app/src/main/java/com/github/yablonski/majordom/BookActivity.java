package com.github.yablonski.majordom;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookActivity extends ListActivity {

    private String[] bookmenu;

    //ListView mListView;
    int[] images = { R.drawable.bulb3, R.drawable.poor, R.drawable.elevator,
            R.drawable.parking, R.drawable.moving};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookmenu = getResources().getStringArray(R.array.booking);
        AdapterBook adapter = new AdapterBook(this, bookmenu, images);
        setListAdapter(adapter);
    }

}
