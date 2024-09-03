package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.result.Result;
import com.questionbrushingplatform.pojo.entity.Question;
import com.questionbrushingplatform.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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


}
