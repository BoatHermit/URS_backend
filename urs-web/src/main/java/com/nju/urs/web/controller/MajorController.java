package com.nju.urs.web.controller;

import com.nju.urs.common.utils.Result;
import com.nju.urs.service.model.param.MajorFilterParam;
import com.nju.urs.service.model.param.MajorSearchParam;
import com.nju.urs.service.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/majors")
public class MajorController {
    MajorService majorService;

    @Autowired
    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping("/filter")
    public Result filter(@RequestBody MajorFilterParam param) {
        return Result.success(majorService.getMajorsByConditions(param));
    }

    @GetMapping("/filter/count")
    public Result filterCount(@RequestBody MajorFilterParam param) {
        return Result.success(majorService.countPagesByConditions(param));
    }

    @GetMapping("/code")
    public Result getByCode(@RequestParam String code) {
        return Result.success(majorService.getMajorByCode(code));
    }

    @GetMapping("/search")
    public Result search(@RequestBody MajorSearchParam param) {
        return Result.success(majorService.getMajorsByKeyword(param));
    }
    @GetMapping("/search/count")
    public Result searchCount(@RequestBody MajorSearchParam param) {
        return Result.success(majorService.countPagesByKeyword(param));
    }
}
