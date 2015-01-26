package com.github.yablonski.majordom.bo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Acer on 05.12.2014.
 */
public class News extends JSONObjectWrapper {
    private Long uid;
    private static final String DATE = "date";
    private static final String TITLE = "title";
    private static final String IMAGE = "image";
    private static final String ID = "id";


    public static final Parcelable.Creator<News> CREATOR
            = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
                return new News(in);
            }
        public News[] newArray(int size) {
                return new News[size];
            }
    };

    /*public News(String jsonObject) {
            super(jsonObject);
        }*/

    public News(JSONObject jsonObject) {
            super(jsonObject);
        }

    protected News(Parcel in) {
            super(in);
        }

    public String getDate() {
        return getString(DATE);
    }

    public String getTitle() {
        return getString(TITLE);
    }

    public String getImage() {
        return getString(IMAGE);
    }

    public Long getId() {
        return getLong(ID);
    }
}
