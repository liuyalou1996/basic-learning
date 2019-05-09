package com.universe.thirdparty.protostuff;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.universe.thirdparty.fastjson.CollectionUtils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 序列化和反序列化工具类
 */
public class ProtostuffUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProtostuffUtils.class);

  @SuppressWarnings("unchecked")
  public static <T> byte[] serialize(T obj) {
    Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
    LinkedBuffer buffer = LinkedBuffer.allocate();
    byte[] bytes = null;
    try {
      bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
    } finally {
      buffer.clear();
    }

    return bytes;
  }

  public static <T> T deserialize(byte[] data, Class<T> clazz) {
    Schema<T> schema = RuntimeSchema.getSchema(clazz);
    T message = schema.newMessage();
    ProtostuffIOUtil.mergeFrom(data, message, schema);
    return message;
  }

  @SuppressWarnings("unchecked")
  public static <T> byte[] serializeList(List<T> list) {
    byte[] bytes = null;
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }

    Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(list.get(0).getClass());
    LinkedBuffer buffer = LinkedBuffer.allocate(1024);
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ProtostuffIOUtil.writeListTo(baos, list, schema, buffer);
      bytes = baos.toByteArray();
    } catch (Exception e) {
      LOGGER.error("序列化对象时发生异常:{}", e.getMessage(), e);
    } finally {
      buffer.clear();
    }

    return bytes;
  }

  /**
   * 反序列化list
   * @param bytes
   * @param clazz 传入的运行时对象不能是集合的运行时对象，否则反序列化时会失败，集合的序列化和反序列化使用CollectionWrapper包装
   * @return
   */
  public static <T> List<T> deserializeList(byte[] bytes, Class<T> clazz) {
    Schema<T> schema = RuntimeSchema.getSchema(clazz);
    List<T> list = Collections.emptyList();
    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      list = ProtostuffIOUtil.parseListFrom(bais, schema);
    } catch (IOException e) {
      LOGGER.error("反序列化对象时发生异常:{}" + e.getMessage(), e);
    }

    return list;
  }

  /**
   * 包装集合的POJO，由于Protostuff获取模式时指定的运行时对象不能是接口或者抽象类的运行时对象，因此序列化集合时需要通过POJO来包装
   * <p>
   * @author liuyalou
   * @since 2018年12月15日
   * <p>
   */
  public static class CollectionWrapper<T> {

    private List<T> list;

    private Map<String, T> map;

    public List<T> getList() {
      return list;
    }

    public void setList(List<T> list) {
      this.list = list;
    }

    public Map<String, T> getMap() {
      return map;
    }

    public void setMap(Map<String, T> map) {
      this.map = map;
    }

    @Override
    public String toString() {
      return "CollectionWrapper [list=" + list + ", map=" + map + "]";
    }
  }
}
