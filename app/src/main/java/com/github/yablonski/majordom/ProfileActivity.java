package com.github.yablonski.majordom;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.yablonski.majordom.bo.User;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.processing.UserArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.UserDataSource;

import java.util.List;

public class ProfileActivity extends ActionBarActivity implements DataManager.Callback<List<User>> {

    private UserArrayProcessor mUserArrayProcessor = new UserArrayProcessor();
    private List<User> mData;
    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final HttpDataSource dataSource = getHttpDataSource();
        final UserArrayProcessor processor = getProcessor();
        update(dataSource, processor);
    }

    private UserArrayProcessor getProcessor() {
        return mUserArrayProcessor;
    }

    private HttpDataSource getHttpDataSource() {
        return UserDataSource.get(ProfileActivity.this);
    }

    private void update(HttpDataSource dataSource, UserArrayProcessor processor) {
        DataManager.loadData(ProfileActivity.this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        return Api.PROFILE_GET;
    }

    @Override
    public void onDataLoadStart() {
        findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
        findViewById(android.R.id.empty).setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(List<User> data) {

        findViewById(android.R.id.progress).setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
        }
        ListView profileListView = (ListView) findViewById(R.id.profileListView);
        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<User>(this, R.layout.adapter_profile, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(ProfileActivity.this, R.layout.adapter_profile, null);
                    }
                    User item = getItem(position);
                    TextView firstNameValueTextView = (TextView) convertView.findViewById(R.id.firstNameValueTextView);
                    firstNameValueTextView.setText(item.getFirstName());
                    TextView lastNameValueTextView = (TextView) convertView.findViewById(R.id.lastNameValueTextView);
                    lastNameValueTextView.setText(item.getLastName());
                    TextView emailValueTextView = (TextView) convertView.findViewById(R.id.emailValueTextView);
                    emailValueTextView.setText(item.getEmail());
                    TextView suiteNrValueTextView = (TextView) convertView.findViewById(R.id.suiteNrValueTextView);
                    suiteNrValueTextView.setText(item.getSuiteNr());
                    return convertView;
                }
            };

            profileListView.setAdapter(mAdapter);
        } else {
            mData.clear();
            mData.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
        findViewById(android.R.id.progress).setVisibility(View.GONE);
        findViewById(android.R.id.empty).setVisibility(View.GONE);
        TextView errorView = (TextView) findViewById(R.id.error);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(errorView.getText() + "\n" + e.getMessage());
    }

}
