package com.nju.urs.service.service.impl;

import com.nju.urs.dao.mongo.mapper.ScoreMapper;
import com.nju.urs.dao.mongo.model.po.Inner.ScoreInner;
import com.nju.urs.dao.mongo.model.po.Score;
import com.nju.urs.service.model.dto.ScoreInfo;
import com.nju.urs.service.model.dto.ScoreRow;
import com.nju.urs.service.model.dto.ScoreTable;
import com.nju.urs.service.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    ScoreMapper scoreMapper;
    @Autowired
    public ScoreServiceImpl(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }

    @Override
    public ScoreTable getScoreTable(String province, String studentType, String scoreType) {
        Score score;
        if (scoreType != null && !scoreType.isEmpty()) {
            score = scoreMapper.findByProvinceAndStudentTypeAndScoreType(province, studentType, scoreType);
        } else {
            score = scoreMapper.findByProvinceAndStudentType(province, studentType);
        }

        return new ScoreTable(score);
    }

    @Override
    public ScoreInfo getScoreInfo(String score, String province, String studentType, String scoreType) {
        Score scorePO;
        if (scoreType != null && !scoreType.isEmpty()) {
            scorePO = scoreMapper.findByProvinceAndStudentTypeAndScoreType(province, studentType, scoreType);
        } else {
            scorePO = scoreMapper.findByProvinceAndStudentTypeAndScoreType(province, studentType, "-");
        }
        
        ScoreInfo scoreInfo = null;
        for (ScoreInner scoreInner : scorePO.getData()) {
            Integer[] scores = Arrays.stream(scoreInner.getScore().split("-"))
                    .map(Integer::parseInt).toArray(Integer[]::new);
            if (scoreInner.getScore().equals(score) || (scores.length == 2
                    && Integer.parseInt(score) >= scores[0] && Integer.parseInt(score) <= scores[1])) {
                scoreInfo = new ScoreInfo(scoreInner);
            }
        }
        return scoreInfo;
    }

    @Override
    public List<ScoreRow> getScoreRows(String province, String studentType, String scoreType) {
        Score score;
        if (scoreType != null && !scoreType.isEmpty()) {
            score = scoreMapper.findByProvinceAndStudentTypeAndScoreType(province, studentType, scoreType);
        } else {
            score = scoreMapper.findByProvinceAndStudentType(province, studentType);
        }

        List<ScoreRow> scoreRows = new ArrayList<>();
        for (ScoreInner scoreInner : score.getData()) {
            ScoreRow scoreRow = new ScoreRow(scoreInner);
            scoreRows.add(scoreRow);
        }

        return scoreRows;
    }

}
