package com.phongthq.demo.sql.dbo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 11:49 AM
 */
@Entity
@Table(name = "user")
public class UserDBO implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "account_id")
    public int accountId;

    @Column(name = "name")
    public String name;

    @Column(name = "status_id")
    public short statusId;

    @Column(name = "role_id")
    public short roleId;

    @Column(name = "country")
    public String country;

    @Column(name = "contact")
    public String contact;
}
