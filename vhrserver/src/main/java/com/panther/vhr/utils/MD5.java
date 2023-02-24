package com.panther.vhr.utils;

import org.springframework.util.DigestUtils;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 22:06
 **/
public class MD5 {

    public static String HEX(String pwd){
        // 盐值
        String salt = "1a2b3c4d5e";
        return DigestUtils.md5DigestAsHex((pwd+ salt).getBytes());
    }

//    public static void main(String[] args) {
//        System.out.println(HEX("admin"));
//        System.out.println(HEX("123456"));
//        System.out.println(HEX("666666"));
//    }
}
