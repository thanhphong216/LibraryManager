package com.phongthq.demo.sql.dbo;

import javax.persistence.*;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 11:47 AM
 */
@Entity
@Table(name = "book_ls")
public class BookDefindDBO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int hash;

    @Column(name = "book_id")
    public int bookId;

    @Column(name = "status_id")
    public int statusId;


    public BookDefindDBO() {}
    public BookDefindDBO(int hash, int bookId, int statusId) {
        this.hash = hash;
        this.bookId = bookId;
        this.statusId = statusId;
    }
}
