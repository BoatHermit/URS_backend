package com.nju.urs.recommendation.model.vo;

import com.nju.urs.dao.mongo.model.vo.SimpleSchool;
import lombok.Data;

import java.util.List;

@Data
public class RecommendedResult {
    private String schoolCode;
    private SimpleSchool schoolInfo;
    private String majorCode;
    private String majorName;
    private Double admissionProbability;
    private List<SimpleAdmission> admissions;
}
