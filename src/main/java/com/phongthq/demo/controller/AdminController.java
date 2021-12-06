package com.phongthq.demo.controller;

import com.phongthq.demo.model.ResponseData;
import com.phongthq.demo.model.UserInfo;
import com.phongthq.demo.service.AdminService;
import com.phongthq.demo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * Created by Quach Thanh Phong
 * On 11/28/2021 - 12:50 PM
 */
@Controller
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminService accountService;


    @GetMapping("/login")
    public String index(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "account/login";
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(HttpServletRequest request,
                                              @RequestParam String username,
                                              @RequestParam String password){
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

                try{
                    UsernamePasswordAuthenticationToken authReq
                            = new UsernamePasswordAuthenticationToken(username, password);
                    Authentication auth = authenticationManager.authenticate(authReq);

                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(auth);
                    HttpSession session = request.getSession(true);
                    session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
                }catch (Exception e){
                    e.printStackTrace();
                }

                success = true;
            }
        }

        if(success){
            responseData = new ResponseData(200, "Đăng nhập thành công");
            responseData.extend = "/";
        } else {
            responseData = new ResponseData(-1, "Đăng nhập không thành công");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "direct:/login";
    }
}
