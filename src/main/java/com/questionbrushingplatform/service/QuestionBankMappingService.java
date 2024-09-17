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
}
