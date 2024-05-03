package com.nju.urs.web.controller;

import com.nju.urs.common.utils.Result;
import com.nju.urs.service.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {
    ScoreService scoreService;
    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @RequestMapping("/table")
    public Result getScoreTable(String province, String studentType, String scoreType) {
        return Result.success(scoreService.getScoreTable(province, studentType, scoreType));
    }

    @RequestMapping("/info")
    public Result getScoreInfo(String score, String province, String studentType, String scoreType) {
        return Result.success(scoreService.getScoreInfo(score, province, studentType, scoreType));
    }
}
