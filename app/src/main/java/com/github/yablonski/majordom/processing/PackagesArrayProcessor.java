package com.github.yablonski.majordom.processing;

import com.github.yablonski.majordom.bo.News;
import com.github.yablonski.majordom.bo.Packages;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 12.01.2015.
 */
public class PackagesArrayProcessor implements Processor<List<Packages>,InputStream> {

    @Override
    public List<Packages> process(InputStream inputStream) throws Exception {
        String string = new StringProcessor().process(inputStream);
        JSONArray array = new JSONArray(string);
        //TODO wrapper for array
        List<Packages> noteArray = new ArrayList<Packages>(array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Packages packages = new Packages(jsonObject);
            noteArray.add(packages);
        }
        return noteArray;
    }
}
