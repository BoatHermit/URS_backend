package com.nju.urs.service.service;

import com.nju.urs.dao.mongo.model.po.School;

import java.util.List;

public interface SchoolService {

    List<School> getAll();
    // List<School> getSchoolByProvinceId(String id);
    // List<School> getSchoolByProvinceName(String name);
    // List<School> getSchoolByTypeName(String name);
    // List<School> getSchoolByLevelName(String name);
    // List<School> getSchoolByNatureName(String name);
}
