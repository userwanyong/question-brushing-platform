package com.questionbrushingplatform.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQuery extends PageQuery{

    private Long id;
    private Long NotId;
    private String SearchText;
    private List<String> tags;
    private Long userId;
    private Long questionBankId;


}
