package com.questionbrushingplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
    private LocalDateTime editTime;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateTime")
    private LocalDateTime updateTime;

    @TableField("isDelete")
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
