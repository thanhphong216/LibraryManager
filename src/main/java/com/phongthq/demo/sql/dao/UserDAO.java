package com.phongthq.demo.sql.dao;

import com.phongthq.demo.sql.dbo.UserDBO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 2:02 PM
 */
@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;


    /**
     *
     * @param id
     * @return
     */
    public UserDBO getUserById(int id){
        UserDBO userDBO = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            //logic
            userDBO = session.get(UserDBO.class, id);

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

        return userDBO;
    }

    /**
     *
     * @param auth
     * @return
     */
    public UserDBO getUserByAuth(int auth){
        UserDBO userDBO = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            //logic
            //logic
            String sql = "SELECT * FROM user WHERE account_id = " + auth;
            SQLQuery sqlQuery = session.createSQLQuery(sql).addEntity(UserDBO.class);
            List<UserDBO> listResult = sqlQuery.list();

            if(!listResult.isEmpty()) userDBO = listResult.get(0);

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

        return userDBO;
    }
}
