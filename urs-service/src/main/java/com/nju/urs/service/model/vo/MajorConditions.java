package com.nju.urs.service.model.vo;

import com.nju.urs.dao.mongo.model.po.Major;
import lombok.Data;

@Data
public class MajorConditions {
    private String level1Name;
    private String level2Name;
    private String level3Name;
    public static Major wrapConditions(MajorConditions conditions) {
        if (conditions == null) {
            return null;
        }
        Major major = new Major();
        major.setLevel1Name(conditions.getLevel1Name());
        major.setLevel2Name(conditions.getLevel2Name());
        major.setLevel3Name(conditions.getLevel3Name());
        return major;
    }
}
