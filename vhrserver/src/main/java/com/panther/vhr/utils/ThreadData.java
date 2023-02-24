package com.panther.vhr.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 14:34
 **/
public class ThreadData {


//    public static ThreadLocal<Integer> thread = new ThreadLocal<>();
    public static Map<String,Object> data = new HashMap<>();

    public static void setThreadData(String name ,Object obj){
       data.put(name,obj);
    }

    public static Object getThreadData(String name){
        return data.get(name);
    }

}
