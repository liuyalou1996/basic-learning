package com.iboxpay.protostuff;

import java.util.List;
import java.util.Map;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @author: liuyalou
 * @since: 2018年12月14日
 * <p>
 * @Description:
 * 序列化和反序列化工具类
 */
public class ProtostuffUtils {

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
