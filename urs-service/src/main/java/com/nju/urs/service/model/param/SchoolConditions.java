package com.nju.urs.service.model.param;

import lombok.Data;

@Data
public class SchoolConditions {
    private String provinceName;
    // 综合、理工、农林、医药、师范、语言、财经、政法、体育、艺术、民族、军事、其他
    private String typeName;
    // 普通本科、专科（高职）
    private String levelName;
    // 公办、民办、中外合作办学、内地与港澳台地区合作办学
    private String natureName;
    // 1-是，2-不是
    private String f211;
    private String f985;
    // 双一流
    private String dualClassName;
    // 教育部
    private String belong;
}
