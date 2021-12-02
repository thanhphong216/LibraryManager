package com.phongthq.demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quach Thanh Phong
 * On 12/2/2021 - 10:30 AM
 */
public class OrderInfo {
    public UserInfo user;
    public List<BookInfo> listBook = new ArrayList<>();

    public OrderInfo(UserInfo userInfo) {
        this.user = userInfo;
    }
}
