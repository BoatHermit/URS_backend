package com.nju.urs.recommendation.service.impl;

import com.nju.urs.recommendation.model.dto.RecommendedResult;
import com.nju.urs.common.model.dto.StudentInfo;
import com.nju.urs.dao.mapper.AdmissionMapper;
import com.nju.urs.dao.mapper.SchoolMajorMapper;
import com.nju.urs.dao.mapper.SchoolMapper;
import com.nju.urs.dao.model.po.Admission;
import com.nju.urs.dao.model.po.SchoolMajor;
import com.nju.urs.recommendation.model.vo.SimpleAdmission;
import com.nju.urs.recommendation.service.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationImpl implements Recommendation {

    AdmissionMapper admissionMapper;
    SchoolMapper schoolMapper;
    SchoolMajorMapper schoolMajorMapper;

    @Autowired
    public RecommendationImpl(SchoolMajorMapper schoolMajorMapper,
                              SchoolMapper schoolMapper, AdmissionMapper admissionMapper) {
        this.schoolMajorMapper = schoolMajorMapper;
        this.schoolMapper = schoolMapper;
        this.admissionMapper = admissionMapper;
    }

    private boolean checkSubjects(String subjects, String requirement) {
        for (int i = 0; i < subjects.length(); i++) {
            if (requirement.charAt(i) == '1' && subjects.charAt(i) == '0') {
                return false;
            }
        }
        return true;
    }

    private Map<SchoolMajor, List<SimpleAdmission>> Preprocessing(StudentInfo studentInfo) {
        Map<SchoolMajor, List<SimpleAdmission>> map = new HashMap<>();

        List<Admission> admissions = admissionMapper.selectByProvince(studentInfo.getProvince());
        for (Admission admission : admissions) {
            SchoolMajor schoolMajor = schoolMajorMapper.selectBySchoolIdAndMajorId(
                    admission.getSchoolId(), admission.getMajorId());
            if (checkSubjects(studentInfo.getSubjects(), schoolMajor.getSubjects())) {
                if (!map.containsKey(schoolMajor)) {
                    List<SimpleAdmission> simpleAdmissions = new ArrayList<>();
                    map.put(schoolMajor, simpleAdmissions);
                }
                SimpleAdmission sAdmission = new SimpleAdmission(
                        admission.getRank(), admission.getScore(), admission.getYear());
                map.get(schoolMajor).add(sAdmission);
            }
        }

        return map;
    }


    @Override
    public List<RecommendedResult> recommend(StudentInfo studentInfo) {

        List<RecommendedResult> results = new ArrayList<>();
        Map<SchoolMajor, List<SimpleAdmission>> map = Preprocessing(studentInfo);

        for (SchoolMajor schoolMajor : map.keySet()) {
            List<SimpleAdmission> admissions = map.get(schoolMajor);

            // p-高校近n年内的投档线对应省排名大于目标排名的年份数占比
            // y-大于目标分数的年份数量
            // n-总年份
            int p = 0, y = 0, n = 0;

            // d-高校投档线对应省排名的标准差
            double d = 0;
            double s = admissions.stream().mapToInt(SimpleAdmission::getRank).average().getAsDouble();
            for (SimpleAdmission admission : admissions) {
                n++;
                if (admission.getRank() > studentInfo.getRank()) {
                    y++;
                }
                d = d + Math.pow(((admission.getRank() - s)/s), 2);
            }
            p = y / n;

            double rate = p * 0.88 + (1 - d) * 0.12;

            RecommendedResult result = new RecommendedResult();
            result.setSchoolId(schoolMajor.getSchoolId());
            result.setMajorId(schoolMajor.getMajorId());
            result.setMajorName(schoolMajor.getMajorName());
            result.setAdmissionRate(rate);
            result.setSchoolName(schoolMapper.selectById(schoolMajor.getSchoolId()).getName());
            results.add(result);
        }

        return results;
    }
}
