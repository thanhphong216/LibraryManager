package com.phongthq.demo.model;

import com.phongthq.demo.constant.EUserStatus;
import com.phongthq.demo.sql.dbo.BookDBO;
import com.phongthq.demo.sql.dbo.BookDefindDBO;
import com.phongthq.demo.sql.dbo.RoleDBO;
import com.phongthq.demo.sql.dbo.UserDBO;

/**
 * Created by Quach Thanh Phong
 * On 12/2/2021 - 9:36 AM
 */
public class ResponseBookReturn extends ResponseData {

    public int orderId;


    public ResponseBookReturn(int responseCode, String description) {
        super(responseCode, description);
    }

    public ResponseBookReturn(int orderId) {
        super(200, "");
        this.orderId = orderId;
    }
}
