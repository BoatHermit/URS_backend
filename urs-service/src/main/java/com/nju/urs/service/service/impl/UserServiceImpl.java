package com.nju.urs.service.service.impl;

import com.nju.urs.common.utils.JwtUtils;
import com.nju.urs.common.utils.MD5;
import com.nju.urs.common.utils.Result;
import com.nju.urs.service.service.UserService;
import com.nju.urs.user.po.UserPO;
import com.nju.urs.user.repository.UserRepository;
import com.nju.urs.user.vo.LoginVO;
import com.nju.urs.user.vo.RegisterVO;
import com.nju.urs.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    //todo
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String[] login(LoginVO loginVO){
        String checkPhone = loginVO.getPhone();
        String pwd = MD5.encrypt(loginVO.getPwd());
        Optional<UserPO> user = userRepository.findByPhoneAndPassword(checkPhone, pwd);
        if(user.isPresent()){
            UserVO userVO = UserSession.UserVO(user.get());
            return getRes(userVO);
        }
        else {
            Optional<UserPO> userCheck = userRepository.findByPhoneAndPassword(checkPhone, pwd);
            if(userCheck.isPresent()){
                UserVO userVO = UserSession.UserVO(userCheck.get());
                return getRes(userVO);
            }
        }
        return null;
    }

    @Override
    public void register(RegisterVO registerVO){
        Optional<UserPO> user = userRepository.findByNameAndPassword(registerVO.getName(), MD5.encrypt(registerVO.getPwd()));
        if (!user.isPresent()) {
            userRepository.insert(UserSession.UserPO(registerVO));
        }
    }

    @Override
    public UserVO getUserInfo(String phoneNumber){
        Optional<UserPO> user = userRepository.findByPhone(phoneNumber);
        return user.map(UserSession::UserVO).orElse(null);
    }

    @Override
    public UserVO update(UserVO userVO){
        UserPO user = UserSession.UserPO(userVO);
        Query query = Query.query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        update.set("phone", user.getPhone());
        update.set("name", user.getName());
        update.set("place", user.getPlace());
        update.set("score", user.getScore());
        update.set("rank", user.getRank());
        update.set("subjects", user.getSubjects());
        mongoTemplate.updateFirst(query,update,"user");
        return UserSession.UserVO(user);
    }

    private static String[] getRes(UserVO userVO) {
        String name = userVO.getName();
        String pwd = userVO.getPassword();
        String token = JwtUtils.getJwtToken(name, pwd);
        String[] res = new String[2];
        res[0] = token;
        res[1] = name;
        return res;
    }

}

