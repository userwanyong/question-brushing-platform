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
     * @param id
     * @return
     */
    public QuestionBank getQuestionBankById(Long id) {
        return getById(id);
    }

    /**
     * 根据标题查询题库
     * @param title
     * @return
     */
    public QuestionBank getBankByTitle(String title) {
        QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        return getOne(queryWrapper);
    }

    /**
     * 删除题库
     * @param id
     * @return
     */
    public boolean deleteBank(Long id) {
        return removeById(id);
    }



    /**
     * 批量删除题库
     * @param ids
     */
    public void deleteBankBatch(Long[] ids) {
        removeBatchByIds(Arrays.asList(ids));
    }

    /**
     * 新增题库
     * @param questionBank
     */
    public boolean addBank(QuestionBank questionBank) {
        return save(questionBank);
    }

//    /**
//     * 根据id删除题库
//     * @param id
//     */
//    public void deleteById(Long id) {
//        //判断是否是正常的id并且不能重复删除
//        if (questionBankMapper.selectById(id) == null) {
//            throw new BaseException(MessageConstant.QUEST_BANK_NOT_FOUND);
//        }
//        //判断该题库下有没有题目，如果有的话，不能删除该题库，要先删除其中的题目
//        //查询该题库下是否有题目 select * from question where questionBankId = id
//        //TODO
////        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
////        queryWrapper.eq(Question::getQuestionBankId, id);
////        Long count = questionMapper.selectCount(queryWrapper);
////        if (count > 0){
////            throw new BaseException(MessageConstant.QUESTION_BANK_NOT_EMPTY);
////        }
//        //更新时间
////        updateTimeById(id);
//        questionBankMapper.deleteById(id);
//        log.info("QuestionBankServiceImpl.deleteById success: delete questionBank success which is {}", id);
//
//
//    }

//    /**
//     * 根据id批量删除题库
//     * @param ids
//     */
//    public void deleteByIds(Long[] ids) {
//        LambdaQueryWrapper<QuestionBank> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.in(QuestionBank::getId, ids);
//        //判断是否是正常的id并且不能重复删除
//        Long count = questionBankMapper.selectCount(queryWrapper);
//        if (count != ids.length){
//            throw new BaseException(MessageConstant.QUEST_BANK_NOT_FOUND);
//        }
//        //判断该题库下有没有题目，如果有的话，不能删除该题库，要先删除其中的题目
//        //TODO
////        LambdaQueryWrapper<Question> queryWrapperQuestion = new LambdaQueryWrapper<>();
////        queryWrapperQuestion.in(Question::getQuestionBankId, ids);
////        Long countQuestion = questionMapper.selectCount(queryWrapperQuestion);
////        if (countQuestion > 0){
////            throw new BaseException(MessageConstant.QUESTION_BANK_NOT_EMPTY);
////        }
//        //更新时间
////        LambdaUpdateWrapper<QuestionBank> updateWrapper = new LambdaUpdateWrapper<>();
////        updateWrapper.set(QuestionBank::getUpdateTime, LocalDateTime.now())
////                .in(QuestionBank::getId, ids);
////        questionBankMapper.update(null, updateWrapper);
//        //删除所选题库
//        questionBankMapper.delete(queryWrapper);
//        log.info("QuestionBankServiceImpl.deleteByIds success: delete questionBank success which is {}", ids);
//
//    }

//    /**
//     * 分页查询题库
//     * @param questionBankQuery
//     * @return
//     */
//    public PageDTO<QuestionBankResponseDTO> selectByPage(QuestionBankQuery questionBankQuery) {
//        // 1.构建基础查询条件
//        Page<QuestionBank> page = questionBankQuery.toMpPage("created_time", false);
//        // 2.分页查询
//        Page<QuestionBank> p = lambdaQuery()
//                .like(questionBankQuery.getTitle()!=null,QuestionBank::getTitle,questionBankQuery.getTitle())
//                .eq(questionBankQuery.getUserId()!=null,QuestionBank::getUserId,questionBankQuery.getUserId())
//                .between(questionBankQuery.getStartTime()!=null&&questionBankQuery.getEndTime()!=null,QuestionBank::getCreatedTime,questionBankQuery.getStartTime(),questionBankQuery.getEndTime())
//                .between(questionBankQuery.getStartTime()!=null&&questionBankQuery.getEndTime()!=null,QuestionBank::getUpdatedTime,questionBankQuery.getStartTime(),questionBankQuery.getEndTime())
//                .between(questionBankQuery.getStartTime()!=null&&questionBankQuery.getEndTime()!=null,QuestionBank::getEditTime,questionBankQuery.getStartTime(),questionBankQuery.getEndTime())
//                .page(page);
//        log.info("QuestionBankServiceImpl.selectByPage success: selectByPage success which is {}", p);
//        // 3.封装VO结果
//        return PageDTO.of(p, QuestionBankResponseDTO.class);
//    }

}
