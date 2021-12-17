package com.phongthq.demo.controller;

import com.phongthq.demo.model.ResponseData;
import com.phongthq.demo.model.UserInfo;
import com.phongthq.demo.service.AdminService;
import com.phongthq.demo.utils.JwtUtils;
import com.phongthq.demo.utils.SecurityUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping("/login")
    public String index(){
        return "account/login";
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(HttpServletRequest request,
                                              @RequestParam String username,
                                              @RequestParam String password){
        if(username.isEmpty() || password.isEmpty()){
            ResponseData responseData = new ResponseData(-1, "Dữ liệu đầu vào không hợp lệ");
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

        ResponseData responseData;
        if(success){
            responseData = new ResponseData(200, "Đăng nhập thành công");
            responseData.extend = "/";
        } else {
            responseData = new ResponseData(-1, "Đăng nhập không thành công");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> tokenLogin(HttpServletRequest request,
                                              @RequestParam(required = false) String username,
                                              @RequestParam(required = false) String password){
        if(StringUtils.isEmptyOrWhitespace(username) || StringUtils.isEmptyOrWhitespace(password)){
            ResponseData responseData = new ResponseData(-1, "Dữ liệu đầu vào không hợp lệ");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }

        String token = jwtUtils.generateToken(username, SecurityUtils.hashMD5(password.trim()));
        if(token.isEmpty()){
            ResponseData responseData = new ResponseData(-1, "Dữ liệu đầu vào không hợp lệ");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "direct:/login";
    }

    @GetMapping("")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ResponseData> getListUser(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping
    public ResponseEntity<ResponseData> addListUser(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
