package com.phongthq.demo.controller;

import com.phongthq.demo.model.BasketBorrowInfo;
import com.phongthq.demo.model.OrderInfo;
import com.phongthq.demo.model.ResponseData;
import com.phongthq.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 9:00 AM
 */
@Controller
public class LibraryController {

    @Autowired
    private LibraryService libraryService;


    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        libraryService.removeUserBasketBorrowInfo(httpServletRequest.getSession());
        return "library/index";
    }

    @GetMapping("/bookborrow")
    public String bookBorrowInfo(HttpServletRequest httpServletRequest, Model model){
        BasketBorrowInfo basketInfo = libraryService.getBasketBorrowInfo(httpServletRequest.getSession());

        model.addAttribute("data", basketInfo);
        return "library/bookborrow";
    }

    @PutMapping("/bookborrow/user")
    public ResponseEntity<ResponseData> borrowBookUpdateUser(HttpServletRequest httpServletRequest,
                                                             Integer userId){
        ResponseData responseData = null;
        if(userId == null){
            responseData = new ResponseData(-1, "Param invalid!!!");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        } else {
            responseData = libraryService.updateUserBasketBorrowInfo(httpServletRequest.getSession(), userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
    }

    @PutMapping("/bookborrow/book")
    public ResponseEntity<ResponseData> borrowBookUpdateBook(HttpServletRequest httpServletRequest,
                                                             Integer bookId){
        ResponseData responseData = null;
        if(bookId == null){
            responseData = new ResponseData(-1, "Param invalid!!!");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        } else {
            responseData = libraryService.updateBookBasketBorrowInfo(httpServletRequest.getSession(), bookId);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
    }

    @DeleteMapping("/bookborrow/book")
    public ResponseEntity<ResponseData> borrowBookDeleteBook(HttpServletRequest httpServletRequest,
                                                             Integer bookId){
        ResponseData responseData = null;
        if(bookId == null){
            responseData = new ResponseData(-1, "Param invalid!!!");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }else {
            responseData = libraryService.deleteBookBasketBorrowInfo(httpServletRequest.getSession(), bookId);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
    }

    @PostMapping("/bookborrow/book")
    public ResponseEntity<ResponseData> borrowBook(HttpServletRequest httpServletRequest){
        ResponseData responseData = libraryService.borrowBook(httpServletRequest.getSession());
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping("/bookborrow/book")
    public ResponseEntity<ResponseData> checkBorrowBook(Integer bookId){
        ResponseData responseData = null;
        if(bookId == null){
            responseData = new ResponseData(-1, "Param invalid!!!");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }else {
            responseData = libraryService.isBorrowedBook(bookId);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
    }

    @GetMapping("/bookreturn")
    public String bookReturn(Model model){
        return "library/bookreturn";
    }

    @PostMapping("/bookreturn/book")
    public ResponseEntity<ResponseData> returnBook(Integer bookId){
        ResponseData responseData = null;
        if(bookId == null){
            responseData = new ResponseData(-1, "Param invalid!!!");
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }else {
            responseData = libraryService.returnBook(bookId);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
    }

    @GetMapping("/order/{id}")
    public String orderInfo(Model model, @PathVariable(name = "id", required = true) Integer orderId){
        OrderInfo orderInfo = libraryService.getOrderInfo(orderId);
        if(orderInfo == null) return "/error";

        model.addAttribute("data", orderInfo);
        return "library/order";
    }
}
