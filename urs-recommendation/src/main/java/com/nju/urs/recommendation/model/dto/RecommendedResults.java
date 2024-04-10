package com.nju.urs.recommendation.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendedResults {
    // 可冲击，录取概率小于50%
    private List<RecommendedResult> highRisk;
    // 较稳妥，录取概率大于50%，小于80%
    private List<RecommendedResult> mediumRisk;
    // 可冲刺，录取概率大于80%
    private List<RecommendedResult> lowRisk;
}
