package com.github.yablonski.majordom.processing;

import com.github.yablonski.majordom.bo.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 25.11.2014.
 */
public class UserArrayProcessor implements Processor<List<User>,InputStream>{

@Override
public List<User> process(InputStream inputStream) throws Exception {
        String string = new StringProcessor().process(inputStream);
        JSONArray array = new JSONArray(string);
        //TODO wrapper for array
        List<User> noteArray = new ArrayList<User>(array.length());
        for (int i = 0; i < array.length(); i++) {
        JSONObject jsonObject = array.getJSONObject(i);
        User user = new User(jsonObject);
        noteArray.add(user);
        }
        return noteArray;
        }
}
