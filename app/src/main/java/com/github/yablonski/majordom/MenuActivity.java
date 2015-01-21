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
    public static String[] menu;
    public static String key;
    private String title;

    private enum Menu {
        BOOKING(R.string.main_menu_booking),
        MY_PROFILE(R.string.main_menu_profile),
        MY_PACKAGES(R.string.main_menu_packages),
        REPORTS(R.string.main_menu_reports),
        COMPLAINTS(R.string.main_menu_complaints),
        LOST_N_FOUND(R.string.main_menu_lostnfound),
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
        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        menu = getResources().getStringArray(R.array.menu);
        MainMenuAdapter adapter = new MainMenuAdapter(this,menu);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //MenuItem menuItem = MenuItem.valueOf;
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //String name = (String) parent.getItemAtPosition(position);
                //Menu menuItem = Menu.valueOf(name);
               // Log.d(LOG_TAG, "enum = " + name);
                Menu menuItem = Menu.COMPLAINTS;
                switch (menuItem) {
                    case BOOKING:
                        Intent newActivity0 = new Intent(MenuActivity.this,BookActivity.class);
                        startActivity(newActivity0);
                        break;
                    case MY_PACKAGES:
                        Intent intentPackages = new Intent(MenuActivity.this,PackagesActivity.class);
                        startActivity(intentPackages);
                        break;
                    case REPORTS:
                        Intent intentReports = new Intent(MenuActivity.this,ReportsActivity.class);
                        intentReports.putExtra(key,R.string.main_menu_reports);
                        startActivity(intentReports);
                        break;
                    case COMPLAINTS:
                        Intent intentComplaints = new Intent(MenuActivity.this,ReportsActivity.class);
                        intentComplaints.putExtra(key,R.string.main_menu_complaints);
                        startActivity(intentComplaints);
                        break;
                    case LOST_N_FOUND:
                        Intent newActivity4 = new Intent(MenuActivity.this,NewsActivity.class);
                        startActivity(newActivity4);
                        break;
                    case NEWS:
                        Intent intent = new Intent(MenuActivity.this, NewsActivity.class);
                        startActivity(intent);
                        break;
                    case MY_PROFILE:
                        Intent intentProfile = new Intent(MenuActivity.this, NewsActivity.class);
                        startActivity(intentProfile);
                        break;
                }
                //TODO create enum's

                /*switch (name) {
                    case BOOKING:
                        Intent newActivity0 = new Intent(MenuActivity.this,BookActivity.class);
                        startActivity(newActivity0);
                        break;
                    case MY_PACKAGES:
                        Intent intentPackages = new Intent(MenuActivity.this,PackagesActivity.class);
                        startActivity(intentPackages);
                        break;
                    case REPORTS:
                        Intent intentReports = new Intent(MenuActivity.this,ReportsActivity.class);
                        startActivity(intentReports);
                        break;
                    case COMPLAINTS:
                        Intent intentComplaints = new Intent(MenuActivity.this,ComplaintsActivity.class);
                        startActivity(intentComplaints);
                        break;
                    case LOST_N_FOUND:
                        Intent newActivity4 = new Intent(MenuActivity.this,NewsActivity.class);
                        startActivity(newActivity4);
                        break;
                    case NEWS:
                        Intent intent = new Intent(MenuActivity.this, NewsActivity.class);
                        startActivity(intent);
                        break;
                }*/
            }
        });
    }
}