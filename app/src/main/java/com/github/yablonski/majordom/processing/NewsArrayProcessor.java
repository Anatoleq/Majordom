package com.github.yablonski.majordom.processing;

import com.github.yablonski.majordom.bo.News;
import com.github.yablonski.majordom.bo.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 05.12.2014.
 */
public class NewsArrayProcessor implements Processor<List<News>,InputStream>{

        @Override
        public List<News> process(InputStream inputStream) throws Exception {
            String string = new StringProcessor().process(inputStream);
            JSONArray array = new JSONArray(string);
            //TODO wrapper for array
            List<News> noteArray = new ArrayList<News>(array.length());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                News news = new News(jsonObject);
                noteArray.add(news);
            }
            return noteArray;
        }
}
