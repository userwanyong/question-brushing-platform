package com.questionbrushingplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("question_bank")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("picture")
    private String picture;

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

    @TableField("priority")
    private Integer priority;


}
