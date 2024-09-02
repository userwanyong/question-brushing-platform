package com.questionbrushingplatform.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserUpdatePasswordDTO {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
