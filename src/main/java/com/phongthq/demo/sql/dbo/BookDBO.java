package com.phongthq.demo.sql.dbo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 11:42 AM
 */
@Entity
@Table(name = "book")
public class BookDBO implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "img")
    public String image;
}
