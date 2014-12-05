package com.github.yablonski.majordom;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookActivity extends ActionBarActivity {

    String[] menu = new String[]{"Booking", "Reports", "Complaints", "Lost & found"};
    String[] bookmenu = new String[]{"Electrician", "Plumber", "Elevator", "Visitor's parking", "Move-in, Move-out service"};
    ArrayList<Map<String, String>> groupData;
    ArrayList<Map<String, String>> childDataItem;
    ArrayList<ArrayList<Map<String, String>>> childData;
    Map<String, String> m;
    ExpandableListView mExpandableListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        groupData = new ArrayList<Map<String, String>>();
        for (String group:menu){
            m = new HashMap<String, String>();
            m.put("groupName", group);
            groupData.add(m);
        }
        String groupFrom[] = new String[] {"groupName"};
        int groupTo[] = new int[] {android.R.id.text1};
        childData = new ArrayList<ArrayList<Map<String, String>>>();
        childDataItem = new ArrayList<Map<String, String>>();
        for (String expmenu : bookmenu){
            m = new HashMap<String, String>();
            m.put("menuName", expmenu);
            childDataItem.add(m);
        }
        childData.add(childDataItem);
        String childFrom[] = new String[]{"menuName"};
        int childTo[] = new int[]{android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this,
            groupData, R.layout.group_view, groupFrom, groupTo, childData,
            android.R.layout.simple_list_item_1, childFrom,childTo);

        mExpandableListView = (ExpandableListView) findViewById(R.id.mExpandableListView);
        mExpandableListView.setAdapter(adapter);

    }

    public void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;}

    /*public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }*/


}
