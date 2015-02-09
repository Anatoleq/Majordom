package com.github.yablonski.majordom.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.yablonski.majordom.R;
import com.github.yablonski.majordom.adapters.BookAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 09.02.2015.
 */
public class BookMenuFragment extends Fragment {

    private String[] mBookMenuArray;
    public static String BOOKKEY;
    private int[] mDrawables;
    private List<String> mBookMenuList;
    private ListView mListView;
    private BookAdapter mAdapter;
    private int type;

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

    public interface onItemClickListener {
        public void itemClick(int type);
    }

    onItemClickListener listener;

    public static BookMenuFragment newInstance(int pos) {
        BookMenuFragment fragment = new BookMenuFragment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        fragment.setArguments(args);
        return fragment;
    }

    int getPosition() {
        return getArguments().getInt("position", 0);
    }

    public BookMenuFragment() {
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

                Log.d("Tag", Integer.toString(position));
                BookMenu menuItem = BookMenu.values()[position];
                switch (menuItem) {
                    case ELECTRICIAN:
                        type = R.string.electric_type;
                        break;
                    case PLUMBER:
                        type = R.string.plumber_type;
                        break;
                    case ELEVATOR:
                        type = R.string.elevator_type;
                        break;
                    case PARKING:
                        type = R.string.parking_type;
                        break;
                    case MOVING:
                        type = R.string.moving_type;
                        break;
                }
                listener.itemClick(type);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (onItemClickListener) activity;
    }

}
