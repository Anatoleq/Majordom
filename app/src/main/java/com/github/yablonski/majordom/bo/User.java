package com.github.yablonski.majordom.bo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Acer on 04.11.2014.
 */
public class User extends JSONObjectWrapper {
    //private String response;
    private Long uid;
    private static final String USER_NAME = "user_name";
    private static final String EMAIL = "email";

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String jsonObject) {
        super(jsonObject);
    }

    public User(JSONObject jsonObject) {
        super(jsonObject);
    }

    protected User(Parcel in) {
        super(in);
    }

    public String getUserName() {
        return getString(USER_NAME);
    }

    public String getEmail() {
        return getString(EMAIL);
    }

}
