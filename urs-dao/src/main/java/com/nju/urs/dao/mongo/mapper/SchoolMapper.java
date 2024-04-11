package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.School;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolMapper extends MongoRepository<School, String> {
}
