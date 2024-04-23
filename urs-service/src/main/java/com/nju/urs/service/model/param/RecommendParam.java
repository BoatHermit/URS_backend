package com.nju.urs.service.model.param;

import com.nju.urs.recommendation.model.vo.StudentInfo;
import lombok.Data;

@Data
public class RecommendParam {
    private Integer pageNo;
    private Integer pageSize;
    private StudentInfo studentInfo;
}
