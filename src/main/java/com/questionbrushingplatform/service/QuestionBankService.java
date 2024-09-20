package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.questionbrushingplatform.entity.QuestionBank;



/**
 * @author 永
 */
public interface QuestionBankService extends IService<QuestionBank> {

    /**
     * 新增题库
     * @param questionBank 题库信息
     */
    void addBank(QuestionBank questionBank);

    /**
     * 根据id查询题库
     * @param id 题库id
     * @return 题库信息
     */
    QuestionBank getQuestionBankById(Long id);

    /**
     * 根据标题查询题库
     * @param title 题库标题
     * @return 题库信息
     */
    QuestionBank getBankByTitle(String title);

    /**
     * 删除题库
     * @param id 题库id
     */
    void deleteBank(Long id);

    /**
     * 批量删除题库
     * @param ids 题库id数组
     */
    void deleteBankBatch(Long[] ids);

    /**
     * 更新题库
     * @param questionBank 题库信息
     */
    void updateBank(QuestionBank questionBank);

}
