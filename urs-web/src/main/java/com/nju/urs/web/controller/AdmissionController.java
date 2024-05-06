package com.nju.urs.web.controller;

import com.nju.urs.common.annotation.Log;
import com.nju.urs.common.utils.Result;
import com.nju.urs.service.model.param.SchoolAdmissionParam;
import com.nju.urs.service.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admission")
public class AdmissionController {
    private AdmissionService admissionService;

    @Autowired
    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @Log(module = "录取", operation = "计算某学校所有专业录取概率")
    @RequestMapping("/probability")
    public Result getSchoolAdmissionProbability(@RequestBody SchoolAdmissionParam param) {
        return Result.success(admissionService.getSchoolAdmissionProbability(param));
    }

    @Log(module = "录取", operation = "获取某学校在某省份某个选课情况的分数线")
    @GetMapping("/scoreline")
    public Result getScoreLines(Integer schoolId, String province, Integer subject) {
        return Result.success(admissionService.getScoreLines(schoolId, province, subject));
    }
}
