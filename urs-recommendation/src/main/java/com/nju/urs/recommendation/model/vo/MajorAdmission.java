package com.nju.urs.recommendation.model.vo;

import com.nju.urs.dao.mysql.model.vo.SimpleAdmission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MajorAdmission implements Serializable {
    String majorCode;
    String majorName;
    Double admissionProbability;
    List<SimpleAdmission> admissions;
}
