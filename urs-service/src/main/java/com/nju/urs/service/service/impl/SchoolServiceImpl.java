package com.nju.urs.service.service.impl;

import com.nju.urs.dao.mongo.model.po.School;
import com.nju.urs.service.model.vo.SchoolConditions;
import com.nju.urs.service.model.param.SchoolFilterParam;
import com.nju.urs.service.model.vo.CompleteSchool;
import com.nju.urs.dao.mongo.model.vo.SimpleSchool;
import com.nju.urs.service.service.SchoolService;
import com.nju.urs.dao.mongo.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    SchoolMapper schoolMapper;

    @Autowired
    public SchoolServiceImpl(SchoolMapper schoolMapper) {
        this.schoolMapper = schoolMapper;
    }


    @Override
    public List<SimpleSchool> listPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<School> schools = schoolMapper.findAll(pageable);
        List<SimpleSchool> simpleSchools = new ArrayList<>();
        for (School school : schools) {
            SimpleSchool simpleSchool = new SimpleSchool(school);
            simpleSchools.add(simpleSchool);
        }
        return simpleSchools;
    }

    @Override
    public Integer countPage(int pageSize) {
        double num = schoolMapper.count();
        return (int) Math.ceil(num / pageSize);
    }

    @Override
    public CompleteSchool getSchoolById(String id) {
        School school =schoolMapper.findOneBySchoolId(id);
        return new CompleteSchool(school);
    }

    @Override
    public List<SimpleSchool> getSchoolsByKeywords(String fuzzyName) {
        StringBuilder regex = new StringBuilder("(?:");
        String[] strings = fuzzyName.split(" ");
        for (String string : strings) {
            regex.append(".*").append(string);
        }
        regex.append(".*)");
        List<School> schools = schoolMapper.findByFuzzySchoolName(String.valueOf(regex));
        List<SimpleSchool> simpleSchools = new ArrayList<>();
        for (School school : schools) {
            SimpleSchool simpleSchool = new SimpleSchool(school);
            simpleSchools.add(simpleSchool);
        }
        return simpleSchools;
    }

    @Override
    public List<SimpleSchool> getSchoolsByConditions(SchoolFilterParam param) {
        School querySchool = SchoolConditions.wrapConditions(param.getConditions());

        List<School> schools = schoolMapper.findByConditions(
                param.getPageNo(), param.getPageSize(), querySchool, param.getKeyword());
        List<SimpleSchool> simpleSchools = new ArrayList<>();
        for (School school : schools) {
            SimpleSchool simpleSchool = new SimpleSchool(school);
            simpleSchools.add(simpleSchool);
        }

        return simpleSchools;
    }

    @Override
    public Integer countPagesByConditions(SchoolFilterParam param) {
        School querySchool = SchoolConditions.wrapConditions(param.getConditions());
        double num = schoolMapper.countByConditions(querySchool, param.getKeyword());
        return (int) Math.ceil(num / param.getPageSize());
    }
}
