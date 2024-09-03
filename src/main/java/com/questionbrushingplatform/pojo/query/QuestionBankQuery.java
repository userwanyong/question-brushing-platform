package com.questionbrushingplatform.pojo.query;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionBankQuery extends PageQuery{

    private String title;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
