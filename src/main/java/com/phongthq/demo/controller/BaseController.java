package com.phongthq.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by Quach Thanh Phong
 * On 12/6/2021 - 9:16 AM
 */
public abstract class BaseController {

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public String notPermision(){
        return "/403";
    }

    public boolean checkPermision(){

        return true;
    }
}
