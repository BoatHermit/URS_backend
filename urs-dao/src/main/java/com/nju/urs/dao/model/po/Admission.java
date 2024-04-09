package com.nju.urs.dao.model.po;

import lombok.Data;

@Data
public class Admission {
    private int id;
    private int schoolId;
    private int majorId;
    private String province;
    private int rank;
    private int score;
    private int year;
}
