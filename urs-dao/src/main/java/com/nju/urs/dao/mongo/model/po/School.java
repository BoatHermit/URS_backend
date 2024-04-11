package com.nju.urs.dao.mongo.model.po;

import com.nju.urs.dao.mongo.model.po.Inner.DualClass;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document(collection = "school")
public class School {
    @MongoId
    private String id;

    @Field("school_id")
    private String schoolId;

    @Field("school_name")
    private String schoolName;

    @Field("school_email_one")
    private String schoolEmailOne;

    @Field("school_email_two")
    private String schoolEmailTwo;

    @Field("school_address")
    private String schoolAddress;

    @Field("school_postcode")
    private String schoolPostcode;

    @Field("school_site_one")
    private String schoolSiteOne;

    @Field("school_site_two")
    private String schoolSiteTwo;

    @Field("school_phone_one")
    private String schoolPhoneOne;

    @Field("school_phone_two")
    private String schoolPhoneTwo;

    private String belong;

    @Field("city_id")
    private String cityId;

    @Field("city_name")
    private String cityName;

    private String f211;

    private String f985;

    @Field("level_name")
    private String levelName;

    @Field("nature_name")
    private String natureName;

    @Field("province_id")
    private String provinceId;

    @Field("province_name")
    private String provinceName;

    @Field("type_name")
    private String typeName;

    @Field("school_type_name")
    private String schoolTypeName;

    @Field("dual_class_name")
    private String dualClassname;

    @Field("dualclass")
    private List<DualClass> dualClass;
}
