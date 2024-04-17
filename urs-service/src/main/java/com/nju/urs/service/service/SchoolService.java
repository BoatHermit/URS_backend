package com.nju.urs.service.service;

import com.nju.urs.dao.mongo.model.po.School;
import com.nju.urs.service.model.param.SchoolFilterParam;
import com.nju.urs.service.model.vo.CompleteSchool;
import com.nju.urs.service.model.vo.SimpleSchool;

import java.util.List;

public interface SchoolService {

    List<SimpleSchool> listPage(int pageNo, int pageSize);
    Integer countPage(int pageSize);

    CompleteSchool getSchoolById(String id);
    List<SimpleSchool> getSchoolsByFuzzyName(String fuzzyName);
    List<SimpleSchool> getSchoolsPage(SchoolFilterParam param);
    Integer countSchoolsPage(SchoolFilterParam param);
}
