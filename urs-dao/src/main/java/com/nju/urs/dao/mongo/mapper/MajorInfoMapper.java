package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.MajorInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorInfoMapper extends MongoRepository<MajorInfo,String> {
}
