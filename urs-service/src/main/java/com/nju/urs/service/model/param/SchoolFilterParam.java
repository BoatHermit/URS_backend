package com.nju.urs.service.model.param;

import lombok.Data;

@Data
public class SchoolFilterParam {
    private Integer pageNo;
    private Integer pageSize;
    private SchoolConditions conditions;
}

