package com.phongthq.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Quach Thanh Phong
 * On 11/28/2021 - 12:50 PM
 */
@Controller
public class AccountController {

    @GetMapping("/account")
    public String index(){
        return "account/login";
    }
}
