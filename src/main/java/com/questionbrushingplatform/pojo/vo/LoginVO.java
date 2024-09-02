package com.questionbrushingplatform.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class LoginVO {

    private Integer id;
    private String userAccount;
    private String userPassword;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;
    private LocalDateTime editTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String token;

}

