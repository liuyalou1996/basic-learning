package com.universe.thirdparty.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * json字符串与java bean转换工具类
 * @author: liuyalou
 * @date: 2019年10月29日
 */
public class FastJsonUtils {

  public static String toJsonString(Object obj) {
    return toJsonString(obj, null, false, false);
  }

  /**
   * 转化为json字符串
   * @param obj 目标对象
   * @param filters 序列化过滤器
   * @return json字符串
   */
  public static String toJsonString(Object obj, SerializeFilter... filters) {
    return toJsonString(obj, null, false, false, filters);
  }

  /**
   * 转化为带空值的json字符串
   * @param obj 目标对象
   * @param filters 序列化过滤器
   * @return json字符串
   */
  public static String toNullableJsonString(Object obj, SerializeFilter... filters) {
    return toJsonString(obj, null, true, false, filters);
  }

  /**
   * 转化为已格式化的json字符串
   * @param obj 目标对象
   * @param filters 序列化过滤器
   * @return json字符串
   */
  public static String toPrettyJsonString(Object obj, SerializeFilter... filters) {
    return toJsonString(obj, null, false, true, filters);
  }

  /**
   * 转化为带空值已格式化的Json字符串
   * @param obj 目标对象
   * @param filters 序列化过滤器
   * @return json字符串
   */
  public static String toNullablePrettyJsonString(Object obj, SerializeFilter... filters) {
    return toJsonString(obj, null, true, true, filters);
  }

  public static String toDateFormattedJsonString(Object obj, String dateFormat, SerializeFilter... filters) {
    return toJsonString(obj, dateFormat, false, false, filters);
  }

  public static String toNullableAndDateFormattedJsonString(Object obj, String dateFormat, SerializeFilter... filters) {
    return toJsonString(obj, dateFormat, true, false, filters);
  }

  public static String toDateFormattedPrettyJsonString(Object obj, String dateFormat, SerializeFilter... filters) {
    return toJsonString(obj, dateFormat, false, true, filters);
  }

  public static String toNullableAndDateFormattedPrettyJsonString(Object obj, String dateFormat, SerializeFilter... filters) {
    return toJsonString(obj, dateFormat, true, true, filters);
  }

  public static String toJsonString(Object obj, String dateFormat, boolean writeNullValue, boolean prettyFormat,
      SerializeFilter... filters) {
    if (obj == null) {
      return null;
    }

    int defaultFeature = JSON.DEFAULT_GENERATE_FEATURE;
    if (writeNullValue) {
      return prettyFormat
          ? JSON.toJSONString(obj, SerializeConfig.globalInstance, filters, dateFormat, defaultFeature, SerializerFeature.WriteMapNullValue,
              SerializerFeature.PrettyFormat)
          : JSON.toJSONString(obj, SerializeConfig.globalInstance, filters, dateFormat, defaultFeature,
              SerializerFeature.WriteMapNullValue);
    }

    return prettyFormat
        ? JSON.toJSONString(obj, SerializeConfig.globalInstance, filters, dateFormat, defaultFeature, SerializerFeature.PrettyFormat)
        : JSON.toJSONString(obj, SerializeConfig.globalInstance, filters, dateFormat, defaultFeature);

  }

  public static String toJsonStringWithKeyOrdered(Object obj) {
    return toJsonString(beanToTreeMap(obj));
  }

  /**
   * json字符串转bean
   * @param jsonStr json字符串
   * @param clazz 运行时对象
   * @return bean
   * @param <T>
   */
  public static <T> T toBean(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseObject(jsonStr, clazz);
  }

  /**
   * json字符串转List
   * @param jsonStr json字符串
   * @param clazz 运行时对象
   * @return List对象
   * @param <T>
   */
  public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseArray(jsonStr, clazz);
  }

  /**
   * json字符串转Map
   * @param jsonStr json字符串
   * @return Map对象
   */
  public static Map<String, Object> toMap(String jsonStr) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {});
  }

  /**
   * bean转Map
   * @param obj
   * @return
   */
  public static Map<String, Object> beanToMap(Object obj) {
    if (Objects.isNull(obj)) {
      return null;
    }

    return toMap(toJsonString(obj));
  }

  public static Map<String, Object> beanToTreeMap(Object obj) {
    if (Objects.isNull(obj)) {
      return null;
    }

    return toTreeMap(toJsonString(obj));
  }

  public static Map<String, Object> toTreeMap(String jsonStr) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    JSONObject jsonObject = JSON.parseObject(jsonStr);
    Map<String, Object> treeMap = new TreeMap<>();
    addJsonObjectToTreeMap(treeMap, jsonObject);
    return treeMap;
  }

  private static void addJsonObjectToTreeMap(Map<String, Object> treeMap, JSONObject jsonObject) {
    jsonObject.forEach((key, value) -> {
      if (value instanceof JSONObject) {
        // 递归处理嵌套的 JSONObject
        Map<String, Object> nestedSortedMap = new TreeMap<>(Comparator.naturalOrder());
        addJsonObjectToTreeMap(nestedSortedMap, (JSONObject) value);
        treeMap.put(key, nestedSortedMap);
      } else {
        treeMap.put(key, value);
      }
    });
  }

  /**
   * map转bean
   * @param map
   * @param clazz
   * @return
   * @param <T>
   */
  public static <T> T mapToBean(Map<String, ? extends Object> map, Class<T> clazz) {
    if (CollectionUtils.isEmpty(map)) {
      return null;
    }

    String jsonStr = JSON.toJSONString(map);
    return JSON.parseObject(jsonStr, clazz);
  }

  public static void main(String[] args) {
    String jsonString = "{\"key3\":{\"subKey2\":{\"subSubKey2\":\"value2\",\"subSubKey1\":\"value1\"},\"subKey1\":\"value1\"},\"key1\":\"value1\",\"key2\":\"value2\"}";
    System.out.println(toPrettyJsonString(toTreeMap(jsonString)));
  }

}
