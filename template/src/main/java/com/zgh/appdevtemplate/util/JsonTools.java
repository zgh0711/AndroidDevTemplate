package com.zgh.appdevtemplate.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class JsonTools {

    public static Gson gson;

    static {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
    }

    /**
     * Object --> json
     */
    public static String buildJsonStr(Object obj) throws IOException {
        if (obj == null)
            throw new IOException("输入为null");
        return gson.toJson(obj);
    }

    /**
     * 把一个json反序列化为一个对象
     *
     * @param <T>
     */

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || "".equals(json))
            return null;
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

}
