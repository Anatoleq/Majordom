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

import com.github.yablonski.majordom.auth.OAuthHelper;
import com.github.yablonski.majordom.bo.News;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.image.ImageLoader;
import com.github.yablonski.majordom.processing.NewsArrayProcessor;
import com.github.yablonski.majordom.source.CachedHttpDataSource;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.NewsDataSource;
import com.github.yablonski.majordom.source.UserDataSource;

import java.util.List;


public class NewsActivity extends ActionBarActivity implements DataManager.Callback<List<News>>{

    private ArrayAdapter mAdapter;
    //private static final String TAG = OAuthHelper.class.getSimpleName();
    private NewsArrayProcessor mNewsArrayProcessor = new NewsArrayProcessor();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageLoader mImageLoader;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        if (getIntent().hasExtra(OAuthHelper.TOKEN)) {
            token = getIntent().getStringExtra(OAuthHelper.TOKEN);
        }
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
        if (Api.NEWS_GET.contains("?")) {
            return Api.NEWS_GET + "&access_token="+token;
        } else {
            return Api.NEWS_GET + "?access_token="+token;
        }
    }

    @Override
    public void onDataLoadStart() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
        }
        findViewById(android.R.id.empty).setVisibility(View.GONE);
    }

    private List<News> mData;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(List<News> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        findViewById(android.R.id.progress).setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
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
                    TextView textView1 = (TextView) convertView.findViewById(android.R.id.text1);
                    textView1.setText(item.getDate());
                    TextView textView2 = (TextView) convertView.findViewById(android.R.id.text2);
                    textView2.setText(item.getTitle());
                    convertView.setTag(item.getId());
                    final String url = item.getImage();

                        //TODO add delay and cancel old request or create limited queue
                        //TODO create sync Map to check existing request and existing callbacks
                        //TODO create separate thread pool for manager

                    final ImageView imageView = (ImageView) convertView.findViewById(android.R.id.icon);
                    mImageLoader.loadAndDisplay(url, imageView);
                    return convertView;
                }

            };
            listView.setAdapter(mAdapter);
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            mImageLoader.resume();
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                            mImageLoader.pause();
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                            mImageLoader.pause();
                            break;
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
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
