package com.phongthq.demo.utils;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 4:29 PM
 */
public class Utils {

    public static int getTimestampInSecond(){
        return Math.toIntExact(System.currentTimeMillis() / 1000);
    }
}
