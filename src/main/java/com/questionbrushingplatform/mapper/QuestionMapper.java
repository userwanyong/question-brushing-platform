package com.questionbrushingplatform.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.questionbrushingplatform.pojo.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 查询题目列表（包括已被删除的题目）
     * @param minUpdateTime
     * @return
     */
    @Select("select * from question where updateTime >= #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date minUpdateTime);

}
