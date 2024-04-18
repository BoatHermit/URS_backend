package com.nju.urs.dao.mongo.mapper;

import com.nju.urs.dao.mongo.model.po.School;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolMapper extends MongoRepository<School, String>, CustomizedSchoolMapper {

    @Query(value = "{}",fields = "{ 'school_id' : 1, 'school_name' : 1, 'province_name' : 1, 'school_type_name' : 1, " +
            "'type_name' : 1, 'nature_name' : 1, 'f985' : 1, 'f211' : 1, 'dual_class_name' : 1 }")
    @NotNull
    Page<School> findAll(@NotNull Pageable pageable);

    // @Query(value = "{'provinceName' : ?0, 'typeName' : ?1, 'levelName' : ?2, 'natureName' : ?3, 'f211' : ?4, " +
    //         "'f985' : ?5, 'dualClassName' : ?6, 'belong' : ?7}",
    //         fields = "{ 'school_id' : 1, 'school_name' : 1, 'province_name' : 1, 'school_type_name' : 1, " +
    //         "'type_name' : 1, 'nature_name' : 1, 'f985' : 1, 'f211' : 1, 'dual_class_name' : 1 }")
    // @NotNull
    // List<School> findByConditions(String provinceName, String typeName, String levelName, String natureName,
    //                               String f211, String f985, String dualClassName, String belong);

    School findOneBySchoolId(String id);

    @Query("{'school_name': {$regex: ?0, $options: 'i'}}")
    List<School> findByFuzzySchoolName(String regex);
}
