package com.nju.urs.user.vo;

import lombok.Data;

@Data
public class UserVO {
    private String id;
    private String name;
    private String phone;
    private String password;
    private String place;
    private int score;
    private int rank;
    private String subjects;

}
