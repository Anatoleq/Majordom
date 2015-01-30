package com.github.yablonski.majordom;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.github.yablonski.majordom.bo.Reports;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.processing.ReportsArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.ReportsDataSource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ReportsActivity extends ActionBarActivity implements DataManager.Callback<List<Reports>> {

    private int titleId;
    public int id;
    private String title, message = "", request, type, encodedRequest;
    private ArrayAdapter mAdapter;
    private ReportsArrayProcessor mReportsArrayProcessor = new ReportsArrayProcessor();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Reports> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        titleId = intent.getIntExtra(MenuActivity.sKey, id);
        title = getString(titleId);
        setTitle(title);
        setContentView(R.layout.activity_reports);
        dismissLine();
        TextView titleReportTextView = (TextView) findViewById(R.id.titleReportTextView);
        switch (titleId) {
            case R.string.main_menu_reports:
                message = getString(R.string.report_message);
                type = getString(R.string.report_type);
                break;
            case R.string.main_menu_complaints:
                message = getString(R.string.complaint_message);
                type = getString(R.string.complaint_type);
                break;
        }
        titleReportTextView.setText(message);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        final HttpDataSource dataSource = getHttpDataSource();
        final ReportsArrayProcessor processor = getProcessor();
        encodedRequest = "";
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

    private void update(HttpDataSource dataSource, ReportsArrayProcessor processor) {
        DataManager.loadData(ReportsActivity.this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        return Api.REPORTS_GET + Api.TYPE + type + Api.REQUEST + encodedRequest;
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
            dismissLine();
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
            showLine();
        } else {
            encodedRequest = "";
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
        request = text.getText().toString();
        encodedRequest = getEncodedRequest(request);
        HttpDataSource dataSource = getHttpDataSource();
        ReportsArrayProcessor processor = getProcessor();
        update(dataSource, processor);
        text.setText("");
    }

    public static String getEncodedRequest(String plainText) {
        try {
            return URLEncoder.encode(plainText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return plainText;
        }
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

    private void showLine() {
        findViewById(R.id.line).setVisibility(View.VISIBLE);
    }
    private void dismissLine() {
        findViewById(R.id.line).setVisibility(View.GONE);
    }

}
