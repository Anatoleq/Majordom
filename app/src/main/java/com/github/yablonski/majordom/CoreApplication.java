package com.github.yablonski.majordom;

import android.app.Application;
import android.content.Context;

import com.github.yablonski.majordom.image.ImageLoader;
import com.github.yablonski.majordom.source.CachedHttpDataSource;
import com.github.yablonski.majordom.source.HttpDataSource;
import com.github.yablonski.majordom.source.NewsDataSource;
import com.github.yablonski.majordom.source.PackagesDataSource;
import com.github.yablonski.majordom.source.ReportsDataSource;
import com.github.yablonski.majordom.source.UserDataSource;


/**
 * Created by Acer on 25.11.2014.
 */
public class CoreApplication extends Application {

    private HttpDataSource mHttpDataSource;
    private UserDataSource mUserDataSource;
    private NewsDataSource mNewsDataSource;
    private ReportsDataSource mReportsDataSource;
    private PackagesDataSource mPackagesDataSource;
    private CachedHttpDataSource mCachedHttpDataSource;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mHttpDataSource = new HttpDataSource();
        mUserDataSource = new UserDataSource();
        mNewsDataSource = new NewsDataSource();
        mPackagesDataSource = new PackagesDataSource();
        mCachedHttpDataSource = new CachedHttpDataSource(this);
    }

    @Override
    public Object getSystemService(String name) {
        if (HttpDataSource.KEY.equals(name)) {
            if (mHttpDataSource == null) {
                mHttpDataSource = new HttpDataSource();
            }
            return mHttpDataSource;
        }

        if (NewsDataSource.KEY.equals(name)) {
            if (mNewsDataSource == null) {
                mNewsDataSource = new NewsDataSource();
            }
            return mNewsDataSource;
        }

        if (PackagesDataSource.KEY.equals(name)) {
            if (mPackagesDataSource == null) {
                mPackagesDataSource = new PackagesDataSource();
            }
            return mPackagesDataSource;
        }

        if (ReportsDataSource.KEY.equals(name)) {
            if (mReportsDataSource == null) {
                mReportsDataSource = new ReportsDataSource();
            }
            return mReportsDataSource;
        }

        if (UserDataSource.KEY.equals(name)) {
            if (mUserDataSource == null) {
                mUserDataSource = new UserDataSource();
            }
            return mUserDataSource;
        }

        if (CachedHttpDataSource.KEY.equals(name)) {
            if (mCachedHttpDataSource == null) {
                mCachedHttpDataSource = new CachedHttpDataSource(this);
            }
            return mCachedHttpDataSource;
        }

        if (ImageLoader.KEY.equals(name)) {
            if (mImageLoader == null) {
                mImageLoader = new ImageLoader(this);
            }
            return mImageLoader;
        }

        return super.getSystemService(name);
    }

    public static <T> T get(Context context, String key) {
        if (context == null || key == null) {
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
