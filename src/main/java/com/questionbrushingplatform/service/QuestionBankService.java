package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.questionbrushingplatform.entity.QuestionBank;



/**
 * @author 永
 */
public interface QuestionBankService extends IService<QuestionBank> {

    /**
     * 新增题库
     * @param questionBank
     */
    boolean addBank(QuestionBank questionBank);

    /**
     * 根据id获取题库
     * @param id
     * @return
     */
    QuestionBank getQuestionBankById(Long id);

    /**
     * 根据title获取题库
     * @param title
     * @return
     */
    QuestionBank getBankByTitle(String title);

    /**
     * 删除题库
     * @param id
     * @return
     */
    boolean deleteBank(Long id);

    /**
     * 根据id批量删除题库
     * @param ids
     */
    void deleteBankBatch(Long[] ids);


//    /**
//     * 根据id删除题库
//     * @param id
//     */
//    void deleteById(Long id);

//    /**
//     * 根据id批量删除题库
//     * @param ids
//     */
//    void deleteByIds(Long[] ids);


//    /**
//     * 分页查询题库
//     * @param questionBankQuery
//     * @return
//     */
//    PageDTO<QuestionBankResponseDTO> selectByPage(QuestionBankQuery questionBankQuery);



}
