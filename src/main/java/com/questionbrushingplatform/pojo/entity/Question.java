package com.questionbrushingplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("tags")
    private String tags;

    @TableField("answer")
    private String answer;

    @TableField("userId")
    private Long userId;

    @TableField("editTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime editTime;

    @TableField("createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField("updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;

    @TableField("reviewStatus")
    private Integer reviewStatus;

    @TableField("reviewMessage")
    private String reviewMessage;

    @TableField("reviewerId")
    private Long reviewerId;

    @TableField("reviewTime")
    private LocalDateTime reviewTime;

    @TableField("priority")
    private Integer priority;

    @TableField("questionBankId")
    private Integer questionBankId;


}
