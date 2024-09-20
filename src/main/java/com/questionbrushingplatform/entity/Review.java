package com.questionbrushingplatform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 审核表
 * @author 永
 * @TableName review
 */
@TableName(value ="review")
@Data
public class Review implements Serializable {
    /**
     * 唯一标识
     */
    @TableId
    private Long id;

    /**
     * 题目唯一标识
     */
    private Long questionId;

    /**
     * 审核人唯一标识
     */
    private Long reviewerId;

    /**
     * 审核状态 0 - 待审核 1 - 通过 2 - 拒绝
     */
    private Integer status;

    /**
     * 审核信息
     */
    private String reviewMsg;

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
    private Integer isDelete;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}