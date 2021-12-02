package com.phongthq.demo.sql.dao;

import com.phongthq.demo.constant.EBookStatus;
import com.phongthq.demo.constant.EUserActionBook;
import com.phongthq.demo.model.BasketBorrowInfo;
import com.phongthq.demo.model.BookInfo;
import com.phongthq.demo.sql.dbo.BookDefindDBO;
import com.phongthq.demo.sql.dbo.OrderDBO;
import com.phongthq.demo.sql.dbo.OrderDetailDBO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Quach Thanh Phong
 * On 11/30/2021 - 3:48 PM
 */
@Repository
public class OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public OrderDBO getOrderById(int id){
        OrderDBO orderDBO = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            //logic
            orderDBO = session.get(OrderDBO.class, id);

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

        return orderDBO;
    }

    public List<OrderDetailDBO> getOrderDetailById(int id){
        List<OrderDetailDBO> listResult = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            //logic
            String sql = "SELECT * FROM orders_detail WHERE order_id = " + id;
            SQLQuery sqlQuery = session.createSQLQuery(sql).addEntity(OrderDetailDBO.class);
            listResult = sqlQuery.list();

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

        return listResult;
    }

    public boolean userBorrowBook(BasketBorrowInfo basketBorrowInfo){
        boolean result = true;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            //logic
            OrderDBO orderDBO = new OrderDBO(basketBorrowInfo.user.id, EUserActionBook.BORROW.getId());
            session.save(orderDBO);
            BookDefindDBO bookDefindDBO = null;
            for(BookInfo bookInfo : basketBorrowInfo.listBook){
                session.save(new OrderDetailDBO(orderDBO.id, bookInfo.hash));
                bookDefindDBO = session.get(BookDefindDBO.class, bookInfo.hash);
                bookDefindDBO.statusId = EBookStatus.BORROWED.getId();
                bookDefindDBO.userId = basketBorrowInfo.user.id;
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

    /**
     * Return book borrow
     * @param bookHash
     * @return order id
     */
    public Integer userReturnBook(int bookHash){
        Integer result = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            //logic
            BookDefindDBO bookDefindDBO = session.get(BookDefindDBO.class, bookHash);
            OrderDBO orderDBO = new OrderDBO(bookDefindDBO.userId, EUserActionBook.RETURN.getId());
            session.save(orderDBO);
            session.save(new OrderDetailDBO(orderDBO.id, bookHash));
            bookDefindDBO.statusId = EBookStatus.FREE.getId();
            bookDefindDBO.userId = null;

            result = orderDBO.id;

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

        return result;
    }
}
