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

import java.util.ArrayList;
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
        List<Criteria> andCriteriaList = new ArrayList<>();
        if (conditions != null) {
            if (conditions.getProvinceName() != null && !conditions.getProvinceName().isEmpty()) {
                String[] provinces = conditions.getProvinceName().split(" ");
                if (provinces.length > 1) {
                    List<Criteria> criteriaList = new ArrayList<>();
                    for (String province : provinces) {
                        criteriaList.add(Criteria.where("province_name").is(province));
                    }
                    andCriteriaList.add(new Criteria().orOperator(criteriaList));
                } else {
                    andCriteriaList.add(Criteria.where("province_name").is(conditions.getProvinceName()));
                }
            }
            if (conditions.getTypeName() != null && !conditions.getTypeName().isEmpty()) {
                String[] types = conditions.getTypeName().split(" ");
                if (types.length > 1) {
                    List<Criteria> criteriaList = new ArrayList<>();
                    for (String type : types) {
                        criteriaList.add(Criteria.where("type_name").regex(
                                "^"+type+".*"));
                    }
                    andCriteriaList.add(new Criteria().orOperator(criteriaList));
                } else {
                    andCriteriaList.add(Criteria.where("type_name").regex("^"+conditions.getTypeName()+".*"));
                }
            }
            if (conditions.getNatureName() != null && !conditions.getNatureName().isEmpty()) {
                String[] natureNames = conditions.getNatureName().split(" ");
                if (natureNames.length > 1) {
                    List<Criteria> criteriaList = new ArrayList<>();
                    for (String natureName : natureNames) {
                        criteriaList.add(Criteria.where("nature_name").is(natureName));
                    }
                    andCriteriaList.add(new Criteria().orOperator(criteriaList));
                } else {
                    andCriteriaList.add(Criteria.where("nature_name").is(conditions.getNatureName()));
                }
            }

            QueryUtils.addCondition(query, "level_name", conditions.getLevelName());
            QueryUtils.addCondition(query, "f211", conditions.getF211());
            QueryUtils.addCondition(query, "f985", conditions.getF985());
            QueryUtils.addCondition(query, "dual_class_name", conditions.getDualClassName());

            if(conditions.getBelong() != null && conditions.getBelong().equals("教育部直属")) {
                QueryUtils.addCondition(query, "belong", "教育部");
            } else if (conditions.getBelong() != null && conditions.getBelong().equals("中央部委")) {
                String[] departments = {
                        "教育部", "外交部", "工业和信息化部", "国家民委", "中国科学院", "中央军委", "中央统战部", "中央办公厅",
                        "公安部", "国家卫生健康委员会", "国家体育总局", "中华全国总工会", "中华妇女联合会",
                        "交通运输部（中国民用航空局）", "海关总署", "交通运输部", "司法部", "应急管理部", "中国地震局"
                };
                List<Criteria> criteriaList = new ArrayList<>();
                for (String department : departments) {
                    criteriaList.add(Criteria.where("belong").is(department));
                }
                andCriteriaList.add(new Criteria().orOperator(criteriaList));
            }
        }
        if(keyword != null && !keyword.trim().isEmpty()) {
            String regex = QueryUtils.getFuzzyRegex(keyword);
            Criteria criteria = new Criteria().orOperator(
                    Criteria.where("school_name").regex(regex, "i")
            );
            andCriteriaList.add(criteria);
        }
        if (andCriteriaList.size() > 1) {
            query.addCriteria(new Criteria().andOperator(andCriteriaList));
        } else if (andCriteriaList.size() == 1) {
            query.addCriteria(andCriteriaList.get(0));
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
