package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.questionbrushingplatform.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【question(题目表)】的数据库操作Service
* @createDate 2024-09-16 17:07:21
*/
public interface QuestionService extends IService<Question> {
    void addQuestion(Question question);
    void addQuestions(List<Question> questions);
    void deleteQuestion(Long id);
    void updateQuestion(Question question);
    Question getQuestionById(Long id);
    Question getQuestionByTitle(String title);
    Page<Question> listQuestions(Integer pageNum, Integer pageSize);
    List<Question> listQuestions();
    List<Question> listQuestionsByIds(List<Long> ids);
}
