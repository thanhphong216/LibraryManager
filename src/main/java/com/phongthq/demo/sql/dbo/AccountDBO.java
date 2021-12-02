package com.phongthq.demo.sql.dbo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Quach Thanh Phong
 * On 12/2/2021 - 2:50 PM
 */
@Entity
@Table(name = "account")
public class AccountDBO implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;
}
