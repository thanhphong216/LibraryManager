package com.phongthq.demo.controller;

import com.phongthq.demo.model.BasketBorrowInfo;
import com.phongthq.demo.model.ResponseData;
import com.phongthq.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        libraryService.removeUserBasketBorrowInfo(httpServletRequest.getSession());
        return "library/index";
    }

    @GetMapping("/bookborrow")
    public String bookBorrowInfo(HttpServletRequest httpServletRequest, Model model){
        BasketBorrowInfo basketInfo = libraryService.getBasketBorrowInfo(httpServletRequest.getSession());

        model.addAttribute("data", basketInfo);
        return "library/bookborrow";
    }

    @ResponseBody
    @PutMapping("/bookborrow/user")
    public ResponseEntity<ResponseData> borrowBookUpdateUser(HttpServletRequest httpServletRequest, Model model,
                                                             Integer userId){
        if(userId == null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(500, "Param invalid!!!"));
        } else {
            ResponseData responseData = libraryService.updateUserBasketBorrowInfo(httpServletRequest.getSession(), userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
    }

    @ResponseBody
    @PutMapping("/bookborrow/book")
    public ResponseEntity<ResponseData> borrowBookUpdateBook(HttpServletRequest httpServletRequest, Model model,
                                                             Integer bookId){
        if(bookId == null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(500, "Param invalid!!!"));
        } else {
            ResponseData responseData = libraryService.updateBookBasketBorrowInfo(httpServletRequest.getSession(), bookId);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
    }

    @ResponseBody
    @DeleteMapping("/bookborrow/book")
    public ResponseEntity<ResponseData> borrowBookDeleteBook(HttpServletRequest httpServletRequest, Model model,
                                                             Integer bookId){
        if(bookId == null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(500, "Param invalid!!!"));
        }else {
            ResponseData responseData = libraryService.deleteBookBasketBorrowInfo(httpServletRequest.getSession(), bookId);
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
    }

    @ResponseBody
    @PostMapping("/bookborrow/book")
    public ResponseEntity<ResponseData> borrowBook(HttpServletRequest httpServletRequest){
        ResponseData responseData = libraryService.borrowBook(httpServletRequest.getSession());
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping("/bookreturn")
    public String bookReturn(Model model){
        return "library/bookreturn";
    }
}
