/*
 * Copyright (C) 2011-2019 ShenZhen iBOXCHAIN Information Technology Co.,Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary
 * information of iBoxChain Company of China.
 * ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement
 * you entered into with iBoxchain inc.
 */
package com.universe.thirdparty.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * @author: liuyalou
 * @date: 2019年7月23日
 */
public abstract class GsonUtils {

  private static final Gson DEFAULT_GSON = new Gson();

  public static String toJsonString(Object obj) {
    return DEFAULT_GSON.toJson(obj);
  }

  public static String toPrettyJsonString(Object obj) {
    return toJsonString(obj, false, true, false);
  }

  public static String toJsonStringWithNullValue(Object obj) {
    return toJsonString(obj, true, false, false);
  }

  public static String toPrettyJsonStringWithNullValue(Object obj) {
    return toJsonString(obj, false, false, true);
  }

  public static String toJsonStringWithExcludeMode(Object obj) {
    return toJsonString(obj, false, false, true);
  }

  private static String toJsonString(Object obj, boolean serializeNulls, boolean prettyFormat, boolean excludeField) {
    if (Objects.isNull(obj)) {
      return null;
    }

    GsonBuilder builder = new GsonBuilder();

    if (serializeNulls) {
      builder.serializeNulls();
    }

    if (prettyFormat) {
      builder.setPrettyPrinting();
    }

    if (excludeField) {
      builder.excludeFieldsWithoutExposeAnnotation();
    }
    Gson gson = builder.create();
    return gson.toJson(obj);
  }

  public static <T> T toJavaBean(String jsonStr, Class<T> clazz) {
    return DEFAULT_GSON.fromJson(jsonStr, clazz);
  }

  public static <T> T toCollection(String jsonStr, TypeToken<T> typeToken) {
    return DEFAULT_GSON.fromJson(jsonStr, typeToken.getType());
  }

  public static class JsonToMapDeserializer implements JsonDeserializer<Map<String, Object>> {

    @Override
    public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();
      return null;
    }

  }

  public static void main(String[] args) {
    Map<String, Object> map = new HashMap<>();
    map.put("name", "liuyalou");
    map.put("age", 20);

    List<Map<String, Object>> list = new ArrayList<>();
    list.add(map);

    String jsonStr = toJsonString(map);
    System.out.println(jsonStr);

    System.out.println(toCollection(jsonStr, new TypeToken<Map<String, Object>>() {}));

    GsonBuilder builder = new GsonBuilder();
    Type type = new TypeToken<Map<String, Object>>() {}.getType();
    builder.registerTypeAdapter(type, new JsonToMapDeserializer());
    Gson gson = builder.create();

    Map<String, Object> newMap = gson.fromJson(jsonStr, type);
    System.out.println(newMap);

  }
}
