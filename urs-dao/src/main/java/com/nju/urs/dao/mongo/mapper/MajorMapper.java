package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.Major;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorMapper extends MongoRepository<Major, String>, CustomizedMajorMapper {

    @Query("{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }" +
            ", { 'level1_name': { $regex: ?0, $options: 'i' } }" +
            ", { 'level2_name': { $regex: ?0, $options: 'i' } }" +
            ", { 'level3_name': { $regex: ?0, $options: 'i' } } ] }")
    List<Major> findByKeyWord(String regex, Pageable pageable);

}
