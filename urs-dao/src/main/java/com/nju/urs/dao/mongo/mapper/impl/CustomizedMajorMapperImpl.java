package com.nju.urs.dao.mongo.mapper.impl;

import com.nju.urs.common.utils.FuzzySearch;
import com.nju.urs.dao.mongo.mapper.CustomizedMajorMapper;
import com.nju.urs.dao.mongo.model.po.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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
        String regex = FuzzySearch.getRegex(keyword);
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("name").regex(regex, "i"),
                Criteria.where("level1_name").regex(regex, "i"),
                Criteria.where("level2_name").regex(regex, "i"),
                Criteria.where("level3_name").regex(regex, "i")
        );
        query.addCriteria(criteria);

        return (int) mongoTemplate.count(query, Major.class);
    }
}
