package com.questionbrushingplatform.service;

import com.questionbrushingplatform.entity.QuestionBankMapping;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【question_bank_mapping】的数据库操作Service
* @createDate 2024-09-17 18:23:45
*/
public interface QuestionBankMappingService extends IService<QuestionBankMapping> {

    List<QuestionBankMapping> listByBankId(Long bankId);

    /**
     * 通过题目id和题库id获取题目题库映射
     * @param questionId
     * @param bankId
     * @return
     */
    QuestionBankMapping getQuestionBankMappingById(Long questionId, Long bankId);
}
