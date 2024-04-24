package com.nju.urs.recommendation.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class MajorAdmission {
    String majorCode;
    String majorName;
    Double admissionProbability;
    List<SimpleAdmission> admissions;
}
