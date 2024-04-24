package com.nju.urs.dao.mysql.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("score_line")
public class ScoreLine {
    private Integer schoolId;
    private Integer provinceId;
    private Integer subject;
    private Integer year;
    private Integer score;
    private Integer minRank;
}
