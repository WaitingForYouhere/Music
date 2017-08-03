package com.example.lenovo.music.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/8/3.
 */

public class GsonUtil {
    public static <T>List<T> parseJsonWithGson(String jsonData, Class<T> type) {
        List<T> result=new ArrayList<T>();
        Gson gson = new Gson();
        JsonArray arry = new JsonParser().parse(jsonData).getAsJsonArray();
        for (JsonElement jsonElement : arry) {
            result.add(gson.fromJson(jsonElement, type));
        }
        return result;
    }
}
