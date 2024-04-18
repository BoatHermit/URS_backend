package com.nju.urs.web.controller;

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

    @GetMapping("/filter")
    public Result filter(@RequestBody SchoolFilterParam param) {
        return Result.success(schoolService.getSchoolsByConditions(param));
    }
    @GetMapping("/filter/count")
    public Result filterCount(@RequestBody SchoolFilterParam param) {
        return Result.success(schoolService.countPagesByConditions(param));
    }


    @GetMapping("/id")
    public Result getById(String id) {
        return Result.success(schoolService.getSchoolById(id));
    }

    @GetMapping("/search")
    public Result getByFuzzyName(String fuzzyName) {
        return Result.success(schoolService.getSchoolsByKeywords(fuzzyName));
    }
}
