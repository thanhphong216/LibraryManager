package com.phongthq.demo.sql.dbo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 3:45 PM
 */
@Entity
@Table(name = "orders_detail")
public class OrderDetailDBO implements Serializable {
    @Id
    @Column(name = "order_id")
    public int orderId;

    @Id
    @Column(name = "book_hash")
    public int bookHash;


    public OrderDetailDBO() { }
    public OrderDetailDBO(int orderId, int bookHash) {
        this.orderId = orderId;
        this.bookHash = bookHash;
    }
}
