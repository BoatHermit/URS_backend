package com.nju.urs.web.controller;

import com.nju.urs.common.enums.ResultCode;
import com.nju.urs.common.utils.JwtUtils;
import com.nju.urs.common.utils.Result;
import com.nju.urs.service.service.UserService;
import com.nju.urs.user.vo.LoginVO;
import com.nju.urs.user.vo.RegisterVO;
import com.nju.urs.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    //todo
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVO loginVO){
        String[] res=userService.login(loginVO);
        if(res==null){
            return Result.fail(ResultCode.LOGIN_FAIL);
        }else{
            return Result.success();
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterVO registerVO){
        userService.register(registerVO);
        return Result.success();
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        String userPhone=JwtUtils.getCheckPhoneByJwtToken(request);
        UserVO userVO = userService.getUserInfo(userPhone);
        if(userVO==null){
            return Result.fail(ResultCode.FAILED);
        }else{
            return Result.success();
        }

    }
}
