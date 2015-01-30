package com.github.yablonski.majordom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends ActionBarActivity {

    private ListView mListView;
    private static String[] sMenuArray;

    //TODO make uppercase
    public static String sKey;

    public enum Menu {
        MY_PROFILE(R.string.main_menu_profile),
        BOOKING(R.string.main_menu_booking),
        MY_PACKAGES(R.string.main_menu_packages),
        REPORTS(R.string.main_menu_reports),
        COMPLAINTS(R.string.main_menu_complaints),
        NEWS(R.string.main_menu_news);

        private int label;

        Menu(int label) {
            this.label = label;
        }

        public int getLabel() {
            return label;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final List<String> menuList = new ArrayList<String>();
        for (final Menu items : Menu.values()) {
            menuList.add(getString(items.getLabel()));
        }

        //TODO not static
        sMenuArray = new String[menuList.size()];
        sMenuArray = menuList.toArray(sMenuArray);

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setAdapter(new ArrayAdapter<>(this, R.layout.adapter_menu_item, R.id.menuItemTextView, sMenuArray));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Menu menuItem = Menu.values()[position];
                switch (menuItem) {
                    case BOOKING:
                        startBookActivity();
                        break;
                    case MY_PACKAGES:
                        startPackagesActivity();
                        break;
                    case REPORTS:
                        startReportsActivity();
                        break;
                    case COMPLAINTS:
                        startComplaintsActivity();
                        break;
                    case NEWS:
                        startNewsActivity();
                        break;
                    case MY_PROFILE:
                        startProfileActivity();
                        break;
                }
            }
        });
    }

    private void startBookActivity() {
        Intent intent = new Intent(MenuActivity.this, BookActivity.class);
        startActivity(intent);
    }

    private void startPackagesActivity() {
        Intent intent = new Intent(MenuActivity.this, PackagesActivity.class);
        startActivity(intent);
    }

    private void startReportsActivity() {
        Intent intent = new Intent(MenuActivity.this, ReportsActivity.class);
        intent.putExtra(sKey, R.string.main_menu_reports);
        startActivity(intent);
    }

    private void startComplaintsActivity() {
        Intent intent = new Intent(MenuActivity.this, ReportsActivity.class);
        intent.putExtra(sKey, R.string.main_menu_complaints);
        startActivity(intent);
    }

    private void startNewsActivity() {
        Intent intent = new Intent(MenuActivity.this, NewsActivity.class);
        startActivity(intent);
    }

    private void startProfileActivity() {
        Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}