package com.phongthq.demo.constant;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 4:25 PM
 */
public enum  EUserActionBook {
    BORROW(0),
    RETURN(1),
    ADD(2),
    REMOVE(3);

    private short id;

    public short getId() {
        return id;
    }

    EUserActionBook(int id) {
        this.id = (short) id;
    }

    public EUserActionBook fromId(int id){
        for(EUserActionBook action : EUserActionBook.values()){
            if(action.id == id) return action;
        }
        return null;
    }
}
