package com.nju.urs.recommendation.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class RecommendedResults {
    // 可冲击，录取概率小于30%
    private List<RecommendedResult> highRisk;
    // 较稳妥，录取概率大于30%，小于80%
    private List<RecommendedResult> midRisk;
    // 可冲刺，录取概率大于80%
    private List<RecommendedResult> lowRisk;
}
