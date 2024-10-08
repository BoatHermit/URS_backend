package com.nju.urs.recommendation.service.impl;

import com.nju.urs.common.annotation.DoubleCache;
import com.nju.urs.common.enums.Province;
import com.nju.urs.dao.mongo.mapper.SchoolMapper;
import com.nju.urs.dao.mongo.model.vo.SimpleSchool;
import com.nju.urs.dao.mysql.mapper.SchoolCodeMapper;
import com.nju.urs.dao.mysql.model.vo.SimpleAdmission;
import com.nju.urs.recommendation.model.vo.*;
import com.nju.urs.dao.mysql.mapper.AdmissionMapper;
import com.nju.urs.dao.mysql.mapper.SchoolMajorMapper;
import com.nju.urs.dao.mysql.model.po.Admission;
import com.nju.urs.dao.mysql.model.po.SchoolMajor;
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
    SchoolMapper schoolMapper;

    @Autowired
    public RecommendationImpl(SchoolMajorMapper schoolMajorMapper,
                              SchoolCodeMapper schoolCodeMapper,
                              AdmissionMapper admissionMapper,
                              SchoolMapper schoolMapper) {
        this.schoolMajorMapper = schoolMajorMapper;
        this.schoolCodeMapper = schoolCodeMapper;
        this.admissionMapper = admissionMapper;
        this.schoolMapper = schoolMapper;
    }


    private boolean checkSubjects(String subjects, SchoolMajor schoolMajor) {
        boolean result = true;
        StringBuilder  requirement = new StringBuilder(schoolMajor.getRequirement());
        int subject = schoolMajor.getSubject();
        if (subject == 1) {
            requirement.setCharAt(0, '1');
        } else if (subject == 2) {
            requirement.setCharAt(0, '2');
        } else if (subject == 3) {
            requirement = new StringBuilder("111000");
        } else if (subject == 4) {
            requirement = new StringBuilder("000111");
        } else if (subject == 5) {
            requirement = new StringBuilder("000000");
        }
        for (int i = 0; i < subjects.length(); i++) {
            if (requirement.charAt(i) == '1' && subjects.charAt(i) == '0') {
                result = false;
                break;
            }
        }
        return result;
    }

    private Map<SchoolMajor, List<SimpleAdmission>> wrapAdmissions(List<Admission> admissions, StudentInfo studentInfo) {
        Map<SchoolMajor, List<SimpleAdmission>> map = new HashMap<>();

        for (Admission admission : admissions) {
            SchoolMajor schoolMajor = schoolMajorMapper.findById(admission.getSchoolMajorId());
            if (schoolMajor != null && checkSubjects(studentInfo.getSubjects(), schoolMajor)) {
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


    private Map<SchoolMajor, List<SimpleAdmission>> preprocessing(StudentInfo studentInfo) {
        List<Admission> admissions = admissionMapper.findByProvinceId(Province.getIdByName(studentInfo.getProvince()));
        Map<SchoolMajor, List<SimpleAdmission>> map = wrapAdmissions(admissions, studentInfo);

        Iterator<Map.Entry<SchoolMajor, List<SimpleAdmission>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<SchoolMajor, List<SimpleAdmission>> entry = iterator.next();
            List<SimpleAdmission> admissionList = entry.getValue();
            boolean shouldRemove = true;
            for (SimpleAdmission admission : admissionList) {
                if (admission.getRank() > 0 && Math.abs(admission.getRank()-studentInfo.getRank()) < 5000) {
                    shouldRemove = false;
                    break;
                }
            }
            if (shouldRemove) {
                iterator.remove();
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
        double k = 150;
        return 0.5*(1/k)*Math.tan(Math.atan(k)*(2*(x-0.5)))+0.5;
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
        ranks.add(prediction);
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

    public double calculateProbability(List<SimpleAdmission> admissions, int studentRank) {
        Collections.sort(admissions);

        List<Double> ranks = admissions.stream()
                .map(obj -> (double) obj.getRank())
                .filter(rank -> rank > 0)
                .collect(Collectors.toList());

        if (ranks.size() > 1) {
            return calculateProbabilityByND(ranks, studentRank);
        } else if (ranks.size() == 1) {
            int rank = ranks.get(0).intValue();
            if (studentRank < rank*0.5) {
                return 1.0;
            } else if (studentRank < rank*0.6 && studentRank >= rank*0.5) {
                return 0.9;
            } else if (studentRank < rank*0.7 && studentRank >= rank*0.6) {
                return 0.8;
            } else if (studentRank < rank*0.8 && studentRank >= rank*0.7) {
                return 0.7;
            } else if (studentRank < rank*0.9 && studentRank >= rank*0.8) {
                return 0.6;
            } else if (studentRank < rank*1.0 && studentRank >= rank*0.9) {
                return 0.5;
            } else if (studentRank < rank*1.1 && studentRank >= rank*1.0) {
                return 0.4;
            } else if (studentRank < rank*1.2 && studentRank >= rank*1.1) {
                return 0.3;
            } else if (studentRank < rank*1.3 && studentRank >= rank*1.2) {
                return 0.2;
            } else if (studentRank < rank*1.4 && studentRank >= rank*1.3) {
                return 0.1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    @Override
    public List<RecommendedResult> allRecommend(StudentInfo studentInfo) {
        List<RecommendedResult> results = new ArrayList<>();
        Map<SchoolMajor, List<SimpleAdmission>> map = preprocessing(studentInfo);

        for (SchoolMajor schoolMajor : map.keySet()) {
            List<SimpleAdmission> admissions = map.get(schoolMajor);

            double admissionProbability = calculateProbability(admissions, studentInfo.getRank());

            if (admissionProbability > 0) {
                RecommendedResult result = new RecommendedResult();

                result.setSchoolId(schoolMajor.getSchoolId());
                result.setSchoolInfo(new SimpleSchool(
                        schoolMapper.findOneBySchoolId(String.valueOf(schoolMajor.getSchoolId()))));
                result.setMajorId(schoolMajor.getMajorId());
                result.setMajorCode(schoolMajor.getMajorCode());
                result.setMajorName(schoolMajor.getMajorName());
                result.setAdmissionProbability(admissionProbability);
                result.setAdmissions(admissions);
                results.add(result);
            }
        }
        results.sort(Comparator.comparingDouble(RecommendedResult::getAdmissionProbability));
        return results;
    }

    @Override
    @DoubleCache(cacheName = "RecommendedResults", key = "#studentInfo")
    public RecommendedResults recommend(StudentInfo studentInfo) {
        List<RecommendedResult> results = allRecommend(studentInfo);
        RecommendedResults recommendedResults = new RecommendedResults();
        List<RecommendedResult> highRisk = new ArrayList<>();
        List<RecommendedResult> mediumRisk = new ArrayList<>();
        List<RecommendedResult> lowRisk = new ArrayList<>();
        for (RecommendedResult result : results) {
            if (result.getAdmissionProbability() < 0.5 && result.getAdmissionProbability() >= 0.2) {
                highRisk.add(result);
            } else if (result.getAdmissionProbability() >= 0.5 && result.getAdmissionProbability() < 0.7) {
                mediumRisk.add(result);
            } else if (result.getAdmissionProbability() >= 0.7 && result.getAdmissionProbability() < 1.0) {
                lowRisk.add(result);
            }
        }
        recommendedResults.setHighRisk(highRisk);
        recommendedResults.setMidRisk(mediumRisk);
        recommendedResults.setLowRisk(lowRisk);

        return recommendedResults;
    }

    @Override
    @DoubleCache(cacheName = "SchoolAdmissionProbability", key = "#schoolId,#studentInfo")
    public List<MajorAdmission> calculateSchoolAdmissionProbability(Integer schoolId, StudentInfo studentInfo) {
        List<Admission> admissions = admissionMapper.findBySchoolIdAndProvinceId(
                schoolId, Province.getIdByName(studentInfo.getProvince()));
        Map<SchoolMajor, List<SimpleAdmission>> admissionsMap = wrapAdmissions(admissions, studentInfo);

        List<MajorAdmission> majorAdmissions = new ArrayList<>();
        admissionsMap.forEach((k, v) -> {
            MajorAdmission majorAdmission = new MajorAdmission();
            majorAdmission.setMajorCode(String.valueOf(k.getMajorCode()));
            majorAdmission.setMajorName(k.getMajorName());
            majorAdmission.setAdmissions(v);
            majorAdmission.setAdmissionProbability(calculateProbability(v, studentInfo.getRank()));
            majorAdmissions.add(majorAdmission);
        });

        return majorAdmissions;
    }
}
