package com.nju.urs.recommendation.service;

import com.nju.urs.recommendation.model.dto.RecommendedResult;
import com.nju.urs.recommendation.model.dto.StudentInfo;
import com.nju.urs.recommendation.model.dto.RecommendedResults;

import java.util.List;

public interface Recommendation {

    List<RecommendedResult> allRecommend(StudentInfo studentInfo);

    RecommendedResults recommend(StudentInfo studentInfo);

}
