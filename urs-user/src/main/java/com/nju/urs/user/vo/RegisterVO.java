package com.nju.urs.user.vo;

import com.nju.urs.common.utils.MD5;
import com.nju.urs.user.po.UserPO;
import lombok.Data;

@Data
public class RegisterVO {
    private String phone;
    private String pwd;
    private String name;

    public UserPO UserPO(){
        UserPO userPO = new UserPO();
        userPO.setPassword(MD5.encrypt(this.getPwd()));
        userPO.setPhone(this.getPhone());
        return userPO;
    }

}
