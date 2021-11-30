package com.phongthq.demo.constant;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 2:25 PM
 */
public enum EUserStatus {
    ACTIVE(0),
    DISABLE(1),
    LOCK(2);

    private int id;

    EUserStatus(int id) {
        this.id = id;
    }

    public static EUserStatus fromId(int id){
        for(EUserStatus status : EUserStatus.values()){
            if(status.id == id) return status;
        }
        return null;
    }
}
