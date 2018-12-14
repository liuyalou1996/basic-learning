package com.iboxpay.protostuff;

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
}
