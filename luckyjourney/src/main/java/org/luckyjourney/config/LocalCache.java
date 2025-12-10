package org.luckyjourney.config;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;


public class LocalCache {
    private static Map<String,Object> cache = new ConcurrentHashMap();
    static {
        cache.put( "http://localhost:5378/",true);
        cache.put("http://localhost:3000/", true);
    }

    public static void put(String key,Object val){
        cache.put(key,val);
    }
    // 新增：打印所有缓存内容
    public static Set<String> getAllKeys() {
        return cache.keySet();
    }
    public static Boolean containsKey(String key){
        if (key == null) return false;
        return cache.containsKey(key);
    }

    public static void rem(String key) {
        cache.remove(key);
    }
}
