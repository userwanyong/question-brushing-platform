package com.questionbrushingplatform.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 问题请求类
 * @author wenruohan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;

    private String content;

    private String answer;

    @JsonProperty("user_id")
    private String userId;

    private List<Integer> tags;

}
