package com.phongthq.demo.model;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 5:37 PM
 */
public class ResponseData {
    public int responseCode;
    public String description;
    public String extend;

    public ResponseData(int responseCode, String description) {
        this.responseCode = responseCode;
        this.description = description;
    }
}
