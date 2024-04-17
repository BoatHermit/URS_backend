package com.nju.urs.service.service.impl;

import com.nju.urs.dao.mongo.model.po.School;
import com.nju.urs.service.model.param.SchoolConditions;
import com.nju.urs.service.model.param.SchoolFilterParam;
import com.nju.urs.service.model.vo.CompleteSchool;
import com.nju.urs.service.model.vo.SimpleSchool;
import com.nju.urs.service.service.SchoolService;
import com.nju.urs.dao.mongo.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    SchoolMapper schoolMapper;
    MongoTemplate mongoTemplate;

    @Autowired
    public SchoolServiceImpl(SchoolMapper schoolMapper, MongoTemplate mongoTemplate) {
        this.schoolMapper = schoolMapper;
        this.mongoTemplate = mongoTemplate;
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
    public List<SimpleSchool> getSchoolsByFuzzyName(String fuzzyName) {
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

    private void addCondition(Query query, String fieldName, String value) {
        if (value != null && !value.isEmpty()) {
            query.addCriteria(Criteria.where(fieldName).is(value));
        }
    }

    private Query getSchoolQuery(SchoolConditions conditions) {
        Query query = new Query();
        if (conditions != null) {
            addCondition(query, "province_name", conditions.getProvinceName());
            addCondition(query, "type_name", conditions.getTypeName());
            addCondition(query, "level_name", conditions.getLevelName());
            addCondition(query, "nature_name", conditions.getNatureName());
            addCondition(query, "f211", conditions.getF211());
            addCondition(query, "f985", conditions.getF985());
            addCondition(query, "dual_class_name", conditions.getDualClassName());
            addCondition(query, "belong", conditions.getBelong());
        }
        return query;
    }

    @Override
    public List<SimpleSchool> getSchoolsPage(SchoolFilterParam param) {
        Query query = getSchoolQuery(param.getConditions());
        Integer pageNo = param.getPageNo();
        Integer pageSize = param.getPageSize();
        Pageable pageable = PageRequest.of(pageNo != null ? pageNo-1 : 0, pageSize != null ? pageSize : 10);
        query.with(pageable);

        List<School> schools = mongoTemplate.find(query, School.class);
        List<SimpleSchool> simpleSchools = new ArrayList<>();
        for (School school : schools) {
            SimpleSchool simpleSchool = new SimpleSchool(school);
            simpleSchools.add(simpleSchool);
        }

        return simpleSchools;
    }

    @Override
    public Integer countSchoolsPage(SchoolFilterParam param) {
        Query query = getSchoolQuery(param.getConditions());
        double num = mongoTemplate.count(query, School.class);
        return (int) Math.ceil(num / param.getPageSize());
    }
}
