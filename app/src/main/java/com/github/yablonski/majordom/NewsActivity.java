package com.github.yablonski.majordom;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.yablonski.majordom.bo.News;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.image.ImageLoader;
import com.github.yablonski.majordom.processing.NewsArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.NewsDataSource;

import java.util.List;

public class NewsActivity extends ActionBarActivity implements DataManager.Callback<List<News>> {

    private ArrayAdapter mAdapter;
    private NewsArrayProcessor mNewsArrayProcessor = new NewsArrayProcessor();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageLoader mImageLoader;
    private List<News> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mImageLoader = ImageLoader.get(NewsActivity.this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
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
        return NewsDataSource.get(NewsActivity.this);
    }

    private void update(HttpDataSource dataSource, NewsArrayProcessor processor) {
        DataManager.loadData(NewsActivity.this,
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
        AbsListView listView = (AbsListView) findViewById(android.R.id.list);

        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<News>(this, R.layout.adapter_item, android.R.id.text1, data) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(NewsActivity.this, R.layout.adapter_item, null);
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
        dismissProgress();
        dismissEmpty();
        TextView errorView = (TextView) findViewById(R.id.error);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(errorView.getText() + "\n" + e.getMessage());
    }

    private void dismissProgress() {
        findViewById(android.R.id.progress).setVisibility(View.GONE);
    }

    private void showProgress() {
        findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
    }

    private void showEmpty() {
        findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
    }

    private void dismissEmpty() {
        findViewById(android.R.id.empty).setVisibility(View.GONE);
    }

}
