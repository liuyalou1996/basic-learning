package com.universe.thirdparty.fastjson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {

  private static List<SerializerFeature> featureList = new ArrayList<>();

  static {
    featureList.add(SerializerFeature.WriteDateUseDateFormat);
  }

  public static String toJsonString(Object obj, SerializeFilter... filters) {
    return toJsonString(obj, false, false, filters);
  }

  public static String toJsonStringWithNullValue(Object obj, SerializeFilter... filters) {
    return toJsonString(obj, true, false, filters);
  }

  public static String toPrettyJsonString(Object obj, SerializeFilter... filters) {
    return toJsonString(obj, false, true, filters);
  }

  public static String toPrettyJsonStringWithNullValue(Object obj, SerializeFilter... filters) {
    return toJsonString(obj, true, true, filters);
  }

  private static String toJsonString(Object obj, boolean isNullValueAllowed, boolean prettyFormat,
      SerializeFilter... filters) {
    if (Objects.isNull(obj)) {
      return null;
    }

    if (isNullValueAllowed) {
      if (prettyFormat) {
        return JSON.toJSONString(obj, filters, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat,
            SerializerFeature.WriteDateUseDateFormat);
      }
      return JSON.toJSONString(obj, filters, SerializerFeature.WriteMapNullValue,
          SerializerFeature.WriteDateUseDateFormat);
    } else {
      if (prettyFormat) {
        return JSON.toJSONString(obj, filters, SerializerFeature.PrettyFormat,
            SerializerFeature.WriteDateUseDateFormat);
      }
      return JSON.toJSONString(obj, filters, SerializerFeature.WriteDateUseDateFormat);
    }
  }

  public static <T> T toJavaBean(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseObject(jsonStr, clazz);
  }

  public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseArray(jsonStr, clazz);
  }

  public static Map<String, Object> toMap(String jsonStr) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {});
  }

  public static Map<String, Object> javaBeanToMap(Object obj) {
    if (Objects.isNull(obj)) {
      return null;
    }

    return toMap(toJsonString(obj));
  }

  public static <T> T mapToJavaBean(Map<String, ? extends Object> map, Class<T> clazz) {
    if (CollectionUtils.isEmpty(map)) {
      return null;
    }

    String jsonStr = JSON.toJSONString(map);
    return JSON.parseObject(jsonStr, clazz);
  }

}
