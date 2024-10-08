package com.nju.urs.dao.mongo.model.vo;

import com.nju.urs.dao.mongo.model.po.School;
import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleSchool implements Serializable {
    private String schoolId;
    private String schoolName;
    private String provinceName;
    private String levelName;
    private String typeName;
    private String natureName;
    private String f985;
    private String f211;
    private String dualClassName;
    private String belong;

    public SimpleSchool(School school) {
        if (school != null) {
            schoolId = school.getSchoolId();
            schoolName = school.getSchoolName();
            provinceName = school.getProvinceName();
            levelName = school.getLevelName();
            typeName = school.getTypeName();
            natureName = school.getNatureName();
            f985 = school.getF985();
            f211 = school.getF211();
            dualClassName = school.getDualClassName();
            belong = school.getBelong();
        }
    }
}
