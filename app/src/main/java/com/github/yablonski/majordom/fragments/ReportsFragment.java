package com.github.yablonski.majordom.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.yablonski.majordom.Api;
import com.github.yablonski.majordom.R;
import com.github.yablonski.majordom.auth.OAuthHelper;
import com.github.yablonski.majordom.bo.Reports;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.processing.ReportsArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.ReportsDataSource;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 08.02.2015.
 */
public class ReportsFragment extends Fragment implements DataManager.Callback<List<Reports>> {

    private ArrayAdapter mAdapter;
    private ReportsArrayProcessor mReportsArrayProcessor = new ReportsArrayProcessor();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Reports> mData;
    private AbsListView mListView;
    private TextView mError;
    private TextView mEmpty;
    private ProgressBar mProgressBar;
    private TextView titleServiceTextView;
    private String mType;
    private EditText mText;
    private List<NameValuePair> mNameValuePair;
    private String mPostParams;
    private String mRequest;
    private Button mButton;

    public static ReportsFragment newInstance(int pos) {
        ReportsFragment fragment = new ReportsFragment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        fragment.setArguments(args);
        return fragment;
    }

    public ReportsFragment() {
    }

    int getPosition() {
        return getArguments().getInt("position", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reports, container, false);
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        mEmpty = (TextView) view.findViewById(android.R.id.empty);
        mError = (TextView) view.findViewById(R.id.error);
        titleServiceTextView = (TextView) view.findViewById(R.id.titleReportTextView);
        mText = (EditText) view.findViewById(R.id.descriptionReportEditText);
        mButton = (Button) view.findViewById(R.id.sendReportButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null) {
                    mRequest = mText.getText().toString();
                    mNameValuePair = new ArrayList<NameValuePair>(3);
                    mNameValuePair.add(new BasicNameValuePair("type", mType));
                    mNameValuePair.add(new BasicNameValuePair("message", mRequest));
                    mNameValuePair.add(new BasicNameValuePair("token", OAuthHelper.authToken));
                    mPostParams = URLEncodedUtils.format(mNameValuePair, "UTF-8");
                    HttpDataSource dataSource = getHttpDataSource();
                    ReportsArrayProcessor processor = getProcessor();
                    update(dataSource, processor);
                    mText.setText("");
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final HttpDataSource dataSource = getHttpDataSource();
        final ReportsArrayProcessor processor = getProcessor();
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
        return ReportsDataSource.get(getActivity().getApplicationContext());
    }

    private void update(HttpDataSource dataSource, ReportsArrayProcessor processor) {
        DataManager.loadData(this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        if (mPostParams != null) {
            return Api.REPORTS_GET + "?" + mPostParams;
        } else {
            int type = getPosition();
            switch (type) {
                case R.string.report_type:
                    mType = getString(R.string.report_type);
                    titleServiceTextView.setText(R.string.report_message);
                    break;
                case R.string.complaint_type:
                    mType = getString(R.string.complaint_type);
                    titleServiceTextView.setText(R.string.complaint_message);
                    break;
            }
            return Api.REPORTS_GET + Api.TYPE + mType;
        }
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
        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<Reports>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, data) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    switch (position) {
                        case R.string.report_type:
                            mType = getString(R.string.report_type);
                            break;
                        case R.string.complaint_type:
                            mType = getString(R.string.complaint_type);
                            break;
                    }
                    if (convertView == null) {
                        convertView = View.inflate(getActivity().getApplicationContext(), R.layout.adapter_item, null);
                    }
                    Reports item = getItem(position);
                    TextView reportDate = (TextView) convertView.findViewById(android.R.id.text1);
                    reportDate.setText(item.getDate());
                    reportDate.setTextColor(getResources().getColor(R.color.secondary_text));
                    TextView reportMessage = (TextView) convertView.findViewById(android.R.id.text2);
                    reportMessage.setText(item.getMessage());
                    reportMessage.setTextColor(getResources().getColor(R.color.primary_text));
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
