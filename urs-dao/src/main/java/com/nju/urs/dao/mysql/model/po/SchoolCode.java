package com.nju.urs.dao.mysql.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("schools")
public class SchoolCode {
    private String id;
    private String name;
}
