package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.entity.QuestionBankMapping;
import com.questionbrushingplatform.service.QuestionBankMappingService;
import com.questionbrushingplatform.mapper.QuestionBankMappingMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【question_bank_mapping】的数据库操作Service实现
* @createDate 2024-09-17 18:23:45
*/
@Service
public class QuestionBankMappingServiceImpl extends ServiceImpl<QuestionBankMappingMapper, QuestionBankMapping>
    implements QuestionBankMappingService{

    @Override
    public List<QuestionBankMapping> listByBankId(Long bankId) {
        QueryWrapper<QuestionBankMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bank_id", bankId);
        return list(queryWrapper);
    }
}




