package com.github.yablonski.majordom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by Acer on 23.12.2014.
 */
public class AdapterBook extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] bookmenu;
    private final int[] images;

    public AdapterBook(Activity context, String[] bookmenu, int[] images) {
        super(context, R.layout.adapter_book, bookmenu);

        this.context=context;
        this.bookmenu=bookmenu;
        this.images=images;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_book, null,true);

        TextView bookItemTextView = (TextView) rowView.findViewById(android.R.id.text1);
        bookItemTextView.setText(bookmenu[position]);
        ImageView imageView = (ImageView) rowView.findViewById(android.R.id.icon);
        imageView.setImageResource(images[position]);
        return rowView;
    };
}
