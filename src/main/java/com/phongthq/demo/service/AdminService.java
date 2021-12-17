package com.phongthq.demo.service;

import com.phongthq.demo.constant.EUserStatus;
import com.phongthq.demo.model.UserInfo;
import com.phongthq.demo.sql.dao.AccountDAO;
import com.phongthq.demo.sql.dao.RoleDAO;
import com.phongthq.demo.sql.dao.UserDAO;
import com.phongthq.demo.sql.dbo.AccountDBO;
import com.phongthq.demo.sql.dbo.RoleDBO;
import com.phongthq.demo.sql.dbo.UserDBO;
import com.phongthq.demo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Quach Thanh Phong
 * On 12/2/2021 - 2:16 PM
 */
@Service
public class AdminService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;


    public int userLogin(String username, String password){
        int accountId = accountDAO.getAccountId(username, password);
        return accountId;
    }

    public AccountDBO getAccountInfoByAuth(int auth){
        return accountDAO.getAccountDBO(auth);
    }

    public UserInfo getUserInfoByAuth(int auth){
        UserDBO userDBO = userDAO.getUserByAuth(auth);
        RoleDBO roleDBO = roleDAO.getRoleById(userDBO.roleId);

        return new UserInfo(userDBO.id, userDBO.name, EUserStatus.fromId(userDBO.statusId), roleDBO.name, userDBO.country, userDBO.contact);
    }
}
