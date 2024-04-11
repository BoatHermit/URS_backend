package com.nju.urs.service.service.impl;

import com.nju.urs.dao.mongo.model.po.School;
import com.nju.urs.service.service.SchoolService;
import com.nju.urs.dao.mongo.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    SchoolMapper schoolMapper;

    @Autowired
    public SchoolServiceImpl(SchoolMapper schoolMapper) {
        this.schoolMapper = schoolMapper;
    }

    @Override
    public List<School> getAll() {
        return schoolMapper.findAll();
    }
}
