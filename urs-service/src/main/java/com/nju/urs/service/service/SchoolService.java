package com.nju.urs.service.service;

import com.nju.urs.service.model.param.SchoolFilterParam;
import com.nju.urs.service.model.vo.CompleteSchool;
import com.nju.urs.dao.mongo.model.vo.SimpleSchool;

import java.util.List;

public interface SchoolService {

    List<SimpleSchool> listPage(int pageNo, int pageSize);
    Integer countPage(int pageSize);

    CompleteSchool getSchoolById(String id);
    List<SimpleSchool> getSchoolsByKeywords(String fuzzyName);
    List<SimpleSchool> getSchoolsByConditions(SchoolFilterParam param);
    Integer countPagesByConditions(SchoolFilterParam param);
}
