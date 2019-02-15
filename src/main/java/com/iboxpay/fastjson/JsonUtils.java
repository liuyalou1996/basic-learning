package com.iboxpay.fastjson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    if (obj == null) {
      return null;
    }

    if (isNullValueAllowed) {
      featureList.add(SerializerFeature.WriteMapNullValue);
      featureList.add(SerializerFeature.WriteNullListAsEmpty);
      featureList.add(SerializerFeature.WriteNullNumberAsZero);
      featureList.add(SerializerFeature.WriteNullStringAsEmpty);
      if (prettyFormat) {
        featureList.add(SerializerFeature.PrettyFormat);
      }

    } else {
      if (prettyFormat) {
        featureList.add(SerializerFeature.PrettyFormat);
      }
    }

    int size = featureList.size();
    SerializerFeature[] features = new SerializerFeature[size];
    System.arraycopy(featureList.toArray(), 0, features, 0, size);
    return JSON.toJSONString(obj, filters, features);
  }

  /**
   * 将json字符串转换为javaBean
   * 
   * @param jsonStr
   *            json字符串
   * @param clazz
   *            运行时对象
   * @return
   */
  public static <T> T toJavaBean(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseObject(jsonStr, clazz);
  }

  /**
   * 字符串转换为list
   * 
   * @param jsonStr
   *            json字符串
   * @param clazz
   *            运行时对象
   * @return
   */
  public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseArray(jsonStr, clazz);
  }

  /**
   * 将json字符串转换为map
   * 
   * @param jsonStr
   *            json字符串
   * @return
   */
  public static Map<String, Object> toMap(String jsonStr) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {});
  }

  /**
   * 将javaBean转换为map
   * 
   * @param obj
   *            转换的对象
   * @return
   */
  public static Map<String, Object> javaBeanToMap(Object obj) {
    if (obj == null) {
      return null;
    }

    return toMap(toJsonString(obj));
  }

  /**
   * 将map转换为javaBean
   * 
   * @param map
   *            map实例
   * @param clazz
   *            运行时对象
   * @return
   */
  public static <T> T mapToJavaBean(Map<String, ? extends Object> map, Class<T> clazz) {
    if (CollectionUtils.isEmpty(map)) {
      return null;
    }

    String jsonStr = JSON.toJSONString(map);
    return JSON.parseObject(jsonStr, clazz);
  }
}
