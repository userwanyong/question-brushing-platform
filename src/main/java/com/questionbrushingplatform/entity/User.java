package com.questionbrushingplatform.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 永
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("nickname")
    private String nickname;

    @TableField("user_avatar")
    @JsonProperty("user_avatar")
    private String userAvatar;

    @TableField("user_profile")
    @JsonProperty("user_profile")
    private String userProfile;

    @TableField("user_role")
    @JsonProperty("user_role")
    private String userRole;

    @TableField("edit_time")
    @JsonProperty("edit_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime editTime;

    @TableField("created_time")
    @JsonProperty("created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @TableField("update_time")
    @JsonProperty("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField("is_delete")
    @JsonProperty("is_delete")
    @TableLogic
    private Integer isDelete;

}
