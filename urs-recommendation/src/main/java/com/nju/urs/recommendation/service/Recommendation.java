package com.nju.urs.recommendation.service;

import com.nju.urs.recommendation.model.vo.MajorAdmission;
import com.nju.urs.recommendation.model.vo.RecommendedResult;
import com.nju.urs.recommendation.model.vo.StudentInfo;
import com.nju.urs.recommendation.model.vo.RecommendedResults;

import java.util.List;

public interface Recommendation {

    List<RecommendedResult> allRecommend(StudentInfo studentInfo);

    RecommendedResults recommend(StudentInfo studentInfo);
    List<MajorAdmission> calculateSchoolAdmissionProbability(Integer schoolId, StudentInfo studentInfo);
}
