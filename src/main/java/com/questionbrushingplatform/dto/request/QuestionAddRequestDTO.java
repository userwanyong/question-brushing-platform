package com.questionbrushingplatform.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAddRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String title;

    private String content;

    private String answer;

    @NotBlank
    private String userId;

    private List<Integer> tags;

}