package com.phongthq.demo.sql.dao;

import com.phongthq.demo.constant.EBookStatus;
import com.phongthq.demo.constant.EUserActionBook;
import com.phongthq.demo.model.BasketBorrowInfo;
import com.phongthq.demo.model.BookInfo;
import com.phongthq.demo.sql.dbo.BookDefindDBO;
import com.phongthq.demo.sql.dbo.OrderDBO;
import com.phongthq.demo.sql.dbo.OrderDetailDBO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 3:48 PM
 */
@Repository
public class OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public boolean userBorrowBook(BasketBorrowInfo basketBorrowInfo){
        boolean result = true;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            //logic
            OrderDBO orderDBO = new OrderDBO(basketBorrowInfo.user.id, EUserActionBook.BORROW.getId());
            session.save(orderDBO);
            for(BookInfo bookInfo : basketBorrowInfo.listBook){
                session.save(new OrderDetailDBO(orderDBO.id, bookInfo.hash));
                session.update(new BookDefindDBO(bookInfo.hash, bookInfo.id, EBookStatus.BORROWED.getId()));
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            if (session != null && session.isOpen() && session.getTransaction() != null)
                session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }
}
