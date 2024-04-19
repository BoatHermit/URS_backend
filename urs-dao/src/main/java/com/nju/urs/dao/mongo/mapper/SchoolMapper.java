package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolMapper extends MongoRepository<School, String>, CustomizedSchoolMapper {

    @Query(value = "{}",fields = "{ 'school_id' : 1, 'school_name' : 1, 'province_name' : 1, 'level_name' : 1, " +
            "'type_name' : 1, 'nature_name' : 1, 'f985' : 1, 'f211' : 1, 'dual_class_name' : 1 }")
    Page<School> findAll(Pageable pageable);

    School findOneBySchoolId(String id);

    @Query("{'school_name': {$regex: ?0, $options: 'i'}}")
    List<School> findByFuzzySchoolName(String regex);
}
