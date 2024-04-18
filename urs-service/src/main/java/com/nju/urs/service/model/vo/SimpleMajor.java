package com.nju.urs.service.model.vo;

import com.nju.urs.dao.mongo.model.po.Major;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class SimpleMajor {
    private String degree;
    private String highTitle;
    private String level1Name;
    private String level2Name;
    private String level3Name;
    private String name;
    private String code;
    private String viewWeek;

    public SimpleMajor(Major major) {
        this.degree = major.getDegree();
        this.highTitle = major.getHighTitle();
        this.level1Name = major.getLevel1Name();
        this.level2Name = major.getLevel2Name();
        this.level3Name = major.getLevel3Name();
        this.name = major.getName();
        this.code = major.getCode();
        this.viewWeek = major.getViewWeek();
    }
}
