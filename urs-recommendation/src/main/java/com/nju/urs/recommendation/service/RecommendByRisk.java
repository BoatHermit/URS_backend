package com.nju.urs.recommendation.service;

import com.nju.urs.recommendation.model.vo.RecommendPage;
import com.nju.urs.recommendation.model.vo.RecommendedResult;
import com.nju.urs.recommendation.model.param.RecommendParam;

import java.util.List;

public interface RecommendByRisk {

    RecommendPage recommendHighRisk(RecommendParam param);
    RecommendPage recommendMidRisk(RecommendParam param);
    RecommendPage recommendLowRisk(RecommendParam param);
}
