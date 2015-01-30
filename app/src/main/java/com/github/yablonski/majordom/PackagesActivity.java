package com.github.yablonski.majordom;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.yablonski.majordom.bo.Packages;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.processing.PackagesArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.PackagesDataSource;

import java.util.List;

public class PackagesActivity extends ActionBarActivity implements DataManager.Callback<List<Packages>> {

    private ArrayAdapter mAdapter;
    private PackagesArrayProcessor mPackagesArrayProcessor = new PackagesArrayProcessor();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Packages> mData;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        final HttpDataSource dataSource = getHttpDataSource();
        final PackagesArrayProcessor processor = getProcessor();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update(dataSource, processor);
            }
        });
        update(dataSource, processor);
    }

    private PackagesArrayProcessor getProcessor() {
        return mPackagesArrayProcessor;
    }

    private HttpDataSource getHttpDataSource() {
        return PackagesDataSource.get(PackagesActivity.this);
    }

    private void update(HttpDataSource dataSource, PackagesArrayProcessor processor) {
        DataManager.loadData(PackagesActivity.this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        return Api.PACKAGES_GET;
    }

    @Override
    public void onDataLoadStart() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
        }
        findViewById(android.R.id.empty).setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(List<Packages> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        findViewById(android.R.id.progress).setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
        }
        ListView listView = (ListView) findViewById(android.R.id.list);
        headerView = View.inflate(PackagesActivity.this, R.layout.header_packages, null);
        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<Packages>(this, R.layout.adapter_packages_item, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(PackagesActivity.this, R.layout.adapter_packages_item, null);
                    }
                    Packages item = getItem(position);
                    TextView senderTextView = (TextView) convertView.findViewById(R.id.senderTextView);
                    senderTextView.setText(item.getSender());
                    TextView incomeTextView = (TextView) convertView.findViewById(R.id.incomeTextView);
                    incomeTextView.setText(item.getIncomeDate());
                    TextView receivedTextView = (TextView) convertView.findViewById(R.id.receivedTextView);
                    receivedTextView.setText(item.getReceivedDate());
                    return convertView;
                }
            };
            listView.addHeaderView(headerView, null, false);
            listView.setAdapter(mAdapter);
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
