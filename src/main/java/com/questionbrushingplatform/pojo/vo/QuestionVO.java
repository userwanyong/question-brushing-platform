package com.questionbrushingplatform.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("question")
public class QuestionVO {
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



    @TableField("questionBankId")
    private Integer questionBankId;


}

