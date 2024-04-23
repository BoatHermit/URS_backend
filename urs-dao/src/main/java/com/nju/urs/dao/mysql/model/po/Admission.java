package com.nju.urs.dao.mysql.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admissions")
public class Admission {
    private int id;
    private String schoolId;
    private String majorId;
    private String province;
    private int rank;
    private int score;
    private String year;
}
