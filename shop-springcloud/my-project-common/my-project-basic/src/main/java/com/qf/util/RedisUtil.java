package com.qf.util;

public class RedisUtil {

    public static String getRedisKey(String pre,String key){
        return new StringBuilder().append(pre).append(key).toString();
    }

}
