package com.github.yablonski.majordom;

import android.app.Application;
import android.content.Context;

import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.NewsDataSource;
import com.github.yablonski.majordom.source.UserDataSource;
import com.github.yablonski.majordom.source.VkDataSource;

/**
 * Created by Acer on 25.11.2014.
 */
public class CoreApplication extends Application {

    private HttpDataSource mHttpDataSource;
    private VkDataSource mVkDataSource;
    private UserDataSource mUserDataSource;
    private NewsDataSource mNewsDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        mHttpDataSource = new HttpDataSource();
        mVkDataSource = new VkDataSource();
        mUserDataSource = new UserDataSource();
        mNewsDataSource = new NewsDataSource();
    }

    @Override
    public Object getSystemService(String name) {
        if (HttpDataSource.KEY.equals(name)) {
            //for android kitkat +
            if (mHttpDataSource == null) {
                mHttpDataSource = new HttpDataSource();
            }
            return mHttpDataSource;
        }

        if (NewsDataSource.KEY.equals(name)) {
            //for android kitkat +
            if (mNewsDataSource == null) {
                mNewsDataSource = new NewsDataSource();
            }
            return mNewsDataSource;
        }

        if (VkDataSource.KEY.equals(name)) {
            //for android kitkat +
            if (mVkDataSource == null) {
                mVkDataSource = new VkDataSource();
            }
            return mVkDataSource;
        }
        if (UserDataSource.KEY.equals(name)) {
            //for android kitkat +
            if (mUserDataSource == null) {
                mUserDataSource = new UserDataSource();
            }
            return mUserDataSource;
        }
        return super.getSystemService(name);
    }

    public static <T> T get(Context context, String key) {
        if (context == null || key == null){
            throw new IllegalArgumentException("Context and key must not be null");
        }
        T systemService = (T) context.getSystemService(key);
        if (systemService == null) {
            context = context.getApplicationContext();
            systemService = (T) context.getSystemService(key);
        }
        if (systemService == null) {
            throw new IllegalStateException(key + " not available");
        }
        return systemService;
    }
}
