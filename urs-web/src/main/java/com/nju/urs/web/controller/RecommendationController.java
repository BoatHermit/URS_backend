package com.nju.urs.web.controller;

import com.nju.urs.common.annotation.Log;
import com.nju.urs.recommendation.model.vo.StudentInfo;
import com.nju.urs.common.utils.Result;
import com.nju.urs.service.model.param.RecommendParam;
import com.nju.urs.service.service.AdmissionService;
import com.nju.urs.recommendation.service.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {
    Recommendation recommendation;
    AdmissionService admissionService;

    @Autowired
    public RecommendationController(Recommendation recommendation, AdmissionService admissionService) {
        this.recommendation = recommendation;
        this.admissionService = admissionService;
    }

    @GetMapping()
    public Result getRecommendation(@RequestBody StudentInfo s) {
        return Result.success(recommendation.recommend(s));
    }

    @Log(module = "推荐", operation = "获取高风险推荐学校专业")
    @GetMapping("/highrisk")
    public Result getHighRisk(@RequestBody RecommendParam param) {
        return Result.success(admissionService.recommendHighRisk(param));
    }

    @Log(module = "推荐", operation = "获取中风险推荐学校专业")
    @GetMapping("/midrisk")
    public Result getMidRisk(@RequestBody RecommendParam param) {
        return Result.success(admissionService.recommendMidRisk(param));
    }

    @Log(module = "推荐", operation = "获取低风险推荐学校专业")
    @GetMapping("/lowrisk")
    public Result getLowRisk(@RequestBody RecommendParam param) {
        return Result.success(admissionService.recommendLowRisk(param));
    }
}
