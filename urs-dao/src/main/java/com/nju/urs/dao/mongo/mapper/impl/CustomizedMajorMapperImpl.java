package com.nju.urs.dao.mongo.mapper.impl;

import com.nju.urs.common.utils.QueryUtils;
import com.nju.urs.dao.mongo.mapper.CustomizedMajorMapper;
import com.nju.urs.dao.mongo.model.po.Major;
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
public class CustomizedMajorMapperImpl implements CustomizedMajorMapper {
    MongoTemplate mongoTemplate;

    @Autowired
    public CustomizedMajorMapperImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Integer countByKeyword(String keyword) {
        Query query = new Query();
        query.addCriteria(getSearchCriteria(keyword));

        return (int) mongoTemplate.count(query, Major.class);
    }

    private Query getMajorQuery(Major conditions, String keyword) {
        Query query = new Query();
        if (conditions != null) {
            QueryUtils.addCondition(query, "level1_name", conditions.getLevel1Name());
            QueryUtils.addCondition(query, "level2_name", conditions.getLevel2Name());
            QueryUtils.addCondition(query, "level3_name", conditions.getLevel3Name());
        }
        query.addCriteria(getSearchCriteria(keyword));
        return query;
    }

    private Criteria getSearchCriteria(String keyword) {
        String regex = QueryUtils.getFuzzyRegex(keyword);
        return new Criteria().orOperator(
                Criteria.where("name").regex(regex, "i"),
                Criteria.where("level1_name").regex(regex, "i"),
                Criteria.where("level2_name").regex(regex, "i"),
                Criteria.where("level3_name").regex(regex, "i")
        );
    }

    @Override
    public List<Major> findByConditions(int pageNo, int pageSize, Major school, String keyword) {
        Query query = getMajorQuery(school, keyword);
        Pageable queryPageable = PageRequest.of(pageNo-1, pageSize);
        query.with(queryPageable);

        return mongoTemplate.find(query, Major.class);
    }

    @Override
    public Integer countByConditions(Major school, String keyword) {
        Query query = getMajorQuery(school, keyword);
        return (int) mongoTemplate.count(query, Major.class);
    }
}
