package com.github.yablonski.majordom;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.github.yablonski.majordom.auth.OAuthHelper;
import com.github.yablonski.majordom.bo.Reports;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.processing.ReportsArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.ReportsDataSource;
import com.github.yablonski.majordom.utils.HttpUtils;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends ActionBarActivity implements DataManager.Callback<List<Reports>> {

    private int mTitleId;
    public int mId;
    private String mTitle, mMessage = "", mRequest, mType, mEncodedRequest;
    private ArrayAdapter mAdapter;
    private ReportsArrayProcessor mReportsArrayProcessor = new ReportsArrayProcessor();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Reports> mData;
    private List<NameValuePair> mNameValuePair;
    private String mPostParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mTitleId = intent.getIntExtra(MenuActivity.sKey, mId);
        mTitle = getString(mTitleId);
        setTitle(mTitle);
        setContentView(R.layout.activity_reports);
        TextView titleReportTextView = (TextView) findViewById(R.id.titleReportTextView);
        switch (mTitleId) {
            case R.string.main_menu_reports:
                mMessage = getString(R.string.report_message);
                mType = getString(R.string.report_type);
                break;
            case R.string.main_menu_complaints:
                mMessage = getString(R.string.complaint_message);
                mType = getString(R.string.complaint_type);
                break;
        }
        titleReportTextView.setText(mMessage);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        final HttpDataSource dataSource = getHttpDataSource();
        final ReportsArrayProcessor processor = getProcessor();
        mEncodedRequest = "";
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update(dataSource, processor);
            }
        });
        update(dataSource, processor);
    }

    private ReportsArrayProcessor getProcessor() {
        return mReportsArrayProcessor;
    }

    private HttpDataSource getHttpDataSource() {
        return ReportsDataSource.get(ReportsActivity.this);
    }

    private HttpDataSource getPostHttpDataSource() {
        return HttpDataSource.get(ReportsActivity.this);
    }

    private void update(HttpDataSource dataSource, ReportsArrayProcessor processor) {
        DataManager.loadData(ReportsActivity.this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        if (mPostParams != null)
            return Api.REPORTS_GET + "?" + mPostParams;
        else
            return Api.REPORTS_GET + Api.TYPE + mType;
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
    public void onDone(List<Reports> data) {
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
            mAdapter = new ArrayAdapter<Reports>(this, android.R.layout.simple_list_item_2, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(ReportsActivity.this, R.layout.adapter_item, null);
                    }
                    Reports item = getItem(position);
                    TextView reportDate = (TextView) convertView.findViewById(android.R.id.text1);
                    reportDate.setText(item.getDate());
                    TextView reportMessage = (TextView) convertView.findViewById(android.R.id.text2);
                    reportMessage.setText(item.getMessage());
                    return convertView;
                }

            };
            listView.setAdapter(mAdapter);
        } else {
            mEncodedRequest = "";
            mData.clear();
            mData.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
        dismissProgress();
        showEmpty();
        TextView errorView = (TextView) findViewById(R.id.error);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(errorView.getText() + "\n" + e.getMessage());
    }

    public void onReportsClick(View view) {
        EditText text = (EditText) findViewById(R.id.descriptionReportEditText);
        mRequest = text.getText().toString();
        mEncodedRequest = HttpUtils.getEncodedRequest(mRequest);
        mNameValuePair = new ArrayList<NameValuePair>(3);
        mNameValuePair.add(new BasicNameValuePair("type", mType));
        mNameValuePair.add(new BasicNameValuePair("message", mRequest));
        mNameValuePair.add(new BasicNameValuePair("token", OAuthHelper.authToken));
        mPostParams = URLEncodedUtils.format(mNameValuePair, "UTF-8");
//        Log.d("params ", "params " + mPostParams);
        HttpDataSource dataSource = getPostHttpDataSource();
        ReportsArrayProcessor processor = getProcessor();
        update(dataSource, processor);
        text.setText("");
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
