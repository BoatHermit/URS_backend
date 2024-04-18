package com.nju.urs.service.model.param;

import com.nju.urs.service.model.vo.MajorConditions;
import lombok.Data;

@Data
public class MajorFilterParam {
    private Integer pageNo;
    private Integer pageSize;
    private String keyword;
    private MajorConditions conditions;
}
