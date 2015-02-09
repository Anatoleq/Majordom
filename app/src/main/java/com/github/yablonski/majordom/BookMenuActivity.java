package com.github.yablonski.majordom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.github.yablonski.majordom.fragments.BookMenuFragment;
import com.github.yablonski.majordom.fragments.BookServiceFragment;

/**
 * Created by Acer on 08.02.2015.
 */
public class BookMenuActivity extends FragmentActivity implements BookMenuFragment.onItemClickListener {

    private int position = 0;
    public int mId;
    private int mType;
    private boolean withDetails = true;
    public static String BOOKKEY;

    /*public enum BookMenu {
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
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 0);
        setContentView(R.layout.activity_book_menu);
        if (savedInstanceState != null)
            mType = savedInstanceState.getInt("type");
        withDetails = (findViewById(R.id.cont) != null);
        if (withDetails)
            showDetails(mType);
    }

    void showDetails(int type) {
        if (withDetails) {
            Log.d("type", Integer.toString(type));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.cont, BookServiceFragment.newInstance(type))
                    .commit();
        } else {
            startBookServiceActivity(type);
        }
    }

    @Override
    public void itemClick(int type) {
        this.mType = type;
        showDetails(type);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    private void startBookServiceActivity(int type) {
        Intent intent = new Intent(BookMenuActivity.this, BookServiceActivity.class);
        intent.putExtra(BOOKKEY, type);
        startActivity(intent);
    }

}
