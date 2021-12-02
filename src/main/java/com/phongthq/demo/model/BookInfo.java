package com.phongthq.demo.model;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 3:16 PM
 */
public class BookInfo {
    public int hash;
    public int id;
    public String name;
    public int count;
    public String img;

    public BookInfo(int hash, int id, String name, String img) {
        this.hash = hash;
        this.id = id;
        this.name = name;
        this.count = 1;
        this.img = img;
    }
}
