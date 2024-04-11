package com.nju.urs.recommendation.service.impl;

import com.nju.urs.recommendation.model.dto.RecommendedResult;
import com.nju.urs.common.model.dto.StudentInfo;
import com.nju.urs.dao.mysql.mapper.AdmissionMapper;
import com.nju.urs.dao.mysql.mapper.SchoolMajorMapper;
import com.nju.urs.dao.mongo.mapper.SchoolMapper;
import com.nju.urs.dao.mysql.model.po.Admission;
import com.nju.urs.dao.mysql.model.po.SchoolMajor;
import com.nju.urs.recommendation.model.dto.RecommendedResults;
import com.nju.urs.recommendation.model.vo.SimpleAdmission;
import com.nju.urs.recommendation.service.Recommendation;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
            SchoolMajor schoolMajor = schoolMajorMapper.findBySchoolIdAndMajorId(
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

    public static double calculateAverage(List<Double> list) {
        double total = 0;

        // 遍历列表，累加所有元素
        for (double num : list) {
            total += num;
        }

        // 计算平均值
        return total / list.size();
    }

    // 计算标准差
    public static double calculateStdDev(List<Double> numbers) {
        int n = numbers.size();
        if (n < 2) {
            throw new IllegalArgumentException("Standard deviation requires at least two data points");
        }

        // Step 1: Calculate the mean
        double mean = calculateAverage(numbers);

        // Step 2: Calculate the sum of squared differences from the mean
        double squaredDiffSum = 0;
        for (Double num : numbers) {
            squaredDiffSum += Math.pow(num - mean, 2);
        }

        // Step 3: Calculate the variance
        double variance = squaredDiffSum / (n - 1);

        // Step 4: Calculate the standard deviation
        return Math.sqrt(variance);
    }

    // 正态分布，使用最小值作为 mu，计算 delta
    public static double calculateDelta(List<Double> numbers) {
        int n = numbers.size();
        if (n < 2) {
            throw new IllegalArgumentException("Standard deviation requires at least two data points");
        }

        // Step 1: 以最低录取位次为 mu
        double mu = numbers.stream().max(Double::compare).orElse(Double.NaN);

        // Step 2: Calculate the sum of squared differences from the mean
        double squaredDiffSum = 0;
        for (Double num : numbers) {
            squaredDiffSum += Math.pow(num - mu, 2);
        }

        // Step 3: Calculate the variance
        double variance = squaredDiffSum / (n - 1);

        // Step 4: Calculate the standard deviation
        return Math.sqrt(variance);
    }

    // 使用正态分布函数计算考生被录取的概率
    public static double calProbabilityByNormalDistribution(List<Double> ranks, int studentRank) {
        // 计算过去三年录取最低位次的平均值和标准差
        double mu = ranks.stream().max(Double::compare).orElse(Double.NaN);
        double delta = calculateDelta(ranks);

        // 使用正态分布函数计算考生被录取的概率
        NormalDistribution normalDistribution = new NormalDistribution(mu, delta);

        return 1 - normalDistribution.cumulativeProbability(studentRank)
                + normalDistribution.density(studentRank);
    }

    // 简单计算录取概率
    private double calProbability(List<Double> ranks, int studentRank) {
        // p-高校近n年内的投档线对应省排名大于目标排名的年份数占比
        // y-大于目标分数的年份数量
        // n-总年份
        int p = 0, y = 0, n = 0;

        // d-高校投档线对应省排名的标准差
        double d = 0;
        double s = calculateAverage(ranks);
        for (double rank : ranks) {
            n++;
            if (rank > studentRank) {
                y++;
            }
            d = d + Math.pow(((rank - s)/s), 2);
        }
        p = y / n;

        return p * 0.88 + (1 - d) * 0.12;
    }

    @Override
    public List<RecommendedResult> allRecommend(StudentInfo studentInfo) {
        List<RecommendedResult> results = new ArrayList<>();
        Map<SchoolMajor, List<SimpleAdmission>> map = Preprocessing(studentInfo);

        for (SchoolMajor schoolMajor : map.keySet()) {
            List<SimpleAdmission> admissions = map.get(schoolMajor);

            List<Double> ranks = admissions.stream()
                    .map(obj -> (double) ((SimpleAdmission) obj).getRank())
                    .collect(Collectors.toList());


            double admissionProbability = calProbabilityByNormalDistribution(ranks, studentInfo.getRank());

            RecommendedResult result = new RecommendedResult();
            result.setSchoolId(schoolMajor.getSchoolId());
            result.setMajorId(schoolMajor.getMajorId());
            result.setMajorName(schoolMajor.getMajorName());
            result.setAdmissionProbability(admissionProbability);
            result.setSchoolName(schoolMapper.selectById(schoolMajor.getSchoolId()).getSchoolName());
            results.add(result);
        }
        return results;
    }

    @Override
    public RecommendedResults recommend(StudentInfo studentInfo) {
        List<RecommendedResult> results = allRecommend(studentInfo);
        RecommendedResults recommendedResults = new RecommendedResults();
        List<RecommendedResult> highRisk = new ArrayList<>();
        List<RecommendedResult> mediumRisk = new ArrayList<>();
        List<RecommendedResult> lowRisk = new ArrayList<>();
        for (RecommendedResult result : results) {
            if (result.getAdmissionProbability() < 0.5) {
                highRisk.add(result);
            } else if (result.getAdmissionProbability() > 0.8) {
                lowRisk.add(result);
            } else {
                mediumRisk.add(result);
            }
        }
        recommendedResults.setHighRisk(highRisk);
        recommendedResults.setMediumRisk(mediumRisk);
        recommendedResults.setLowRisk(lowRisk);

        return recommendedResults;
    }
}
