package com.github.yablonski.majordom.processing;

import com.github.yablonski.majordom.bo.Reports;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 29.01.2015.
 */
public class ReportsArrayProcessor implements Processor<List<Reports>, InputStream> {

    @Override
    public List<Reports> process(InputStream inputStream) throws Exception {
        String string = new StringProcessor().process(inputStream);
        JSONArray array = new JSONArray(string);
        List<Reports> reportsArray = new ArrayList<Reports>(array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Reports reports = new Reports(jsonObject);
            reportsArray.add(reports);
        }
        return reportsArray;
    }
}
