package com.questionbrushingplatform.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 题目表
 * @author 永
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态 0-待审核 1-通过 2-拒绝
     */
    private Integer status;

    /**
     * 推荐答案
     */
    private String answer;

    /**
     * 创建用户id
     */
    @JsonProperty("user_id")
    private Long userId;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 创建时间
     */
    @JsonProperty("created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @JsonProperty("updated_time")
    private Date updatedTime;

    /**
     * 是否删除
     */
    @TableLogic
    @JsonProperty("is_delete")
    private Integer isDelete;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}