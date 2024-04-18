package com.nju.urs.dao.mongo.mapper.impl;

import com.nju.urs.common.utils.FuzzySearch;
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

    private Query getSchoolQuery(School conditions, String keyword) {
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
        String regex = FuzzySearch.getRegex(keyword);
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("school_name").regex(regex, "i")
        );
        query.addCriteria(criteria);
        return query;
    }

    @Override
    public List<School> findByConditions(int pageNo, int pageSize, School school, String keyword) {
        Query query = getSchoolQuery(school, keyword);
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        query.with(pageable);

        return mongoTemplate.find(query, School.class);
    }

    @Override
    public Integer countByConditions(School school, String keyword) {
        Query query = getSchoolQuery(school, keyword);
        return (int) mongoTemplate.count(query, School.class);
    }

}
