package com.questionbrushingplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 
 * @TableName question_bank_mapping
 */
@TableName(value ="question_bank_mapping")
@Data
public class QuestionBankMapping implements Serializable {
    /**
     * 表id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 题目id
     */
    @JsonProperty("question_id")
    private Long questionId;

    /**
     * 题库id
     */
    @JsonProperty("bank_id")
    private Long bankId;

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
     * 是否删除 0 - 否 1 - 是
     */
    @JsonProperty("is_delete")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}