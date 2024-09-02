package com.questionbrushingplatform.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class LoginAndRegisterDTO{

    @TableField("userAccount")
    private String userAccount;

    @TableField("userPassword")
    private String userPassword;


}
