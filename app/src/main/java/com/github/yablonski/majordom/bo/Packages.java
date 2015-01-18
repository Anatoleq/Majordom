package com.github.yablonski.majordom.bo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Acer on 12.01.2015.
 */
public class Packages extends JSONObjectWrapper {

    private static final String SENDER = "sender";
    private static final String INCOME_DATE = "incomedate";
    private static final String RECEIVED_DATE = "receiveddate";

    public static final Parcelable.Creator<Packages> CREATOR
        = new Parcelable.Creator<Packages>() {
            public Packages createFromParcel(Parcel in) {
                return new Packages(in);
            }
            public Packages[] newArray(int size) {
                return new Packages[size];
            }
        };

    public Packages(String jsonObject) {
            super(jsonObject);
        }

    public Packages(JSONObject jsonObject) {
            super(jsonObject);
        }

    protected Packages(Parcel in) {
            super(in);
        }

    public String getSender() {
        return getString(SENDER);
    }

    public String getIncomeDate() {
        return getString(INCOME_DATE);
    }

    public String getReceivedDate() {
        return getString(RECEIVED_DATE);
    }

}