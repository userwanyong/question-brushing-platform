package com.questionbrushingplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 永
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

    private String nickname;

    private String userAvatar;

}
