package com.universe.middleware.redis;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class RedisDataStructure {

  private static Jedis jedis = new Jedis();

  public static void stringsTest() {
    // 使用Strings
    jedis.set("java EE", "轻量级java EE企业应用实战");
    System.out.println("数据结构为Strings：" + jedis.get("java EE"));
  }

  public static void listsTest() {
    // 使用Lists
    jedis.lpush("name", "lyl");
    jedis.lpush("name", "liuqian");
    System.out.println("查看list中的最后一个：" + jedis.lpop("name"));
    System.out.println("查看list中的第一个：" + jedis.rpop("name"));
  }

  public static void setsTest() {
    // 使用Sets，相当于HashSet
    jedis.sadd("nickNames", "nickName1");
    jedis.sadd("nickNames", "nickName2");
    jedis.sadd("nickNames", "nickName1");
    jedis.sadd("nickNames", "nickName3");
    Set<String> nickNames = jedis.smembers("nickNames");
    System.out.println("set中的所有成员为：" + nickNames);
    System.out.println("nickName1是否为nickNames的成员：" + jedis.sismember("nickNames", "nickName1"));
  }

  public static void hashesTest() {
    // 使用Hashes:键与值得映射，相当于hashMap
    jedis.hset("user", "username", "lyl");
    jedis.hset("user", "password", "14786632348");
    Map<String, String> hashMap = jedis.hgetAll("user");
    System.out.println(hashMap);
  }

  public static void sortedSetsTest() {
    // 使用Sorted Sets，成员之间可以进行排序
    Map<String, Double> scores = new HashMap<String, Double>();
    scores.put("Chinese", 98.0);
    scores.put("Math", 94.0);
    scores.put("English", 100.0);
    scores.keySet().forEach(subject -> jedis.zadd("scores", scores.get(subject), subject));
    Set<String> zset = jedis.zrange("scores", 0, 2);
    Set<String> zrevSet = jedis.zrevrange("scores", 0, 2);
    System.out.println("正序排序：" + zset);
    System.out.println("反序排序：" + zrevSet);
    System.out.println("英语的分数是：" + jedis.zscore("scores", "English"));
    // 注意返回的索引从0开始
    System.out.println("从低到高数学在所有科目中的排名是：" + (jedis.zrank("scores", "Math") + 1));
    System.out.println("从高到低英语在所有科目中的排名是：" + (jedis.zrevrank("scores", "English") + 1));
  }

  public static void transactionTest() throws IOException {
    String key = "countries";
    // 事务实例化之前指定
    jedis.watch(key);
    // 使用事务保证原子性和线程安全操作
    Transaction transaction = jedis.multi();
    // 使用key作为监视器，如果key在途中改变了，则前面的执行将会被回滚。
    transaction.sadd(key, "China");
    transaction.sadd(key, "America");
    key = "names";
    transaction.sadd(key, "Russia");
    transaction.sadd(key, "England");
    transaction.exec();// 提交事务
    System.out.println(jedis.smembers(key));
  }

  public static void pipeLineTest() {
    Pipeline pipeLine = jedis.pipelined();
    pipeLine.sadd("userId", "1420140226");
    pipeLine.zadd("ranking", 1, "17752779682");
    pipeLine.zadd("ranking", 2, "13347293021");
    pipeLine.zadd("ranking", 3, "15575547183");
    Response<Boolean> pipeExists = pipeLine.sismember("userId", "1420140226");
    // -1代表第一个到最后一个
    Response<Set<String>> pipeRanking = pipeLine.zrange("ranking", 0, -1);
    // 通过读所有的响应同步管道，这个操作会关闭管道
    pipeLine.sync();
    System.out.println(pipeExists.get());
    System.out.println(pipeRanking.get());
  }

  public static void connectionPoolTest() {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(128);
    config.setMaxIdle(128);
    config.setMinIdle(16);
    config.setTestOnBorrow(true);
    config.setTestOnReturn(true);
    config.setTestWhileIdle(true);
    config.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
    config.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
    config.setNumTestsPerEvictionRun(3);
    config.setBlockWhenExhausted(true);
    JedisPool jedisPool = new JedisPool(config, "localhost");
    try (Jedis newJedis = jedisPool.getResource()) {
      newJedis.hset("books", "1", "疯狂java讲义");
      newJedis.hset("books", "2", "轻量级JavaEE企业应用实践");
      Map<String, String> map = newJedis.hgetAll("books");
      System.out.println(map);
      // 关闭连接池
      jedisPool.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) throws IOException {
    // stringsTest();
    // setsTest();
    // hashesTest();
    // sortedSetsTest();
    // transactionTest();
    // pipeLineTest();
    // connectionPoolTest();
    // 删除所有的key
    // jedis.flushDB();
  }
}
