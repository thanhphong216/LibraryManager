package com.phongthq.demo.model;

import com.phongthq.demo.constant.EUserStatus;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 3:16 PM
 */
public class UserInfo {
    public int id;
    public String name;
    public String status;
    public String role;
    public String country;
    public String contact;

    public UserInfo(int id, String name, EUserStatus status, String role, String country, String contact) {
        this.id = id;
        this.name = name;
        this.status = status.name();
        this.role = role;
        this.country = country;
        this.contact = contact;
    }
}
