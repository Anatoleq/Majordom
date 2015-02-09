package com.github.yablonski.majordom.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.yablonski.majordom.BookMenuActivity;
import com.github.yablonski.majordom.R;
import com.github.yablonski.majordom.adapters.BookAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 08.02.2015.
 */
public class BookFragment extends Fragment {

    private String[] mBookMenuArray;
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

    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        return fragment;
    }

    public BookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_book, container, false);
        mListView = (ListView) view.findViewById(R.id.mListView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

        mAdapter = new BookAdapter(getActivity(), mBookMenuArray, mDrawables);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BookMenu menuItem = BookMenu.values()[position];
                switch (menuItem) {
                    case ELECTRICIAN:
                        startBookMenu(R.string.electric_type);
                        break;
                    case PLUMBER:
                        startBookMenu(R.string.plumber_type);
                        break;
                    case ELEVATOR:
                        startBookMenu(R.string.elevator_type);
                        break;
                    case PARKING:
                        startBookMenu(R.string.parking_type);
                        break;
                    case MOVING:
                        startBookMenu(R.string.moving_type);
                        break;
                }
            }
        });
    }

    private void startBookMenu(int type) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), BookMenuActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

}
