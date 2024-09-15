package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.questionbrushingplatform.pojo.entity.Question;
import com.questionbrushingplatform.pojo.query.QuestionQuery;


/**
 * @author 永
 */
public interface QuestionService extends IService<Question> {

    /**
     * 从ES中查询题目
     * @param questionQuery
     * @return
     */
    Page<Question> searchFromEs(QuestionQuery questionQuery);

}
