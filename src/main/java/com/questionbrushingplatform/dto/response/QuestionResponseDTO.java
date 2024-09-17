package com.questionbrushingplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 问题响应类
 * @author wenruohan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String content;

    private String answer;

    private String userId;

    private List<TagResponseDTO> tags;

}
