package com.questionbrushingplatform.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class UserDTO{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;


    @TableField("userName")
    private String userName;

    @TableField("userAvatar")
    private String userAvatar;

    @TableField("userProfile")
    private String userProfile;


}