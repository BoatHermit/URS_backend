package com.nju.urs.service.model.vo;

import com.nju.urs.dao.mongo.model.po.Inner.DualClass;
import com.nju.urs.dao.mongo.model.po.School;
import lombok.Data;

import java.util.List;

@Data
public class CompleteSchool {

    private String schoolId;
    private String schoolName;
    private String schoolEmailOne;
    private String schoolEmailTwo;
    private String schoolAddress;
    private String schoolPostcode;
    private String schoolSiteOne;
    private String schoolSiteTwo;
    private String schoolPhoneOne;
    private String schoolPhoneTwo;
    private String belong;
    private String cityId;
    private String cityName;
    private String f211;
    private String f985;
    private String levelName;
    private String natureName;
    private String provinceId;
    private String provinceName;
    private String typeName;
    private String schoolTypeName;
    private String dualClassName;
    private List<DualClass> dualClass;

    public CompleteSchool(School school) {
        schoolId = school.getSchoolId();
        schoolName = school.getSchoolName();
        schoolEmailOne = school.getSchoolEmailOne();
        schoolEmailTwo = school.getSchoolEmailTwo();
        schoolAddress = school.getSchoolAddress();
        schoolPostcode = school.getSchoolPostcode();
        schoolSiteOne = school.getSchoolSiteOne();
        schoolSiteTwo = school.getSchoolSiteTwo();
        schoolPhoneOne = school.getSchoolPhoneOne();
        schoolPhoneTwo = school.getSchoolPhoneTwo();
        belong = school.getBelong();
        cityId = school.getCityId();
        cityName = school.getCityName();
        f211 = school.getF211();
        f985 = school.getF985();
        levelName = school.getLevelName();
        natureName = school.getNatureName();
        provinceId = school.getProvinceId();
        provinceName = school.getProvinceName();
        typeName = school.getTypeName();
        schoolTypeName = school.getSchoolTypeName();
        dualClassName = school.getDualClassName();
        dualClass = school.getDualClass();
    }
}
