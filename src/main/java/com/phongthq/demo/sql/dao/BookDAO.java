package com.phongthq.demo.sql.dao;

import com.phongthq.demo.sql.dbo.BookDBO;
import com.phongthq.demo.sql.dbo.BookDefindDBO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 11:42 AM
 */
@Repository
public class BookDAO {

    @Autowired
    private SessionFactory sessionFactory;


    /**
     *
     * @param id
     * @return
     */
    public BookDBO getBookById(int id){
        BookDBO bookDBO = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            //logic
            bookDBO = session.get(BookDBO.class, id);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null && session.isOpen() && session.getTransaction() != null)
                session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return bookDBO;
    }

    /**
     *
     * @param id
     * @return
     */
    public BookDefindDBO getBookDefindById(int id){
        BookDefindDBO bookDefindDBO = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            //logic
            bookDefindDBO = session.get(BookDefindDBO.class, id);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null && session.isOpen() && session.getTransaction() != null)
                session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return bookDefindDBO;
    }
}
