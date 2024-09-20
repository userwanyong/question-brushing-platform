package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.entity.QuestionBank;
import com.questionbrushingplatform.mapper.QuestionBankMapper;
import com.questionbrushingplatform.service.QuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author 永
 */
@Service
@Slf4j
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {

    @Autowired
    private QuestionBankMapper questionBankMapper;


    /**
     * 根据id查询题库
     * @param id 题库id
     * @return 题库信息
     */
    public QuestionBank getQuestionBankById(Long id) {
        return getById(id);
    }

    /**
     * 根据标题查询题库
     * @param title 题库标题
     * @return 题库信息
     */
    public QuestionBank getBankByTitle(String title) {
        QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        return getOne(queryWrapper);
    }

    /**
     * 删除题库
     * @param id 题库id
     */
    public void deleteBank(Long id) {
        boolean res =  removeById(id);
        if (!res) {
            throw new RuntimeException("删除失败");
        }
    }



    /**
     * 批量删除题库
     * @param ids 题库id数组
     */
    public void deleteBankBatch(Long[] ids) {
        removeByIds(Arrays.asList(ids));
    }

    /**
     * 更新题库
     * @param questionBank 题库信息
     */
    public void updateBank(QuestionBank questionBank) {
        boolean res = updateById(questionBank);
        if (!res) {
            throw new RuntimeException("更新失败");
        }
    }

    /**
     * 新增题库
     * @param questionBank 题库信息
     */
    public void addBank(QuestionBank questionBank) {
        boolean res = save(questionBank);
        if (!res) {
            throw new RuntimeException("添加失败");
        }
    }

}
