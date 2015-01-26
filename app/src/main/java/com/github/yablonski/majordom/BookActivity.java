package com.github.yablonski.majordom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookActivity extends ActionBarActivity {

    private String[] bookmenu, bookMenuArray;
    public static String sBookKey;
    private int[] images = { R.drawable.bulb3, R.drawable.poor, R.drawable.elevator,
            R.drawable.parking, R.drawable.moving};

    public enum BookMenu{
        ELECTRICIAN(R.string.book_menu_electrician, R.drawable.bulb3),
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
        setContentView(R.layout.activity_book);

        final List<String> bookMenuList = new ArrayList<String>();
        for ( final BookMenu items : BookMenu.values() ) {
            bookMenuList.add( getString( items.getLabel() ) );
        }

        bookMenuArray = new String[bookMenuList.size()];
        bookMenuArray = bookMenuList.toArray(bookMenuArray);

        ListView mListView = (ListView) findViewById(R.id.mListView);
        bookmenu = getResources().getStringArray(R.array.booking);
        AdapterBook adapter = new AdapterBook(this, bookMenuArray, images);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                switch (position) {
                    case 0:
                        startElectrician();
                        break;
                    case 1:
                        startPlumber();
                        break;
                    case 2:
                        startElevator();
                        break;
                    case 3:
                        startParking();
                        break;
                    case 4:
                        startMoving();
                        break;
                }
            }
        });
    }
    private void startElectrician(){
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(sBookKey, R.string.book_menu_electrician);
        startActivity(intent);
    }

    private void startPlumber(){
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(sBookKey, R.string.book_menu_plumber);
        startActivity(intent);
    }

    private void startElevator(){
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(sBookKey, R.string.book_menu_elevator);
        startActivity(intent);
    }

    private void startParking(){
        Intent intent = new Intent(BookActivity.this, BookParkingActivity.class);
        intent.putExtra(sBookKey, R.string.book_menu_parking);
        startActivity(intent);
    }

    private void startMoving(){
        Intent intent = new Intent(BookActivity.this, BookServiceActivity.class);
        intent.putExtra(sBookKey, R.string.book_menu_moving);
        startActivity(intent);
    }
}
