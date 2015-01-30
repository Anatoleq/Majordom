package com.github.yablonski.majordom.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;

import com.github.yablonski.majordom.CoreApplication;
import com.github.yablonski.majordom.helper.DataManager;
import com.github.yablonski.majordom.os.assist.LIFOLinkedBlockingDeque;
import com.github.yablonski.majordom.processing.BitmapProcessor;
import com.github.yablonski.majordom.processing.Processor;
import com.github.yablonski.majordom.source.CachedHttpDataSource;
import com.github.yablonski.majordom.source.DataSource;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Acer on 10.12.2014.
 */
public class ImageLoader {

    public static final String KEY = "ImageLoader";
    //TODO generate max memory based on device specification
    public static final int MAX_SIZE = 1 * 1024 * 1024;

    private AtomicBoolean isPause = new AtomicBoolean(false);

    public static ImageLoader get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    private class ComparableRunnable implements Runnable {

        private Handler mHandler;

        private DataManager.Callback<Bitmap> mCallback;
        private String mS;
        private DataSource<InputStream, String> mDataSource;
        private Processor<Bitmap, InputStream> mProcessor;

        public ComparableRunnable(Handler handler, DataManager.Callback<Bitmap> callback, String s, DataSource<InputStream, String> dataSource, Processor<Bitmap, InputStream> processor) {
            mHandler = handler;
            this.mCallback = callback;
            this.mS = s;
            this.mDataSource = dataSource;
            this.mProcessor = processor;
        }

        @Override
        public void run() {
            try {
                InputStream result = mDataSource.getResult(mS);
                final Bitmap bitmap = mProcessor.process(result);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onDone(bitmap);
                    }
                });
            } catch (final Exception e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onError(e);
                    }
                });
            }
        }
    }

    private Context mContext;
    private DataSource<InputStream, String> mDataSource;
    private Processor<Bitmap, InputStream> mProcessor;
    private DataManager.Loader<Bitmap, InputStream, String> mLoader;
    private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(MAX_SIZE) {

        @Override
        protected int sizeOf(String key, Bitmap value) {
            //TODO check correct calculation of bitmap size
            return (value.getWidth() * value.getHeight())*32 + key.length();
        }
    };

    public ImageLoader(Context context, DataSource<InputStream, String> dataSource, Processor<Bitmap, InputStream> processor) {
        this.mContext = context;
        this.mDataSource = dataSource;
        this.mProcessor = processor;
        //TODO can be customizable
        this.mLoader = new DataManager.Loader<Bitmap, InputStream, String>() {

            private ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS,
                    new LIFOLinkedBlockingDeque<Runnable>());

            @Override
            public void load(final DataManager.Callback<Bitmap> callback, final String s, final DataSource<InputStream, String> dataSource, final Processor<Bitmap, InputStream> processor) {
                callback.onDataLoadStart();
                final Looper looper = Looper.myLooper();
                final Handler handler = new Handler(looper);
                executorService.execute(new ComparableRunnable(handler, callback, s, dataSource, processor));
            }

        };
    }

    public ImageLoader(Context context) {
        this(context, CachedHttpDataSource.get(context), new BitmapProcessor());
    }

    public void pause() {
        isPause.set(true);
    }

    private final Object mDelayedLock = new Object();

    public void resume() {
        isPause.set(false);
        synchronized (mDelayedLock) {
            for (ImageView imageView : delayedImagesViews) {
                Object tag = imageView.getTag();
                if (tag != null) {
                    loadAndDisplay((String) tag, imageView);
                }
            }
            delayedImagesViews.clear();
        }
    }

    private Set<ImageView> delayedImagesViews = new HashSet<ImageView>();

    public void loadAndDisplay(final String url, final ImageView imageView) {
        Bitmap bitmap = mLruCache.get(url);
        imageView.setImageBitmap(bitmap);
        imageView.setTag(url);
        if (bitmap != null) {
            return;
        }
        if (isPause.get()) {
            synchronized (mDelayedLock) {
                delayedImagesViews.add(imageView);
            }
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            //TODO create sync Map to check existing request and existing callbacks
            //TODO add delay and cancel old request or create limited queue
            DataManager.loadData(new DataManager.Callback<Bitmap>() {
                @Override
                public void onDataLoadStart() {

                }

                @Override
                public void onDone(Bitmap bitmap) {
                    if (bitmap != null) {
                        mLruCache.put(url, bitmap);
                    }
                    if (url.equals(imageView.getTag())) {
                        imageView.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onError(Exception e) {

                }

            }, url, mDataSource, mProcessor, mLoader);
        }
    }
}
