package com.github.yablonski.majordom.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.yablonski.majordom.Api;
import com.github.yablonski.majordom.R;
import com.github.yablonski.majordom.bo.News;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.image.ImageLoader;
import com.github.yablonski.majordom.processing.NewsArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.NewsDataSource;

import java.util.List;

public class NewsFragment extends Fragment implements DataManager.Callback<List<News>> {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayAdapter mAdapter;
    private NewsArrayProcessor mNewsArrayProcessor = new NewsArrayProcessor();
    private AbsListView mListView;
    private TextView mError;
    private TextView mEmpty;
    private ProgressBar mProgressBar;
    private ImageLoader mImageLoader;
    private List<News> mData;
    private static final int KEY = 3;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news, container, false);
        mImageLoader = ImageLoader.get(getActivity().getApplicationContext());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        mEmpty = (TextView) view.findViewById(android.R.id.empty);
        mError = (TextView) view.findViewById(R.id.error);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final HttpDataSource dataSource = getHttpDataSource();
        final NewsArrayProcessor processor = getProcessor();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                update(dataSource, processor);
            }
        });
        update(dataSource, processor);
    }

    private NewsArrayProcessor getProcessor() {
        return mNewsArrayProcessor;
    }

    private HttpDataSource getHttpDataSource() {
        return new NewsDataSource();
    }

    private void update(HttpDataSource dataSource, NewsArrayProcessor processor) {
        DataManager.loadData(this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        return Api.NEWS_GET;
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
    public void onDone(List<News> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        dismissProgress();
        if (data == null || data.isEmpty()) {
            showEmpty();
        }

        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<News>(getActivity().getApplicationContext(), R.layout.adapter_item, android.R.id.text1, data) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(getActivity().getApplicationContext(), R.layout.adapter_item, null);
                    }
                    News item = getItem(position);
                    TextView newsDate = (TextView) convertView.findViewById(android.R.id.text1);
                    newsDate.setText(item.getDate());
                    TextView newsTitle = (TextView) convertView.findViewById(android.R.id.text2);
                    newsTitle.setText(item.getTitle());
                    convertView.setTag(item.getId());
                    final String url = item.getImage();
                    final ImageView imageView = (ImageView) convertView.findViewById(android.R.id.icon);
                    mImageLoader.loadAndDisplay(url, imageView);
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
        mProgressBar.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mError.setText(mError.getText() + "\n" + e.getMessage());
    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(KEY);
    }*/

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