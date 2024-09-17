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
public class UserRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("user_avatar")
    private String userAvatar;

    @JsonProperty("user_profile")
    private String userProfile;


}