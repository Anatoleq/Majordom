package com.github.yablonski.majordom.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.yablonski.majordom.R;

/**
 * Created by Acer on 19.01.2015.
 */
public class MainMenuAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] menu;

    public MainMenuAdapter(Activity context, String[] menu) {
        super(context, R.layout.adapter_menu_item, menu);
        this.context = context;
        this.menu = menu;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_menu_item, null, true);
        TextView textView1 = (TextView) rowView.findViewById(R.id.menuItemTextView);
        textView1.setText(menu[position]);
        return rowView;
    }

    ;
}
