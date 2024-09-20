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
     * 根据题库 id 获取题目总数
     * @param ids 题库 id
     * @return 题目总数
     */
    Long listByBankIds(Long[] ids);


    /**
     * 根据题目id和题库id获取题目题库映射
     * @param questionId 题目id
     * @param bankId 题库id
     * @return 题目题库映射
     */
    QuestionBankMapping getQuestionBankMappingById(Long questionId, Long bankId);


}
