package com.nju.urs.web.controller;

import com.nju.urs.recommendation.model.dto.StudentInfo;
import com.nju.urs.common.utils.Result;
import com.nju.urs.recommendation.service.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {
    Recommendation recommendation;

    @Autowired
    public RecommendationController(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    @GetMapping()
    public Result getRecommendation(StudentInfo s) {
        return Result.success(recommendation.recommend(s));
    }
}
