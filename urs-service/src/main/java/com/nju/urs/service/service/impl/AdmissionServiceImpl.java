package com.nju.urs.service.service.impl;

import com.nju.urs.common.enums.Province;
import com.nju.urs.dao.mongo.mapper.MajorMapper;
import com.nju.urs.dao.mongo.mapper.SchoolMapper;
import com.nju.urs.dao.mongo.model.po.Major;
import com.nju.urs.dao.mongo.model.po.School;
import com.nju.urs.dao.mysql.mapper.AdmissionMapper;
import com.nju.urs.dao.mysql.mapper.ScoreLineMapper;
import com.nju.urs.dao.mysql.model.po.ScoreLine;
import com.nju.urs.recommendation.model.vo.*;
import com.nju.urs.service.model.dto.RecommendPage;
import com.nju.urs.service.model.dto.SchoolAdmissionPage;
import com.nju.urs.service.model.param.SchoolAdmissionParam;
import com.nju.urs.service.model.param.RecommendParam;
import com.nju.urs.service.model.vo.MajorConditions;
import com.nju.urs.service.model.vo.SchoolConditions;
import com.nju.urs.service.model.vo.ScoreLines;
import com.nju.urs.service.service.AdmissionService;
import com.nju.urs.recommendation.service.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    Recommendation recommendation;
    AdmissionMapper admissionMapper;
    ScoreLineMapper scoreLineMapper;
    SchoolMapper schoolMapper;
    MajorMapper majorMapper;

    @Autowired
    public AdmissionServiceImpl(Recommendation recommendation, AdmissionMapper admissionMapper,
                                ScoreLineMapper scoreLineMapper, SchoolMapper schoolMapper, MajorMapper majorMapper) {
        this.recommendation = recommendation;
        this.admissionMapper = admissionMapper;
        this.scoreLineMapper = scoreLineMapper;
        this.schoolMapper = schoolMapper;
        this.majorMapper = majorMapper;
    }

    private List<RecommendedResult> filter(List<RecommendedResult> list, RecommendParam param) {
        List<RecommendedResult> result = new ArrayList<>(list);
        if (param.getSchoolConditions() != null || param.getSchoolKeyword() != null){
            School school = SchoolConditions.wrapConditions(param.getSchoolConditions());

            List<School> schools = schoolMapper.findByConditions(school, param.getSchoolKeyword());
            List<Integer> schoolIds = schools.stream()
                    .map(School::getSchoolId)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());


            result.removeIf(item -> !schoolIds.contains(item.getSchoolId()));
        }
        if (param.getMajorConditions() != null || param.getMajorKeyword() != null) {
            Major major = MajorConditions.wrapConditions(param.getMajorConditions());
            List<Major> majors = majorMapper.findByConditions(major, param.getMajorKeyword());
            List<Integer> majorIds = majors.stream()
                    .map(Major::getSpecialId)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            result.removeIf(item -> !majorIds.contains(item.getMajorId()));
        }
        return result;
    }

    @Override
    public RecommendPage recommendHighRisk(RecommendParam param) {
        RecommendedResults recommendedResults = recommendation.recommend(param.getStudentInfo());
        List<RecommendedResult> highRisk = recommendedResults.getHighRisk();
        List<RecommendedResult> result = filter(highRisk, param);
        return getPage(param, result);
    }

    @Override
    public RecommendPage recommendMidRisk(RecommendParam param) {
        RecommendedResults recommendedResults = recommendation.recommend(param.getStudentInfo());
        List<RecommendedResult> midRisk = recommendedResults.getMidRisk();
        List<RecommendedResult> result = filter(midRisk, param);
        return getPage(param, result);
    }

    @Override
    public RecommendPage recommendLowRisk(RecommendParam param) {
        RecommendedResults recommendedResults = recommendation.recommend(param.getStudentInfo());
        List<RecommendedResult> lowRisk = recommendedResults.getLowRisk();
        List<RecommendedResult> result = filter(lowRisk, param);
        return getPage(param, result);
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

    @Override
    public SchoolAdmissionPage schoolAdmission(SchoolAdmissionParam param) {
        List<MajorAdmission> list = recommendation.calculateSchoolAdmissionProbability(
                param.getSchoolId(), param.getStudentInfo());
        if (param.getPageNo() != null && param.getPageSize() != null) {
            Pageable pageable = PageRequest.of(param.getPageNo()-1, param.getPageSize());
            // 对缓存中的数据进行分页操作
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), list.size());
            SchoolAdmissionPage page = new SchoolAdmissionPage();
            page.setPageNo(param.getPageNo());
            page.setPageSize(param.getPageSize());
            page.setPageNum((int) Math.ceil((double) list.size()/param.getPageSize()));
            page.setTotalCount(list.size());
            page.setMajorAdmissions(list.subList(start, end));
            return page;
        } else {
            SchoolAdmissionPage page = new SchoolAdmissionPage();
            page.setMajorAdmissions(list);
            return page;
        }
    }

    @Override
    public ScoreLines getScoreLines(int schoolId, String province, int subject) {
        List<ScoreLine> scoreLineList;
        scoreLineList = scoreLineMapper.findBySchoolIdAndProvinceNameAndSubject(
                schoolId, Province.getIdByName(province), subject);

        List<Integer> scores = new ArrayList<>();
        List<Integer> ranks = new ArrayList<>();
        List<Integer> years = new ArrayList<>();
        scoreLineList.sort(Comparator.comparingInt(ScoreLine::getYear));

        for (ScoreLine scoreLine : scoreLineList) {
            scores.add(scoreLine.getScore());
            ranks.add(scoreLine.getRank());
            years.add(scoreLine.getYear());
        }
        return new ScoreLines(scores, ranks, years);
    }
}
