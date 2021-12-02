package com.phongthq.demo.service;

import com.phongthq.demo.constant.EBookStatus;
import com.phongthq.demo.constant.EUserStatus;
import com.phongthq.demo.model.*;
import com.phongthq.demo.sql.dao.BookDAO;
import com.phongthq.demo.sql.dao.OrderDAO;
import com.phongthq.demo.sql.dao.RoleDAO;
import com.phongthq.demo.sql.dao.UserDAO;
import com.phongthq.demo.sql.dbo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Quach Thanh Phong
 * On 11/29/2021 - 2:39 PM
 */
@Service
public class LibraryService {
    private final static String BASKET = "basket";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private OrderDAO orderDAO;


    /**
     * Lay thong tin gio muon sach trong session
     * @param session
     * @return
     */
    public BasketBorrowInfo getBasketBorrowInfo(HttpSession session){
        BasketBorrowInfo basketInfo = (BasketBorrowInfo) session.getAttribute(BASKET);
        if(basketInfo == null){
            basketInfo = new BasketBorrowInfo();
            session.setAttribute(BASKET, basketInfo);
        }

        return basketInfo;
    }

    /**
     * Xoa thong tin gio muon sach khoi session
     * @param session
     */
    public void removeUserBasketBorrowInfo(HttpSession session){
        session.removeAttribute(BASKET);
    }

    /**
     * Update thong tin user gio muon sach trong session
     * @param session
     * @param userId
     * @return
     */
    public ResponseData updateUserBasketBorrowInfo(HttpSession session, int userId){
        UserDBO userDBO = userDAO.getUserById(userId);
        if(userDBO == null){
            return new ResponseData(403, "User not found!!!");
        }

        RoleDBO roleDBO = roleDAO.getRoleById(userDBO.roleId);
        if(roleDBO == null){
            return new ResponseData(403, "Error system!!!");
        }

        BasketBorrowInfo basketInfo = getBasketBorrowInfo(session);
        basketInfo.user = new UserInfo(userDBO.id, userDBO.name, EUserStatus.fromId(userDBO.statusId), roleDBO.name, userDBO.country, userDBO.contact);

        return new ResponseData(200, "");
    }

    /**
     * Update thong tin book gio muon sach trong session
     * @param session
     * @param bookHash
     * @return
     */
    public ResponseData updateBookBasketBorrowInfo(HttpSession session, int bookHash){
        BasketBorrowInfo basketInfo = getBasketBorrowInfo(session);
        for(BookInfo bookInfo : basketInfo.listBook){
            if(bookInfo.hash == bookHash){
                return new ResponseData(403, "Book have been add to basket.");
            }
        }

        BookDefindDBO bookDefindDBO = bookDAO.getBookDefindById(bookHash);
        if(bookDefindDBO == null){
            return new ResponseData(403, "Book not defind! Can't borrow this book");
        }
        switch (EBookStatus.fromId(bookDefindDBO.statusId)){
            case FREE:

                break;
            case BORROWED:
                return new ResponseData(403, "Book have been borrowed");
        }

        BookDBO bookDBO = bookDAO.getBookById(bookDefindDBO.bookId);
        if(bookDBO == null){
            return new ResponseData(403, "Error system!!!");
        }

        basketInfo.listBook.add(new BookInfo(bookDefindDBO.hash, bookDBO.id, bookDBO.name, bookDBO.image));
        return new ResponseData(200, "");
    }

    /**
     * Xoa thong tin book gio muon sach trong session
     * @param session
     * @param bookHash
     * @return
     */
    public ResponseData deleteBookBasketBorrowInfo(HttpSession session, int bookHash){
        BasketBorrowInfo basketInfo = getBasketBorrowInfo(session);
        for(BookInfo bookInfo : basketInfo.listBook){
            if(bookInfo.hash == bookHash){
                basketInfo.listBook.remove(bookInfo);
                return new ResponseData(200, "");
            }
        }

        return new ResponseData(403, "Book haven't been add to basket.");
    }

    /**
     * Muon sach
     * @param session
     * @return
     */
    public ResponseData borrowBook(HttpSession session){
        BasketBorrowInfo basketInfo = getBasketBorrowInfo(session);
        //Kiem tra user input
        if(basketInfo.user == null){
            return new ResponseData(-2, "User haven't been accept");
        }
        //Kiem tra book input
        if(basketInfo.listBook.isEmpty()){
            return new ResponseData(-2, "Book haven't been accept");
        }

        //Save db
        boolean result = orderDAO.userBorrowBook(basketInfo);
//        for(BookInfo bookInfo : basketInfo.listBook){
//            if(EBookStatus.fromId(bookDAO.getBookDefindById(bookInfo.hash).statusId).getId() == EBookStatus.BORROWED.getId()){
//                return new ResponseData(-2, "Book haven't been borrowed");
//            }
//        }
        if(result){
            return new ResponseData(200, "");
        }else {
            return new ResponseData(-2, "Error system.");
        }
    }

    /**
     * Kiem tra sach dc muon ko
     * @param bookHash
     * @return
     */
    public ResponseData isBorrowedBook(int bookHash){
        BookDefindDBO bookDefindDBO = bookDAO.getBookDefindById(bookHash);
        if(bookDefindDBO == null){
            return new ResponseData(-2, "");
        }
        switch (EBookStatus.fromId(bookDefindDBO.statusId)){
            case FREE:
                return new ResponseData(-2, "Book haven't been borrowed");
            case BORROWED:
                return new ResponseData(200, "");
        }
        return new ResponseData(-2, "Error system.");
    }

    /**
     * Kiem tra sach dc muon ko
     * @param bookHash
     * @return
     */
    public ResponseData returnBook(int bookHash){
        BookDefindDBO bookDefindDBO = bookDAO.getBookDefindById(bookHash);
        if(bookDefindDBO == null){
            return new ResponseBookReturn(-2, "Book not defind!");
        }
        switch (EBookStatus.fromId(bookDefindDBO.statusId)){
            case FREE:
                return new ResponseBookReturn(-2, "Book haven't been borrowed");
            case BORROWED:
                break;
        }

        Integer result = orderDAO.userReturnBook(bookHash);
        if(result == null) return new ResponseBookReturn(-2, "Error system.");

        return new ResponseBookReturn(result);
    }

    /**
     * Lay thong tin order
     * @param orderId
     * @return
     */
    public OrderInfo getOrderInfo(int orderId){
        OrderDBO orderDBO = orderDAO.getOrderById(orderId);
        if(orderDBO == null) return null;

        List<OrderDetailDBO> orderDetailDBO = orderDAO.getOrderDetailById(orderId);
        UserDBO userDBO = userDAO.getUserById(orderDBO.userId);
        RoleDBO roleDBO = roleDAO.getRoleById(userDBO.roleId);
        List<BookDBO> listBookDBO = bookDAO.getBookByHash(
                orderDetailDBO.stream().map(obj -> obj.bookHash).collect(Collectors.toList()));

        OrderInfo orderInfo = new OrderInfo(new UserInfo(userDBO.id, userDBO.name, EUserStatus.fromId(userDBO.statusId), roleDBO.name, userDBO.country, userDBO.contact));
        for(BookDBO bookDBO : listBookDBO){
            orderInfo.listBook.add(new BookInfo(-1, bookDBO.id, bookDBO.name, bookDBO.image));
        }

        return orderInfo;
    }
}
