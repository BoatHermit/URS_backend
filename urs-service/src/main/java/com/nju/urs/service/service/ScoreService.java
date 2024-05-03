package com.nju.urs.service.service;

import com.nju.urs.service.model.dto.ScoreInfo;
import com.nju.urs.service.model.dto.ScoreTable;

public interface ScoreService {
    ScoreTable getScoreTable(String province, String studentType, String scoreType);
    ScoreInfo getScoreInfo(String score, String province, String studentType, String scoreType);
}
