package com.questionbrushingplatform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.result.Result;
import com.questionbrushingplatform.pojo.dto.PageDTO;
import com.questionbrushingplatform.pojo.entity.Question;
import com.questionbrushingplatform.pojo.query.QuestionQuery;
import com.questionbrushingplatform.pojo.vo.QuestionVO;
import com.questionbrushingplatform.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/question")
@Slf4j
@Api(tags = "题目接口")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 根据id查询题目
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    @ApiOperation("根据id查询题目")
    public Result getById(@PathVariable Long id) {
        Question question = questionService.getById(id);
        if (question == null) {
            return Result.error(MessageConstant.QUESTION_NOT_FOUND);
        }
        return Result.success(question);
    }

    /**
     * es根据条件查询题目
     * @param questionQuery
     * @return
     */
    @PostMapping("/search/page/vo")
    @ApiOperation("根据条件查询题目")
    public PageDTO<QuestionVO> searchQuestionVOByPage(@RequestBody QuestionQuery questionQuery) {

        Page<Question> questionPage = questionService.searchFromEs(questionQuery);
        return PageDTO.of(questionPage, QuestionVO.class);
    }

}
