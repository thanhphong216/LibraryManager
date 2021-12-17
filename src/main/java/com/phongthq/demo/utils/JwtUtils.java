package com.phongthq.demo.utils;

import com.phongthq.demo.service.AdminService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Quach Thanh Phong
 * On 12/16/2021 - 11:49 AM
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret = "test";

    @Value("${jwt.duration}")
    private long duration = 86400;

    @Autowired
    private AdminService adminService;

    public String generateToken(String username, String password){
        int auth = adminService.userLogin(username, password);
        if(auth > 0){
            return Jwts.builder()
                    .setSubject(String.valueOf(auth))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + duration * 1000))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        } else {
            return "";
        }
    }

    public int getAuth(String token){
        try {
            return Integer.parseInt(
                    Jwts.parser()
                            .setSigningKey(secret)
                            .parseClaimsJws(token).getBody()
                            .getSubject()
            );
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }
}
