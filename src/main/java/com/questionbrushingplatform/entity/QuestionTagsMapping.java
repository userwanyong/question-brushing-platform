package com.questionbrushingplatform.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName question_tags_mapping
 */
@TableName(value ="question_tags_mapping")
@Data
public class QuestionTagsMapping implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 题目ID
     */
    @JsonProperty("question_id")
    private Long questionId;

    /**
     * 标签ID
     */
    @JsonProperty("tag_id")
    private Integer tagId;

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
    @TableLogic
    @JsonProperty("is_delete")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}