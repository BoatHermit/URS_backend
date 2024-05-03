package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreMapper extends MongoRepository<Score, String> {
    Score findByProvinceAndStudentTypeAndScoreType(String province, String studentType, String scoreType);
    Score findByProvinceAndStudentType(String province, String studentType);
}
