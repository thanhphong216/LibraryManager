package com.phongthq.demo.sql.dbo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 5:10 PM
 */
@Entity
@Table(name = "role")
public class RoleDBO implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    public String name;
}
