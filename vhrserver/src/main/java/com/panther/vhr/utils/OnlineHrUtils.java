package com.panther.vhr.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 11:39
 **/
public class OnlineHrUtils {

    private static List<Integer> onlineHrs = new ArrayList<>();

    public static void addHr(Integer hid) {
        onlineHrs.add(hid);
    }

    public static List getHrs() {
        return onlineHrs;
    }
}
