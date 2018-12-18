package com.iboxpay.fastjson;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

  public static boolean isEmpty(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }

  public static boolean isNotEmptry(Collection<?> collection) {
    return !isEmpty(collection);
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  public static boolean isNotEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }
}