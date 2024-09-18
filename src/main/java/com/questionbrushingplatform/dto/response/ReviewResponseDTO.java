package com.questionbrushingplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 审核响应类
 * @author wenruohan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * 审核人信息
     */
    private UserInfoResponseDTO reviewer;

    /**
     * 审核信息
     */
    private String reviewMsg;

    /**
     * 题目信息
     */
    private QuestionResponseDTO questionInfo;

    /**
     * 优先级
     */
    private Integer priority;

}
