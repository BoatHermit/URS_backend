package com.nju.urs.web;

import com.nju.urs.common.utils.MD5;
import com.nju.urs.service.service.UserService;
import com.nju.urs.user.vo.LoginVO;
import com.nju.urs.user.vo.RegisterVO;
import com.nju.urs.user.vo.UserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrsWebApplication.class)
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    public void register(){
        RegisterVO registerVO=new RegisterVO();
        registerVO.setPhone("13844526687");
        registerVO.setPwd("13579@");
        registerVO.setName("Test");

        userService.register(registerVO);

        try{
            userService.register(registerVO);
        }
        catch (Exception e){
        }
    }
    @Test
    public void login1() {
        //密码正确
        LoginVO loginVO = new LoginVO();
        loginVO.setPhone("13844526687");
        loginVO.setPwd("123");
        String[] checkRes = userService.login(loginVO);

        Assert.assertEquals("123", checkRes[1]);
    }

    @Test
    public void login2() {
        //密码不正确
        LoginVO loginVO = new LoginVO();
        loginVO.setPhone("13844526687");
        loginVO.setPwd("123456");
        String[] checkRes = userService.login(loginVO);

        Assert.assertEquals("123", checkRes[1]);
    }

    @Test
    public void login3() {
        //用户不存在
        LoginVO loginVO = new LoginVO();
        loginVO.setPhone("13844526689");
        loginVO.setPwd("123");
        String[] checkRes = userService.login(loginVO);

        Assert.assertEquals(null, checkRes);
    }

    //更新用户数据
    @Test
    public void update(){
        UserVO userVO=new UserVO();
        userVO.setName("test");
        userVO.setPassword("13844526687");
        userVO.setPlace("江苏");
        userVO.setRank(1000);
        userVO.setScore(655);
        userService.update(userVO);

        UserVO userVO1=userService.getUserInfo("13844526689");
        userVO.setPassword(MD5.encrypt(userVO1.getPassword()));

        Assert.assertEquals(userVO1,userVO);
    }

    //获取用户数据
    @Test
    public void getInfo(){
        UserVO userVO=userService.getUserInfo("13844526689");
        System.out.println(userVO.getPhone());
        Assert.assertEquals("Test",userVO.getName());
        Assert.assertEquals("江苏",userVO.getPlace());
    }




}
