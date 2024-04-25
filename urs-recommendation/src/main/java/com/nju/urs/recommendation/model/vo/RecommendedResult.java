package com.nju.urs.recommendation.model.vo;

import com.nju.urs.dao.mongo.model.vo.SimpleSchool;
import com.nju.urs.dao.mysql.model.vo.SimpleAdmission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RecommendedResult implements Serializable {
    private Integer schoolId;
    private String schoolCode;
    private SimpleSchool schoolInfo;
    private Integer majorId;
    private String majorCode;
    private String majorName;
    private Double admissionProbability;
    private List<SimpleAdmission> admissions;
}
