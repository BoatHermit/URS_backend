package com.nju.urs.user.po;

import lombok.Builder;
import lombok.Data;

@Data
//@Builder
public class UserPO {
    //主键
    String id;

    String phone;
    String password;
    String place;

}
