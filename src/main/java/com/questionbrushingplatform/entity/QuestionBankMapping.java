package com.questionbrushingplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName question_bank_mapping
 */
@TableName(value ="question_bank_mapping")
@Data
public class QuestionBankMapping implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Long questionId;

    /**
     * 
     */
    private Long bankId;

    /**
     * 
     */
    private Date createdTiam;

    /**
     * 
     */
    private Date updatedTime;

    /**
     * 是否删除 0 - 否 1 - 是
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}