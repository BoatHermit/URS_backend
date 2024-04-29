package com.nju.urs.service.service;

import com.nju.urs.user.vo.LoginVO;
import com.nju.urs.user.vo.RegisterVO;
import com.nju.urs.user.vo.UserVO;

public interface UserService {
    String[] login(LoginVO loginVO);
    int register(RegisterVO registerVO);
    UserVO getUserInfo(String phoneNumber);
    UserVO update(UserVO userVO);
}
