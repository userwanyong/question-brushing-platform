package com.questionbrushingplatform.service;

import com.questionbrushingplatform.entity.QuestionTagsMapping;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【question_tags_mapping】的数据库操作Service
* @createDate 2024-09-16 17:07:01
*/
public interface QuestionTagsMappingService extends IService<QuestionTagsMapping> {
    void addQuestionTagsMapping(QuestionTagsMapping questionTagsMapping);
    void addQuestionTagsMappings(List<QuestionTagsMapping> questionTagsMappings);
    void deleteQuestionTagsMapping(Integer id);
    void updateQuestionTagsMapping(List<QuestionTagsMapping> questionTagsMappings);
    QuestionTagsMapping getQuestionTagsMappingById(Integer id);
    List<QuestionTagsMapping> listQuestionTagsMappings();
    List<QuestionTagsMapping> listQuestionTagsMappingsByQuestionId(Long questionId);
    List<QuestionTagsMapping> listQuestionTagsMappingsByTagId(Integer tagId);
    List<QuestionTagsMapping> listQuestionTagsMappingsByTagIdAndQuestionId(Integer tagId, Integer questionId);
}
