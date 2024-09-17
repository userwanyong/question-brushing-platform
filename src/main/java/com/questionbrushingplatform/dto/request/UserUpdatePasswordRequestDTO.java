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
public class UserUpdatePasswordRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("oldPassword")
    private String oldPassword;

    @JsonProperty("newPassword")
    private String newPassword;

    @JsonProperty("confirmPassword")
    private String confirmPassword;
}
