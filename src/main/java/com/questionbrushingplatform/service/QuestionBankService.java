package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.questionbrushingplatform.dto.request.PageDTO;
import com.questionbrushingplatform.dto.request.QuestionBankAddRequestDTO;
import com.questionbrushingplatform.dto.response.QuestionBankResponseDTO;
import com.questionbrushingplatform.entity.QuestionBank;
import com.questionbrushingplatform.pojo.query.QuestionBankQuery;



/**
 * @author 永
 */
public interface QuestionBankService extends IService<QuestionBank> {

    /**
     * 新增题库
     * @param questionBankAddDTO
     */
    void add(QuestionBankAddRequestDTO questionBankAddDTO);

    /**
     * 根据id删除题库
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据id批量删除题库
     * @param ids
     */
    void deleteByIds(Long[] ids);

    /**
     * 通用更新时间
     * @param id
     */
//    void updateTimeById(Long id);

    /**
     * 分页查询题库
     * @param questionBankQuery
     * @return
     */
    PageDTO<QuestionBankResponseDTO> selectByPage(QuestionBankQuery questionBankQuery);


    /**
     * 判断题库是否存在
     * @param title
     */
    void isExist(String title);

    /**
     * 根据id获取题库
     * @param id
     * @return
     */
    QuestionBank getQuestionBankById(Long id);
}
