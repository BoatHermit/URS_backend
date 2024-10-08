package com.nju.urs.dao.mysql.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admissions")
public class Admission {
    private Integer id;
    private Integer schoolId;
    private Integer majorId;
    private Integer provinceId;
    private Integer subject;
    private Integer rank;
    private Integer score;
    private Integer year;
    private Integer schoolMajorId;
}
