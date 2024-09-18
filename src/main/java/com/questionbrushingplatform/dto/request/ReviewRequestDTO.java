package com.questionbrushingplatform.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.questionbrushingplatform.dto.response.QuestionResponseDTO;
import com.questionbrushingplatform.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 审核请求DTO
 * @author wenruohan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 审核人
     */
    @JsonProperty("reviewer_id")
    private String reviewerId;

    /**
     * 审核信息
     */
    @JsonProperty("review_msg")
    private String reviewMsg;

    /**
     * 题目信息
     */
    private QuestionRequestDTO questionInfo;

    /**
     * 题目优先级
     */
    private Integer priority;

}
