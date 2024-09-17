package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.entity.QuestionTagsMapping;
import com.questionbrushingplatform.service.QuestionTagsMappingService;
import com.questionbrushingplatform.mapper.QuestionTagsMappingMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【question_tags_mapping】的数据库操作Service实现
* @createDate 2024-09-16 17:07:01
*/
@Service
public class QuestionTagsMappingServiceImpl extends ServiceImpl<QuestionTagsMappingMapper, QuestionTagsMapping>
    implements QuestionTagsMappingService{

    @Override
    public void addQuestionTagsMapping(QuestionTagsMapping questionTagsMapping) {
        boolean res = save(questionTagsMapping);
        if (!res) {
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    public void addQuestionTagsMappings(List<QuestionTagsMapping> questionTagsMappings) {
        boolean res = saveBatch(questionTagsMappings);
        if (!res) {
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    public void deleteQuestionTagsMapping(Integer id) {
        boolean res =  removeById(id);
        if (!res) {
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public void updateQuestionTagsMapping(List<QuestionTagsMapping> questionTagsMappings) {
        boolean res = saveOrUpdateBatch(questionTagsMappings);
        if (!res) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    public QuestionTagsMapping getQuestionTagsMappingById(Integer id) {
        return getById(id);
    }

    @Override
    public List<QuestionTagsMapping> listQuestionTagsMappings() {
        return list();
    }

    @Override
    public List<QuestionTagsMapping> listQuestionTagsMappingsByQuestionId(Long questionId) {
        QueryWrapper<QuestionTagsMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", questionId);
        return list(queryWrapper);
    }

    @Override
    public List<QuestionTagsMapping> listQuestionTagsMappingsByTagId(Integer tagId) {
        QueryWrapper<QuestionTagsMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_id", tagId);
        return list(queryWrapper);
    }

    @Override
    public List<QuestionTagsMapping> listQuestionTagsMappingsByTagIdAndQuestionId(Integer tagId, Integer questionId) {
        QueryWrapper<QuestionTagsMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_id", tagId);
        queryWrapper.eq("question_id", questionId);
        return list(queryWrapper);
    }
}




