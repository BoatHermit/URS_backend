package com.nju.urs.service.service.impl;

import com.nju.urs.common.utils.MD5;
import com.nju.urs.user.po.UserPO;
import com.nju.urs.user.vo.RegisterVO;
import com.nju.urs.user.vo.UserVO;
import org.springframework.beans.BeanUtils;

public class UserSession {
    public static UserPO UserPO(RegisterVO registerVO){
        UserPO userPo = new UserPO();
        userPo.setName(registerVO.getName());
        userPo.setPassword(MD5.encrypt(registerVO.getPwd()));
        userPo.setPhone(registerVO.getPhone());
        return userPo;
    }

    public static UserVO UserVO(UserPO userPo){
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPo,userVO);
        userVO.setPassword(userPo.getPassword());

        return userVO;
    }

    public static UserPO UserPO(UserVO userVO){
        UserPO user = new UserPO();
        BeanUtils.copyProperties(userVO,user);
        user.setPlace(userVO.getPlace());
        user.setPassword(userVO.getPassword());
        return user;
    }
}
