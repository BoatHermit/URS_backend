package com.nju.urs.recommendation.service.impl;

import com.nju.urs.dao.mysql.mapper.SchoolCodeMapper;
import com.nju.urs.recommendation.model.dto.RecommendedResult;
import com.nju.urs.common.model.dto.StudentInfo;
import com.nju.urs.dao.mysql.mapper.AdmissionMapper;
import com.nju.urs.dao.mysql.mapper.SchoolMajorMapper;
import com.nju.urs.dao.mysql.model.po.Admission;
import com.nju.urs.dao.mysql.model.po.SchoolMajor;
import com.nju.urs.recommendation.model.dto.RecommendedResults;
import com.nju.urs.recommendation.model.vo.SimpleAdmission;
import com.nju.urs.recommendation.service.Recommendation;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationImpl implements Recommendation {

    AdmissionMapper admissionMapper;
    SchoolCodeMapper schoolCodeMapper;
    SchoolMajorMapper schoolMajorMapper;


    @Autowired
    public RecommendationImpl(SchoolMajorMapper schoolMajorMapper,
                              SchoolCodeMapper schoolCodeMapper, AdmissionMapper admissionMapper) {
        this.schoolMajorMapper = schoolMajorMapper;
        this.schoolCodeMapper = schoolCodeMapper;
        this.admissionMapper = admissionMapper;
    }


    private boolean checkSubjects(String subjects, String requirement) {
        boolean result = true;
        for (int i = 0; i < subjects.length(); i++) {
            if (requirement.charAt(i) == '1' && subjects.charAt(i) == '0') {
                result = false;
                break;
            }
        }
        return result;
    }


    private Map<SchoolMajor, List<SimpleAdmission>> preprocessing(StudentInfo studentInfo) {
        Map<SchoolMajor, List<SimpleAdmission>> map = new HashMap<>();

        List<Admission> admissions = admissionMapper.findByProvince(studentInfo.getProvince());
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


    /**
     * 二次指数平滑法求预测值
     * @param list 数据集合
     * @param n 未来第几期
     * @param modulus 平滑系数
     * @return 预测值
     */
    public Double predictByES(List<Double> list, int n, Double modulus) {
        if (modulus <= 0 || modulus >= 1) {
            return null;
        }
        Double modulusLeft = 1 - modulus;
        Double lastIndex = list.get(0);
        Double lastSecIndex = list.get(0);
        for (Double data :list) {
            lastIndex = modulus * data + modulusLeft * lastIndex;
            lastSecIndex = modulus * lastIndex + modulusLeft * lastSecIndex;
        }
        double a = 2 * lastIndex - lastSecIndex;
        double b = (modulus / modulusLeft) * (lastIndex - lastSecIndex);
        return a + b * n;
    }


    /**
     * 计算正态分布中的 delta
     * @param mu mu
     * @param numbers 数据
     * @return delta
     */
    public double calculateDelta(double mu, List<Double> numbers) {
        int n = numbers.size();
        if (n < 2) {
            throw new IllegalArgumentException("Standard deviation requires at least two data points");
        }

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

    private static double sigmoid(double x) {
        double k = Math.PI;
        return 1.0 / (1 + Math.exp(-k*(x-0.5)));
    }


    /**
     * 使用正态分布函数计算考生被录取的概率
     * @param ranks 历史录取最低位次
     * @param studentRank 考生位次
     * @return 录取概率
     */
    public double calculateProbabilityByND(List<Double> ranks, int studentRank) {
        // 以最低位次为 mu
        // double mu = ranks.stream().max(Double::compare).orElse(Double.NaN);
        double prediction = predictByES(ranks,1, 0.5);
        // ranks.add(prediction);
        double delta = calculateDelta(prediction, ranks);

        // 使用正态分布函数计算考生被录取的概率
        NormalDistribution normalDistribution = new NormalDistribution(prediction, delta);
        double probability = 1 - normalDistribution.cumulativeProbability(studentRank)
                + normalDistribution.density(studentRank);
        probability = sigmoid(probability);
        // 创建 DecimalFormat 对象，并指定保留两位小数的格式
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // 使用 DecimalFormat 格式化 Double 数值
        probability = Double.parseDouble(decimalFormat.format(probability));
        return probability;
    }


    @Override
    public List<RecommendedResult> allRecommend(StudentInfo studentInfo) {
        List<RecommendedResult> results = new ArrayList<>();
        Map<SchoolMajor, List<SimpleAdmission>> map = preprocessing(studentInfo);

        for (SchoolMajor schoolMajor : map.keySet()) {
            List<SimpleAdmission> admissions = map.get(schoolMajor);
            Collections.sort(admissions);

            List<Double> ranks = admissions.stream()
                    .map(obj -> (double) ((SimpleAdmission) obj).getRank())
                    .collect(Collectors.toList());


            double admissionProbability = calculateProbabilityByND(ranks, studentInfo.getRank());

            RecommendedResult result = new RecommendedResult();
            result.setSchoolId(schoolMajor.getSchoolId());
            result.setMajorId(schoolMajor.getMajorId());
            result.setMajorName(schoolMajor.getMajorName());
            result.setAdmissionProbability(admissionProbability);
            result.setSchoolName(schoolCodeMapper.selectById(String.valueOf(schoolMajor.getSchoolId()))
                    .getName());
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
            if (result.getAdmissionProbability() < 0.3) {
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
