package com.github.yablonski.majordom.bo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Acer on 29.01.2015.
 */
public class Reports extends JSONObjectWrapper {
    private Long uid;
    private static final String DATE = "order_date";
    private static final String MESSAGE = "message";
    private static final String ID = "id";


    public static final Parcelable.Creator<Reports> CREATOR
            = new Parcelable.Creator<Reports>() {
        public Reports createFromParcel(Parcel in) {
            return new Reports(in);
        }
        public Reports[] newArray(int size) {
            return new Reports[size];
        }
    };

    public Reports(JSONObject jsonObject) {
        super(jsonObject);
    }

    protected Reports(Parcel in) {
        super(in);
    }

    public String getDate() {
        return getString(DATE);
    }

    public String getMessage() {
        return getString(MESSAGE);
    }

    public Long getId() {
        return getLong(ID);
    }
}
