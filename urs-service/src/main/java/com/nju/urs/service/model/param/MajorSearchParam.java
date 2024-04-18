package com.nju.urs.service.model.param;

import lombok.Data;

@Data
public class MajorSearchParam {
    private Integer pageNo;
    private Integer pageSize;
    private String keyword;
}
