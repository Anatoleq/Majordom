package com.github.yablonski.majordom;

/**
 * Created by Acer on 09.11.2014.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    public static DetailsFragment newInstance(int pos) {
        DetailsFragment details = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        details.setArguments(args);
        return details;
    }

    int getPosition() {
        return getArguments().getInt("position", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.details, container, false);
        TextView textView = (TextView) v.findViewById(R.id.textTextView);
        textView.setText(getResources().getStringArray(R.array.content)[getPosition()]);
        return v;
    }
}