package com.github.yablonski.majordom.processing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.yablonski.majordom.source.HttpDataSource;

import java.io.InputStream;

/**
 * Created by Acer on 14.11.2014.
 */
public class BitmapProcessor implements Processor<Bitmap, InputStream> {

    @Override
    public Bitmap process(InputStream inputStream) throws Exception {
        try {
            return BitmapFactory.decodeStream(inputStream);
        } finally {
            HttpDataSource.close(inputStream);
        }
    }
}
