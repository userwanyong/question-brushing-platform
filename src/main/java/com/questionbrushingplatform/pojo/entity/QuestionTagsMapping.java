package com.questionbrushingplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
    private Long questionId;

    /**
     * 标签ID
     */
    private Integer tagId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 是否删除 0 - 否 1 - 是
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}