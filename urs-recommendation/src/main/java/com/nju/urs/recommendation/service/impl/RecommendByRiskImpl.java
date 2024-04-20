package com.nju.urs.recommendation.service.impl;

import com.nju.urs.recommendation.model.vo.RecommendPage;
import com.nju.urs.recommendation.model.vo.RecommendedResult;
import com.nju.urs.recommendation.model.vo.RecommendedResults;
import com.nju.urs.recommendation.model.param.RecommendParam;
import com.nju.urs.recommendation.service.RecommendByRisk;
import com.nju.urs.recommendation.service.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendByRiskImpl implements RecommendByRisk {

    Recommendation recommendation;

    @Autowired
    public RecommendByRiskImpl(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    @Override
    public RecommendPage recommendHighRisk(RecommendParam param) {
        RecommendedResults recommendedResults = recommendation.recommend(param.getStudentInfo());
        List<RecommendedResult> highRisk = recommendedResults.getHighRisk();
        return getPage(param, highRisk);
    }

    @Override
    public RecommendPage recommendMidRisk(RecommendParam param) {
        RecommendedResults recommendedResults = recommendation.recommend(param.getStudentInfo());
        List<RecommendedResult> midRisk = recommendedResults.getMidRisk();
        return getPage(param, midRisk);
    }

    @Override
    public RecommendPage recommendLowRisk(RecommendParam param) {
        RecommendedResults recommendedResults = recommendation.recommend(param.getStudentInfo());
        List<RecommendedResult> lowRisk = recommendedResults.getLowRisk();
        return getPage(param, lowRisk);
    }

    private RecommendPage getPage(RecommendParam param, List<RecommendedResult> list) {
        if (param.getPageNo() != null && param.getPageSize() != null) {
            Pageable pageable = PageRequest.of(param.getPageNo()-1, param.getPageSize());
            // 对缓存中的数据进行分页操作
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), list.size());
            RecommendPage page = new RecommendPage();
            page.setPageNo(param.getPageNo());
            page.setPageSize(param.getPageSize());
            page.setPageNum((int) Math.ceil((double) list.size()/param.getPageSize()));
            page.setTotalCount(list.size());
            page.setRecommend(list.subList(start, end));
            return page;
        } else {
            RecommendPage page = new RecommendPage();
            page.setRecommend(list);
            return page;
        }
    }
}
