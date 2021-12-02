package com.phongthq.demo.sql.dao;

import com.phongthq.demo.sql.dbo.AccountDBO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Quach Thanh Phong
 * On 12/2/2021 - 2:59 PM
 */
@Repository
public class AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public int getAccountId(String username, String password){
        int id = -1;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            //logic
            String sql = "SELECT * FROM account WHERE username = " + username + " AND password = " + password;
            SQLQuery sqlQuery = session.createSQLQuery(sql).addEntity(AccountDBO.class);
            List<AccountDBO> listResult = sqlQuery.list();

            if(!listResult.isEmpty()) id = listResult.get(0).id;

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

        return id;
    }
}
