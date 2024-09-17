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
public class LoginAndRegisterRequestDTO {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;


}
