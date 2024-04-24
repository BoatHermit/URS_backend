package com.nju.urs.service.service;

import com.nju.urs.service.model.dto.RecommendPage;
import com.nju.urs.service.model.dto.SchoolAdmissionPage;
import com.nju.urs.service.model.param.SchoolAdmissionParam;
import com.nju.urs.service.model.param.RecommendParam;

public interface AdmissionService {

    RecommendPage recommendHighRisk(RecommendParam param);
    RecommendPage recommendMidRisk(RecommendParam param);
    RecommendPage recommendLowRisk(RecommendParam param);
    SchoolAdmissionPage schoolAdmission(SchoolAdmissionParam param);
}
