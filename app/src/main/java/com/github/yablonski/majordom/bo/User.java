package com.github.yablonski.majordom.bo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Acer on 04.11.2014.
 */
public class User extends JSONObjectWrapper {
    private Long uid;
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String Suite_NR = "suite_nr";

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };


    /*public User(String jsonObject) {
        super(jsonObject);
    }*/

    public User(JSONObject jsonObject) {
        super(jsonObject);
    }

    protected User(Parcel in) {
        super(in);
    }

    public String getFirstName() {
        return getString(FIRST_NAME);
    }

    public String getLastName() {
        return getString(LAST_NAME);
    }

    public String getEmail() {
        return getString(EMAIL);
    }

    public String getSuiteNr() {
        return getString(Suite_NR);
    }

}
