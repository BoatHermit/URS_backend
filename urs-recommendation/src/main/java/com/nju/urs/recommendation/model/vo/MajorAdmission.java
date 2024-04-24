package com.nju.urs.recommendation.model.vo;

import com.nju.urs.dao.mysql.model.vo.SimpleAdmission;
import lombok.Data;

import java.util.List;

@Data
public class MajorAdmission {
    String majorCode;
    String majorName;
    Double admissionProbability;
    List<SimpleAdmission> admissions;
}
