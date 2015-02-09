package com.github.yablonski.majordom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.github.yablonski.majordom.fragments.BookFragment;
import com.github.yablonski.majordom.fragments.MenuFragment;
import com.github.yablonski.majordom.fragments.NewsFragment;
import com.github.yablonski.majordom.fragments.PackagesFragment;
import com.github.yablonski.majordom.fragments.ProfileFragment;
import com.github.yablonski.majordom.fragments.ReportsFragment;

/**
 * Created by Acer on 06.02.2015.
 */

public class MainActivity extends FragmentActivity implements
        MenuFragment.onItemClickListener {

    private int position = 0;
    private boolean withDetails = true;
    public static String KEY;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null)
            position = savedInstanceState.getInt("position");
        withDetails = (findViewById(R.id.cont) != null);
        if (withDetails)
            showDetails(position);
    }

    void showDetails(int pos) {
        Menu menuItem = Menu.values()[pos];
        if (withDetails) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (menuItem) {
                case BOOKING:
                    fragmentManager.beginTransaction()
                            .replace(R.id.cont, BookFragment.newInstance())
                            .commit();
                    break;
                case MY_PACKAGES:
                    fragmentManager.beginTransaction()
                            .replace(R.id.cont, PackagesFragment.newInstance())
                            .commit();
                    break;
                case REPORTS:
                    fragmentManager.beginTransaction()
                            .replace(R.id.cont, ReportsFragment.newInstance(R.string.report_type))
                            .commit();
                    break;
                case COMPLAINTS:
                    fragmentManager.beginTransaction()
                            .replace(R.id.cont, ReportsFragment.newInstance(R.string.complaint_type))
                            .commit();
                    break;
                case NEWS:
                    fragmentManager.beginTransaction()
                            .replace(R.id.cont, NewsFragment.newInstance())
                            .commit();
                    break;
                case MY_PROFILE:
                    fragmentManager.beginTransaction()
                            .replace(R.id.cont, ProfileFragment.newInstance())
                            .commit();
                    break;
                default:
                    fragmentManager.beginTransaction()
                            .replace(R.id.cont, NewsFragment.newInstance())
                            .commit();
            }
        } else {
            Log.d("TAG", "pos" + position);
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
    }

    @Override
    public void itemClick(int position) {
        this.position = position;
        showDetails(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    private void startBookActivity() {
        Intent intent = new Intent(MainActivity.this, BookActivity.class);
        startActivity(intent);
    }

    private void startPackagesActivity() {
        Intent intent = new Intent(MainActivity.this, PackagesActivity.class);
        startActivity(intent);
    }

    private void startReportsActivity() {
        Intent intent = new Intent(MainActivity.this, ReportsActivity.class);
        intent.putExtra(KEY, R.string.main_menu_reports);
        startActivity(intent);
    }

    private void startComplaintsActivity() {
        Intent intent = new Intent(MainActivity.this, ReportsActivity.class);
        intent.putExtra(KEY, R.string.main_menu_complaints);
        startActivity(intent);
    }

    private void startNewsActivity() {
        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
        startActivity(intent);
    }

    private void startProfileActivity() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}