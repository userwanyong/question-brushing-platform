package com.questionbrushingplatform.pojo.dto;

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
public class UserUpdatePasswordDTO {

    private Long id;

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}
