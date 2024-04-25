package com.nju.urs.service.model.param;

import com.nju.urs.recommendation.model.vo.StudentInfo;
import com.nju.urs.service.model.vo.MajorConditions;
import com.nju.urs.service.model.vo.SchoolConditions;
import lombok.Data;

@Data
public class RecommendParam {
    private Integer pageNo;
    private Integer pageSize;
    private StudentInfo studentInfo;
    private String schoolKeyword;
    private String majorKeyword;
    private SchoolConditions schoolConditions;
    private MajorConditions majorConditions;
}
