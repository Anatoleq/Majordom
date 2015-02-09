package com.github.yablonski.majordom.fragments;

/**
 * Created by Acer on 06.02.2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.yablonski.majordom.BookActivity;
import com.github.yablonski.majordom.NewsActivity;
import com.github.yablonski.majordom.PackagesActivity;
import com.github.yablonski.majordom.ProfileActivity;
import com.github.yablonski.majordom.R;
import com.github.yablonski.majordom.ReportsActivity;
import com.github.yablonski.majordom.adapters.MainMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private ListView mListView;
    private String[] menuArray;
    private ArrayAdapter<String> mAdapter;

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

    public interface onItemClickListener {
        public void itemClick(int position);
    }

    onItemClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final List<String> menuList = new ArrayList<String>();
        for (final Menu items : Menu.values()) {
            menuList.add(getString(items.getLabel()));
        }

        menuArray = new String[menuList.size()];
        menuArray = menuList.toArray(menuArray);
        mAdapter = new MainMenuAdapter(getActivity(), menuArray);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                listener.itemClick(position);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (onItemClickListener) activity;
    }

}