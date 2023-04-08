package me.centrium.bossfight.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {
    public Gson GSON = new Gson();
    public JsonParser JSON_PARSER = new JsonParser();

    public <T> T fromJson(String json, Class<T> clazz){
        return GSON.fromJson(json, clazz);
    }

    public JsonElement parse(String json) {
        return JSON_PARSER.parse(json);
    }

    public String toJson(Object object) {
        return GSON.toJson(object);
    }
}
