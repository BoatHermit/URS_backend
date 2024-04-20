package com.nju.urs.web.controller;

import com.nju.urs.recommendation.model.vo.StudentInfo;
import com.nju.urs.common.utils.Result;
import com.nju.urs.recommendation.model.param.RecommendParam;
import com.nju.urs.recommendation.service.RecommendByRisk;
import com.nju.urs.recommendation.service.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {
    Recommendation recommendation;
    RecommendByRisk recommendByRisk;

    @Autowired
    public RecommendationController(Recommendation recommendation, RecommendByRisk recommendByRisk) {
        this.recommendation = recommendation;
        this.recommendByRisk = recommendByRisk;
    }

    @GetMapping()
    public Result getRecommendation(@RequestBody StudentInfo s) {
        return Result.success(recommendation.recommend(s));
    }

    @GetMapping("/highrisk")
    public Result getHighRisk(@RequestBody RecommendParam param) {
        return Result.success(recommendByRisk.recommendHighRisk(param));
    }

    @GetMapping("/midrisk")
    public Result getMidRisk(@RequestBody RecommendParam param) {
        return Result.success(recommendByRisk.recommendMidRisk(param));
    }

    @GetMapping("/lowrisk")
    public Result getLowRisk(@RequestBody RecommendParam param) {
        return Result.success(recommendByRisk.recommendLowRisk(param));
    }
}
