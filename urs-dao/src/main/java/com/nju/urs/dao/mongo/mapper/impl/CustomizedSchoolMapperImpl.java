package com.nju.urs.dao.mongo.mapper.impl;

import com.nju.urs.common.utils.QueryUtils;
import com.nju.urs.dao.mongo.mapper.CustomizedSchoolMapper;
import com.nju.urs.dao.mongo.model.po.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomizedSchoolMapperImpl implements CustomizedSchoolMapper {
    MongoTemplate mongoTemplate;

    @Autowired
    public CustomizedSchoolMapperImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private Query getSchoolQuery(School conditions, String keyword) {
        Query query = new Query();
        if (conditions != null) {
            QueryUtils.addCondition(query, "province_name", conditions.getProvinceName());
            QueryUtils.addCondition(query, "type_name", conditions.getTypeName());
            QueryUtils.addCondition(query, "level_name", conditions.getLevelName());
            QueryUtils.addCondition(query, "nature_name", conditions.getNatureName());
            QueryUtils.addCondition(query, "f211", conditions.getF211());
            QueryUtils.addCondition(query, "f985", conditions.getF985());
            QueryUtils.addCondition(query, "dual_class_name", conditions.getDualClassName());
            QueryUtils.addCondition(query, "belong", conditions.getBelong());
        }
        if(keyword != null && !keyword.trim().equals("")) {
            String regex = QueryUtils.getFuzzyRegex(keyword);
            Criteria criteria = new Criteria().orOperator(
                    Criteria.where("school_name").regex(regex, "i")
            );
            query.addCriteria(criteria);
        }

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
    public List<School> findByConditions(School school, String keyword) {
        Query query = getSchoolQuery(school, keyword);
        return mongoTemplate.find(query, School.class);
    }

    @Override
    public Integer countByConditions(School school, String keyword) {
        Query query = getSchoolQuery(school, keyword);
        return (int) mongoTemplate.count(query, School.class);
    }

}
