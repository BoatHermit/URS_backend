package com.nju.urs.service.service;

import com.nju.urs.common.utils.Result;
import com.nju.urs.user.vo.InsertInfoVO;
import com.nju.urs.user.vo.LoginVO;
import com.nju.urs.user.vo.RegisterVO;
import com.nju.urs.user.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    String[] login(LoginVO loginVO);
    void register(RegisterVO registerVO);
    UserVO getUserInfo(String phoneNumber);
    UserVO update(UserVO userVO);
}
