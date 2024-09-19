package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    /**
     * 根据题库 id 获取题目总数
     * @param ids
     * @return
     */
    public Long listByBankIds(Long[] ids) {
        LambdaQueryWrapper<QuestionBankMapping> queryWrapperQuestion = new LambdaQueryWrapper<>();
        queryWrapperQuestion.in(QuestionBankMapping::getBankId, ids);
        return count(queryWrapperQuestion);
    }

    /**
     * 根据题目id和银行id获取题目银行映射
     * @param questionId
     * @param bankId
     * @return
     */
    public QuestionBankMapping getQuestionBankMappingById(Long questionId, Long bankId) {
        //构建查询条件
        QueryWrapper<QuestionBankMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", questionId);
        queryWrapper.eq("bank_id", bankId);
        //执行查询并返回
        return getOne(queryWrapper);
    }


}




