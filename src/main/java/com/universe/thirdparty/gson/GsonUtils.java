package com.universe.thirdparty.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public abstract class GsonUtils {

  private static final ObjectTypeAdapter OBJECT_TYPE_ADAPTER = new ObjectTypeAdapter();
  private static final Map<Type, Gson> GSON_INSTANCE_CACHE = new ConcurrentHashMap<>();

  private static class DefaultGsonSingletonHolder {

    private static final Gson DEFAULT_GSON = new Gson();

    public static Gson getInstance() {
      return DEFAULT_GSON;
    }
  }

  public static class MapTypeSingletonHolder {

    private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>() {}.getType();

    public static Type getInstance() {
      return MAP_TYPE;
    }

  }

  public static class ListTypeSingletonHolder {

    private static final Type LIST_TYPE = new TypeToken<List<Map<String, Object>>>() {}.getType();

    public static Type getInstance() {
      return LIST_TYPE;
    }

  }

  public static String toJsonString(Object obj) {
    return obj == null ? null : DefaultGsonSingletonHolder.getInstance().toJson(obj);
  }

  public static String toPrettyJsonString(Object obj) {
    return toJsonString(obj, false, true, false);
  }

  public static String toJsonStringWithNullValue(Object obj) {
    return toJsonString(obj, true, false, false);
  }

  public static String toPrettyJsonStringWithNullValue(Object obj) {
    return toJsonString(obj, true, true, false);
  }

  public static String toJsonStringWithExcludeMode(Object obj) {
    return toJsonString(obj, false, false, true);
  }

  /**
   * @param obj
   * @param serializeNulls 是否序列化空值
   * @param prettyFormat  是否美化打印
   * @param excludeField 是否过滤属性，不带@Expose注解的属性将不会被序列化和反序列化
   * @return
   */
  private static String toJsonString(Object obj, boolean serializeNulls, boolean prettyFormat, boolean excludeField) {
    if (obj == null) {
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

  public static <T> T toBean(String jsonStr, Class<T> clazz) {
    return StringUtils.isEmpty(jsonStr) ? null : DefaultGsonSingletonHolder.getInstance().fromJson(jsonStr, clazz);
  }

  public static <T> T toCollection(String jsonStr, Type type) {
    if (StringUtils.isEmpty(jsonStr)) {
      return null;
    }

    Gson gson = getGsonInstance(type);
    return gson.fromJson(jsonStr, type);
  }

  public static <T> T mapToBean(Map<String, ? extends Object> map, Class<T> clazz) {
    return toBean(toJsonString(map), clazz);
  }

  public static Map<String, Object> beanToMap(Object obj) {
    return toCollection(toJsonString(obj), MapTypeSingletonHolder.getInstance());
  }

  private static Gson getGsonInstance(Type type) {
    Gson gson = GSON_INSTANCE_CACHE.get(type);
    if (gson == null) {
      GsonBuilder builder = new GsonBuilder();

      // 反序列化为Map<String,Object>或List<Map<String,Object>>，当值为Object时针对数字要特殊处理
      if (MapTypeSingletonHolder.getInstance().equals(type) || ListTypeSingletonHolder.getInstance().equals(type)) {
        builder.registerTypeAdapter(type, OBJECT_TYPE_ADAPTER);
      }

      gson = builder.create();
      GSON_INSTANCE_CACHE.put(type, gson);
    }

    return gson;
  }

  /**
   * 对象类型适配器，针对数字做了特殊处理
   * @author liuyalou
   * @date 2019年10月27日
   */
  private static class ObjectTypeAdapter extends TypeAdapter<Object> {

    @Override
    public Object read(JsonReader in) throws IOException {
      JsonToken token = in.peek();
      switch (token) {
        case BEGIN_ARRAY:
          List<Object> list = new ArrayList<Object>();
          in.beginArray();
          while (in.hasNext()) {
            list.add(read(in));
          }
          in.endArray();
          return list;

        case BEGIN_OBJECT:
          Map<String, Object> map = new LinkedTreeMap<String, Object>();
          in.beginObject();
          while (in.hasNext()) {
            map.put(in.nextName(), read(in));
          }
          in.endObject();
          return map;

        case STRING:
          return in.nextString();

        case NUMBER:
          // 改写数字处理逻辑
          String numberStr = in.nextString();
          if (numberStr.contains(".") || numberStr.contains("e") || numberStr.contains("E")) {
            return Double.parseDouble(numberStr);
          }

          long number = Long.parseLong(numberStr);
          return number <= Integer.MAX_VALUE ? Integer.parseInt(numberStr) : number;

        case BOOLEAN:
          return in.nextBoolean();

        case NULL:
          in.nextNull();
          return null;

        default:
          throw new IllegalStateException();
      }
    }

    @Override
    public void write(JsonWriter out, Object value) throws IOException {
    }
  }

}
