package cn.sourcecodes.chatterClient.common.utils;

import com.google.gson.Gson;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class JsonUtils {
    private static final Gson gson = new Gson();

    public static String toJson(Object object, Class<?> clazz) {
        String json = gson.toJson(object, clazz);
        return json;
    }

    public static Object resolveJson(String jsonStr, Class<?> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }
}
