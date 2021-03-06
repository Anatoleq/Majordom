package com.github.yablonski.majordom;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.yablonski.majordom.adapters.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends ActionBarActivity {

    private String[] mBookMenuArray;
    public static String BOOKKEY;
    private int[] mDrawables;
    private List<String> mBookMenuList;
    private ListView mListView;
    private BookAdapter mAdapter;

    public enum BookMenu {
        ELECTRICIAN(R.string.book_menu_electrician, R.drawable.bulb),
        PLUMBER(R.string.book_menu_plumber, R.drawable.poor),
        ELEVATOR(R.string.book_menu_elevator, R.drawable.elevator),
        PARKING(R.string.book_menu_parking, R.drawable.parking),
        MOVING(R.string.book_menu_moving, R.drawable.moving);

        private int label;
        private int drawable;

        BookMenu(int label, int drawable) {
            this.label = label;
            this.drawable = drawable;
        }

        public int getLabel() {
            return label;
        }

        public int getDrawable() {
            return drawable;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.activity_book);
        int size = 0;
        mBookMenuList = new ArrayList<String>();
        for (final BookMenu items : BookMenu.values()) {
            mBookMenuList.add(getString(items.getLabel()));
            size++;
        }
        mDrawables = new int[size];
        int i = 0;
        for (final BookMenu items : BookMenu.values()) {
            mDrawables[i] = items.getDrawable();
            i++;
        }

        mBookMenuArray = new String[mBookMenuList.size()];
        mBookMenuArray = mBookMenuList.toArray(mBookMenuArray);

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new BookAdapter(this, mBookMenuArray, mDrawables);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BookMenu menuItem = BookMenu.values()[position];
                switch (menuItem) {
                    case ELECTRICIAN:
                        startElectrician();
                        break;
                    case PLUMBER:
                        startPlumber();
                        break;
                    case ELEVATOR:
                        startElevator();
                        break;
                    case PARKING:
                        startParking();
                        break;
                    case MOVING:
                        startMoving();
                        break;
                }
            }
        });
    }

    private void startElectrician() {
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(BOOKKEY, R.string.book_menu_electrician);
        startActivity(intent);
    }

    private void startPlumber() {
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(BOOKKEY, R.string.book_menu_plumber);
        startActivity(intent);
    }

    private void startElevator() {
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(BOOKKEY, R.string.book_menu_elevator);
        startActivity(intent);
    }

    private void startParking() {
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(BOOKKEY, R.string.book_menu_parking);
        startActivity(intent);
    }

    private void startMoving() {
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(BOOKKEY, R.string.book_menu_moving);
        startActivity(intent);
    }
}
