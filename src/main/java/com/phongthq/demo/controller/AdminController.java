package com.phongthq.demo.controller;

import com.phongthq.demo.model.ResponseData;
import com.phongthq.demo.model.UserInfo;
import com.phongthq.demo.service.AdminService;
import com.phongthq.demo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Quach Thanh Phong
 * On 11/28/2021 - 12:50 PM
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService accountService;


    @GetMapping("/login")
    public String index(){
        return "account/login";
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestParam String username, @RequestParam String password){
        ResponseData responseData = null;
        if(username.isEmpty() || password.isEmpty()){
            responseData = new ResponseData(-1, "Dữ liệu đầu vào không hợp lệ");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }

        int userId = accountService.userLogin(username, SecurityUtils.hashMD5(password.trim()));
        boolean success = false;
        if(userId > 0){
            UserInfo userInfo = accountService.getUserInfoByAuth(userId);
            if(userInfo != null && userInfo.id > 0){

                success = true;
            }
        }

        if(success){
            responseData = new ResponseData(200, "Đăng nhập thành công");
            responseData.extend = "";
        } else {
            responseData = new ResponseData(-1, "Đăng nhập không thành công");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
