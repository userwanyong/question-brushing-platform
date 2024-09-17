package com.questionbrushingplatform.mapper;

import com.questionbrushingplatform.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
* @author wenruohan
* @description 针对表【question(题目表)】的数据库操作Mapper
* @createDate 2024-09-16 17:07:21
* @Entity com.questionbrushingplatform.pojo.entity.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 查询题目列表（包括已被删除的题目）
     * @param minUpdateTime
     * @return
     */
    @Select("select * from question where update_time >= #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date minUpdateTime);
}




