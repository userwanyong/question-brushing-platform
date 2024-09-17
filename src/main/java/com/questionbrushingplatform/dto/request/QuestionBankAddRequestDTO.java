package com.questionbrushingplatform.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 永
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankAddRequestDTO {


    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("priority")
    private Integer priority;


}
