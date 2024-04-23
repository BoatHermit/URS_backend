package com.nju.urs.service.model.dto;

import com.nju.urs.recommendation.model.vo.MajorAdmission;
import lombok.Data;

import java.util.List;

@Data
public class SchoolAdmissionPage {
    private Integer pageNo;
    private Integer pageSize;
    private Integer pageNum;
    private Integer totalCount;
    List<MajorAdmission> majorAdmissions;
}
