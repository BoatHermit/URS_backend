package com.nju.urs.web.controller;

import com.nju.urs.common.annotation.Log;
import com.nju.urs.common.utils.Result;
import com.nju.urs.service.model.param.MajorFilterParam;
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
    @GetMapping("/probability")
    public Result filter(@RequestBody SchoolAdmissionParam param) {
        return Result.success(admissionService.schoolAdmission(param));
    }

    @GetMapping("/scoreline")
    public Result getScoreLine(String schoolId, String province, Integer subject) {
        return Result.success(admissionService.getScoreLines(schoolId, province, subject));
    }
}
