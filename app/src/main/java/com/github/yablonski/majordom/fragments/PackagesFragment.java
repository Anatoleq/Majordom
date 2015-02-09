package com.github.yablonski.majordom.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.yablonski.majordom.Api;
import com.github.yablonski.majordom.R;
import com.github.yablonski.majordom.bo.Packages;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.processing.PackagesArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.PackagesDataSource;

import java.util.List;

/**
 * Created by Acer on 08.02.2015.
 */
public class PackagesFragment extends Fragment implements DataManager.Callback<List<Packages>> {

    private ArrayAdapter mAdapter;
    private PackagesArrayProcessor mPackagesArrayProcessor = new PackagesArrayProcessor();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Packages> mData;
    private View headerView;
    private ListView mListView;
    private TextView mError;
    private TextView mEmpty;
    private ProgressBar mProgressBar;

    public static PackagesFragment newInstance() {
        PackagesFragment fragment = new PackagesFragment();
        return fragment;
    }

    public PackagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_packages, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        mEmpty = (TextView) view.findViewById(android.R.id.empty);
        mError = (TextView) view.findViewById(R.id.error);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        return new PackagesDataSource();
    }

    private void update(HttpDataSource dataSource, PackagesArrayProcessor processor) {
        DataManager.loadData(this,
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
            showProgress();
        }
        dismissEmpty();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(List<Packages> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        dismissProgress();
        if (data == null || data.isEmpty()) {
            showEmpty();
        }

        headerView = View.inflate(getActivity().getApplicationContext(), R.layout.header_packages, null);
        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<Packages>(getActivity().getApplicationContext(), R.layout.adapter_packages_item, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(getActivity().getApplicationContext(), R.layout.adapter_packages_item, null);
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
            mListView.addHeaderView(headerView, null, false);
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
        mProgressBar.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
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
