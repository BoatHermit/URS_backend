package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.Major;

import java.util.List;

public interface CustomizedMajorMapper {
    Integer countByKeyword(String keyword);
    List<Major> findByConditions(int pageNo, int pageSize, Major school, String keyword);
    Integer countByConditions(Major school, String keyword);
}
