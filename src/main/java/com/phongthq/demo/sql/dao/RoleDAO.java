package com.phongthq.demo.sql.dao;

import com.phongthq.demo.sql.dbo.RoleDBO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 5:26 PM
 */
@Repository
public class RoleDAO {

    @Autowired
    private SessionFactory sessionFactory;


    /**
     *
     * @param id
     * @return
     */
    public RoleDBO getRoleById(int id){
        RoleDBO roleDBO = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            //logic
            roleDBO = session.get(RoleDBO.class, id);

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

        return roleDBO;
    }
}
