package com.nju.urs.service.model.param;

import com.nju.urs.service.model.vo.SchoolConditions;
import lombok.Data;

@Data
public class SchoolFilterParam {
    private Integer pageNo;
    private Integer pageSize;
    private String keyword;
    private SchoolConditions conditions;
}

