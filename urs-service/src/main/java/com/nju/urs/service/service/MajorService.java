package com.nju.urs.service.service;

import com.nju.urs.service.model.param.MajorFilterParam;
import com.nju.urs.service.model.param.MajorSearchParam;
import com.nju.urs.service.model.vo.CompleteMajor;
import com.nju.urs.service.model.vo.SimpleMajor;

import java.util.List;

public interface MajorService {
    CompleteMajor getMajorByCode(String code);
    List<SimpleMajor> getMajorsByKeyword(MajorSearchParam param);
    Integer countPagesByKeyword(MajorSearchParam param);
    List<SimpleMajor> getMajorsByConditions(MajorFilterParam param);
    Integer countPagesByConditions(MajorFilterParam param);
}
