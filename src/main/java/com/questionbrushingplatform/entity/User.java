package com.questionbrushingplatform.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 永
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("user_account")
    private String userAccount;

    @TableField("user_password")
    private String userPassword;

    @TableField("user_name")
    private String userName;

    @TableField("user_avatar")
    private String userAvatar;

    @TableField("user_profile")
    private String userProfile;

    @TableField("user_role")
    private String userRole;

    @TableField("edit_time")
    private Date editTime;

    @TableField("created_time")
    private Date createdTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("is_delete")
    private Integer isDelete;

}
