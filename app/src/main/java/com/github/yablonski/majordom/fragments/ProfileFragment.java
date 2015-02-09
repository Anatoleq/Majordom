package com.github.yablonski.majordom.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.yablonski.majordom.Api;
import com.github.yablonski.majordom.R;
import com.github.yablonski.majordom.bo.User;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.processing.UserArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.UserDataSource;

import java.util.List;

/**
 * Created by Acer on 08.02.2015.
 */
public class ProfileFragment extends Fragment implements DataManager.Callback<List<User>> {

    private ArrayAdapter mAdapter;
    private UserArrayProcessor mUserArrayProcessor = new UserArrayProcessor();
    private ListView mListView;
    private TextView mError;
    private TextView mEmpty;
    private ProgressBar mProgressBar;
    private List<User> mData;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        mListView = (ListView) view.findViewById(R.id.profileListView);
        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        mEmpty = (TextView) view.findViewById(android.R.id.empty);
        mError = (TextView) view.findViewById(R.id.error);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final HttpDataSource dataSource = getHttpDataSource();
        final UserArrayProcessor processor = getProcessor();
        update(dataSource, processor);
    }

    private UserArrayProcessor getProcessor() {
        return mUserArrayProcessor;
    }

    private HttpDataSource getHttpDataSource() {
        return UserDataSource.get(getActivity().getApplicationContext());
    }

    private void update(HttpDataSource dataSource, UserArrayProcessor processor) {
        DataManager.loadData(this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        return Api.PROFILE_GET;
    }

    @Override
    public void onDataLoadStart() {
        showProgress();
        dismissEmpty();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(List<User> data) {

        dismissProgress();
        if (data == null || data.isEmpty()) {
            showEmpty();
        }
        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<User>(getActivity().getApplicationContext(), R.layout.adapter_profile, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(getActivity().getApplicationContext(), R.layout.adapter_profile, null);
                    }
                    User item = getItem(position);
                    TextView firstNameValueTextView = (TextView) convertView.findViewById(R.id.firstNameValueTextView);
                    firstNameValueTextView.setText(item.getFirstName());
                    firstNameValueTextView.getResources().getColor(R.color.primary_text);
                    TextView lastNameValueTextView = (TextView) convertView.findViewById(R.id.lastNameValueTextView);
                    lastNameValueTextView.setText(item.getLastName());
                    firstNameValueTextView.getResources().getColor(R.color.primary_text);
                    TextView emailValueTextView = (TextView) convertView.findViewById(R.id.emailValueTextView);
                    emailValueTextView.setText(item.getEmail());
                    firstNameValueTextView.getResources().getColor(R.color.primary_text);
                    TextView suiteNrValueTextView = (TextView) convertView.findViewById(R.id.suiteNrValueTextView);
                    suiteNrValueTextView.setText(item.getSuiteNr());
                    firstNameValueTextView.getResources().getColor(R.color.primary_text);
                    return convertView;
                }
            };

            mListView.setAdapter(mAdapter);
        } else {
            mData.clear();
            mData.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
        dismissProgress();
        dismissEmpty();
        mError.setVisibility(View.VISIBLE);
        mError.setText(mError.getText() + "\n" + e.getMessage());
    }

    private void dismissProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showEmpty() {
        mEmpty.setVisibility(View.VISIBLE);
    }

    private void dismissEmpty() {
        mEmpty.setVisibility(View.GONE);
    }
}
