package com.nju.urs.service.model.dto;

import com.nju.urs.recommendation.model.vo.RecommendedResult;
import lombok.Data;

import java.util.List;

@Data
public class RecommendPage {
    private Integer pageNo;
    private Integer pageSize;
    private Integer pageNum;
    private Integer totalCount;
    private List<RecommendedResult> recommend;
}
