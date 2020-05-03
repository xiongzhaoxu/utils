package com.oozol.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.function.Function;

//@Component
public class RedisTplUtils {


    private static RedisTemplate<Object, Object> redisTemplate;

    private static RedisTemplate<String, String> redis;

//    @Resource
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        RedisTplUtils.redisTemplate = redisTemplate;
    }

//    @Resource
    public void setRedis_Template(RedisTemplate<String, String> redis) {
        RedisTplUtils.redis = redis;
    }

    /**
     * @param function
     * @param <R>
     * @return
     */
    public static <R> R ops(Function<RedisTemplate<Object, Object>, R> function) {
        return function.apply(redisTemplate);
    }
    public static void cleanRedisCache(String suffix, String[] keys,boolean isLike) {
        if (keys != null && isLike) {
            for (String key : keys) {
                Set<String> stringSet = redis.keys(suffix+"*"+key + "*");
                redis.delete(stringSet);//删除缓

            }
        }else{
            for (String key : keys) {

                redis.delete(suffix+key);//删除缓存

            }
        }
    }
    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public static boolean hasKey(Object key) {
        return redisTemplate.hasKey(key);
    }




}
