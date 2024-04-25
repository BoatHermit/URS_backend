package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.School;

import java.util.List;

public interface CustomizedSchoolMapper {
    List<School> findByConditions(int pageNo, int pageSize, School school, String keyword);
    List<School> findByConditions(School school, String keyword);
    Integer countByConditions(School school, String keyword);
}
