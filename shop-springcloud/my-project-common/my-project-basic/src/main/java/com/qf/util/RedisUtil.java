package com.qf.util;


public class RedisUtil {
    /**
     * 封装Redis键
     */
    public static String getRedisKey(String pre,String key){
        //线程不安全的，但性能高！
        StringBuilder sb = new StringBuilder();
        sb.append(pre);
        sb.append(key);
        return sb.toString();
    }

}
