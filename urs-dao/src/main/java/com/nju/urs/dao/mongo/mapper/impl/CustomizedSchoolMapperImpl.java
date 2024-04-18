package com.nju.urs.dao.mongo.mapper.impl;

import com.nju.urs.dao.mongo.mapper.CustomizedSchoolMapper;
import com.nju.urs.dao.mongo.model.po.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class CustomizedSchoolMapperImpl implements CustomizedSchoolMapper {
    MongoTemplate mongoTemplate;

    @Autowired
    public CustomizedSchoolMapperImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private void addCondition(Query query, String fieldName, String value) {
        if (value != null && !value.isEmpty()) {
            query.addCriteria(Criteria.where(fieldName).is(value));
        }
    }

    private Query getSchoolQuery(School conditions) {
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
    public List<School> findSchoolByConditions(int pageNo, int pageSize, School school) {
        Query query = getSchoolQuery(school);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        query.with(pageable);

        return mongoTemplate.find(query, School.class);
    }

    @Override
    public Integer countSchoolByConditions(int pageNo, int pageSize, School school) {
        Query query = getSchoolQuery(school);
        double num = mongoTemplate.count(query, School.class);
        return (int) Math.ceil(num / pageSize);
    }

}
