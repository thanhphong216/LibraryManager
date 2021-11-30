package com.phongthq.demo.constant;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 4:49 PM
 */
public enum  EBookStatus {
    FREE(0),
    BORROWED(1);

    private int id;

    public int getId() {
        return id;
    }

    EBookStatus(int id) {
        this.id = id;
    }

    public static EBookStatus fromId(int id){
        for(EBookStatus status : EBookStatus.values()){
            if(status.id == id) return status;
        }
        return null;
    }
}
