package com.dzk.common.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dzk
 * @date 2020/8/2 18:56
 * @description
 */
public class ForceQuitMap {
    private static Map<String,String> forceUserMap=new ConcurrentHashMap<String, String>();

    public static Map<String, String> getForceUserMap() {
        return forceUserMap;
    }
    public static void setForceUserMap(Map<String, String> forceUserMap) {
        ForceQuitMap.forceUserMap = forceUserMap;
    }
}
