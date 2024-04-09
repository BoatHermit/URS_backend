package com.nju.urs.recommendation.service;

import com.nju.urs.recommendation.model.dto.RecommendedResult;
import com.nju.urs.common.model.dto.StudentInfo;

import java.util.List;

public interface Recommendation {

    public List<RecommendedResult> recommend(StudentInfo studentInfo);

}
