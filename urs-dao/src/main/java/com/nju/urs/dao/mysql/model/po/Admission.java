package com.nju.urs.dao.mysql.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admissions")
public class Admission {
    private int id;
    private int schoolId;
    private int majorId;
    private String province;
    private int rank;
    private int score;
    private int year;
}
