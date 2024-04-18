package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.School;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomizedSchoolMapper {
    List<School> findSchoolByConditions(int pageNo, int pageSize, School school);
    Integer countSchoolByConditions(int pageNo, int pageSize, School school);
}
