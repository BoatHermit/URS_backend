package com.nju.urs.web.controller;

import com.nju.urs.common.annotation.Log;
import com.nju.urs.common.utils.Result;
import com.nju.urs.service.model.param.SchoolFilterParam;
import com.nju.urs.service.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/list")
    public Result list(int pageNo, int pageSize) {
        return Result.success(schoolService.listPage(pageNo, pageSize));
    }
    @GetMapping("/list/count")
    public Result page(int pageSize) {
        return Result.success(schoolService.countPage(pageSize));
    }

    @Log(module = "大学", operation = "根据条件筛选大学（结果分页）")
    @GetMapping("/filter")
    public Result filter(@RequestBody SchoolFilterParam param) {
        return Result.success(schoolService.getSchoolsByConditions(param));
    }

    @Log(module = "大学", operation = "根据条件筛选大学的总页数")
    @GetMapping("/filter/count")
    public Result filterCount(@RequestBody SchoolFilterParam param) {
        return Result.success(schoolService.countPagesByConditions(param));
    }

    @Log(module = "大学", operation = "获取大学详细信息")
    @GetMapping("/id")
    public Result getById(String id) {
        return Result.success(schoolService.getSchoolById(id));
    }

    @GetMapping("/search")
    public Result getByFuzzyName(String fuzzyName) {
        return Result.success(schoolService.getSchoolsByKeywords(fuzzyName));
    }
}
