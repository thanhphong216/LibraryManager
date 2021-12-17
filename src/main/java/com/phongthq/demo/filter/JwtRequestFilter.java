package com.phongthq.demo.filter;

import com.phongthq.demo.service.AdminService;
import com.phongthq.demo.sql.dbo.AccountDBO;
import com.phongthq.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * Created by Quach Thanh Phong
 * On 12/16/2021 - 1:47 PM
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final String HEADER_STRING = "Authorization";
    private final String TOKEN_PREFIX = "Bearer";

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminService adminService;


    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String headerToken = request.getHeader(HEADER_STRING);
        String jwt;
        int auth = 0;
        AccountDBO userInfo = null;
        if(headerToken != null && headerToken.startsWith(TOKEN_PREFIX)){
            jwt = headerToken.substring(7);
            auth = jwtUtils.getAuth(jwt);
            userInfo = adminService.getAccountInfoByAuth(auth);
        }

        if(auth > 0 && userInfo != null){
            try{
                UsernamePasswordAuthenticationToken authReq
                        = new UsernamePasswordAuthenticationToken(userInfo.username, userInfo.password);
                Authentication authentication = authenticationManager.authenticate(authReq);

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
                HttpSession session = request.getSession(true);
                session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }
}
