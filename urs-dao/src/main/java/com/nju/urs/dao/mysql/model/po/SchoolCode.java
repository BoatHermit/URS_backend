package com.nju.urs.dao.mysql.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("school_code")
public class SchoolCode {
    private Integer id;
    private String name;
    private String code;
    private Integer provinceId;
}
