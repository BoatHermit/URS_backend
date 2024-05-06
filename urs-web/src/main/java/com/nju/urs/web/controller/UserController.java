package com.nju.urs.web.controller;

import com.nju.urs.common.annotation.Log;
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
    @Autowired
    private UserService userService;

    @Log(module = "用户", operation = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVO loginVO){
        String[] res=userService.login(loginVO);
        if(res==null){
            return Result.fail(ResultCode.LOGIN_FAIL);
        }else{
            return Result.success(ResultCode.LOGIN_SUCCESS);
        }
    }

    @Log(module = "用户", operation = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVO registerVO){
        int res=userService.register(registerVO);
        if(res==0){
            return Result.success(ResultCode.REGISTER_SUCCESS);
        }else if (res==3){
            return Result.fail(10007,"非法手机号");
        }else{
            return Result.fail(ResultCode.ACCOUNT_EXIST);
        }

    }

    @Log(module = "用户", operation = "获取用户信息")
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

    @Log(module = "用户", operation = "更新用户信息")
    @PostMapping("/update")
    public Result update(@RequestBody UserVO userVO) {
        UserVO newUser = userService.update(userVO);
        return Result.success();
    }


}
