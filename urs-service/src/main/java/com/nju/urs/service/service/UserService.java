package com.nju.urs.service.service;

import com.nju.urs.common.utils.Result;
import com.nju.urs.user.vo.InsertInfoVO;
import com.nju.urs.user.vo.LoginVO;
import com.nju.urs.user.vo.RegisterVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    Result login(LoginVO loginVO);
    Result register(RegisterVO registerVO);
    Result getUserInfo(HttpServletRequest request);
    Result update(LoginVO loginVO);
    Result insertInfo(String token, InsertInfoVO insertInfoVO);
}
