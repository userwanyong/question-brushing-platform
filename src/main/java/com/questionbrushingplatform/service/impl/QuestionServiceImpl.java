package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.entity.Question;
import com.questionbrushingplatform.service.QuestionService;
import com.questionbrushingplatform.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wenruohan
 * @description 针对表【question(题目表)】的数据库操作Service实现
 * @createDate 2024-09-16 17:07:21
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService{

    @Override
    public void addQuestion(Question question) {
        boolean result = save(question);
        if (!result) {
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    public void addQuestions(List<Question> questions) {
        boolean result = saveBatch(questions);
        if (!result) {
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    public void deleteQuestion(Long id) {
        boolean result = removeById(id);
        if (!result) {
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public void updateQuestion(Question question) {
        boolean result = updateById(question);
        if (!result) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    public Question getQuestionById(Long id) {
        return getById(id);
    }

    @Override
    public Question getQuestionByTitle(String title) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        return getOne(queryWrapper);
    }

    @Override
    public Page<Question> listQuestions(Integer pageNum, Integer pageSize) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        return page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Override
    public List<Question> listQuestions() {
        return list();
    }

    @Override
    public List<Question> listQuestionsByIds(List<Long> ids) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", ids);
        return list(queryWrapper);
    }

}