package com.nju.urs.recommendation.model.dto;

import lombok.Data;

@Data
public class RecommendedResult {
    private int schoolId;
    private String schoolName;
    private int majorId;
    private String majorName;
    private double admissionProbability;
}
