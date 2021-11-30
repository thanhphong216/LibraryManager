package com.phongthq.demo.sql.dbo;

import com.phongthq.demo.utils.Utils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 3:43 PM
 */
@Entity
@Table(name = "orders")
public class OrderDBO implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "user_id")
    public int userId;

    @Column(name = "action")
    public short action;

    @Column(name = "time")
    public int timestamp;


    public OrderDBO() { }
    public OrderDBO(int userId, short action) {
        this.userId = userId;
        this.action = action;
        this.timestamp = Utils.getTimestampInSecond();
    }
}
