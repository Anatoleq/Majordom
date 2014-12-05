package com.github.yablonski.majordom;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.yablonski.majordom.bo.User;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.processing.UserArrayProcessor;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.UserDataSource;

import java.util.List;

/**
 * Created by Acer on 27.11.2014.
 */
public class MainList extends ListFragment /*implements DataManager.Callback<List<User>>*/ {

 /*   private ArrayAdapter mAdapter;

    private UserArrayProcessor mUserArrayProcessor = new UserArrayProcessor();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);
        final HttpDataSource dataSource = getHttpDataSource();
        final UserArrayProcessor processor = getProcessor();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update(dataSource, processor);
            }
        });
        update(dataSource, processor);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListAdapter(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

    private UserArrayProcessor getProcessor() {
        return mUserArrayProcessor;
    }

    private HttpDataSource getHttpDataSource() {
        return UserDataSource.get(MainList.this);
    }

    private void update(HttpDataSource dataSource, UserArrayProcessor processor) {
        DataManager.loadData(MainList.this,
                getUrl(),
                dataSource,
                processor);
    }

    private String getUrl() {
        return Api.FRIENDS_GET;
    }

    @Override
    public void onDataLoadStart() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            getActivity().findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
        }
        getActivity().findViewById(android.R.id.empty).setVisibility(View.GONE);
    }

    private List<User> mData;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(List<User> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        getActivity().findViewById(android.R.id.progress).setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            getActivity().findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
        }
        AdapterView listView = (AbsListView) getActivity().findViewById(android.R.id.list);
        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<User>(this, R.layout.adapter_item, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(MainList.this, R.layout.adapter_item, null);
                    }
                    User item = getItem(position);
                    TextView textView1 = (TextView) convertView.findViewById(android.R.id.text1);
                    textView1.setText(item.getUserName());
                    TextView textView2 = (TextView) convertView.findViewById(android.R.id.text2);
                    textView2.setText(item.getEmail());
                    //convertView.setTag(item.getId());
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
        getActivity().findViewById(android.R.id.progress).setVisibility(View.GONE);
        getActivity().findViewById(android.R.id.empty).setVisibility(View.GONE);
        TextView errorView = (TextView) getActivity().findViewById(R.id.error);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(errorView.getText() + "\n" + e.getMessage());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, null);
    }*/
}
