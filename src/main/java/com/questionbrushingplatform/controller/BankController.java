package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.dto.response.QuestionResponseDTO;
import com.questionbrushingplatform.dto.response.TagResponseDTO;
import com.questionbrushingplatform.entity.QuestionBank;
import com.questionbrushingplatform.entity.QuestionBankMapping;
import com.questionbrushingplatform.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 题库模块
 *
 * @author wenruohan
 */
@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
@Api(tags = "题库模块")
public class BankController {

    private final QuestionBankService bankService;

    private final QuestionBankMappingService questionBankMappingService;

    private final QuestionService questionService;

    private final TagService tagService;

    private final QuestionTagsMappingService questionTagsMappingService;

    /**
     * 获取题库
     *
     * @return BaseResponse
     */
    @GetMapping("/get")
    @ApiOperation("获取题库")
    public BaseResponse<List<QuestionBank>> getBank() {
        List<QuestionBank> banks = bankService.list();
        return new BaseResponse<>(ResponseCode.SUCCESS, banks);
    }

    /**
     * 获取题库题目列表
     *
     * @return BaseResponse
     */
    @GetMapping("/{id}/questions")
    @ApiOperation("获取题库题目列表")
    public BaseResponse<List<QuestionResponseDTO>> getBankQuestions(@PathVariable Long id) {
        List<Long> questionIds = questionBankMappingService.listByBankId(id).stream()
                .map(QuestionBankMapping::getQuestionId)
                .toList();
        List<QuestionResponseDTO> questions = questionService.listByIds(questionIds).stream()
                .map(question -> {
                    QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
                    questionResponseDTO.setId(String.valueOf(question.getId()));
                    questionResponseDTO.setTitle(question.getTitle());
                    questionResponseDTO.setContent(question.getContent());
                    questionResponseDTO.setAnswer(question.getAnswer());
                    questionResponseDTO.setUserId(String.valueOf(question.getUserId()));
                    questionResponseDTO.setTags(
                            questionTagsMappingService.listQuestionTagsMappingsByQuestionId(question.getId()).stream()
                                    .map(mapping -> new TagResponseDTO(mapping.getTagId(), tagService.getTagById(mapping.getTagId()).getName()))
                                    .collect(Collectors.toList()));
                    return questionResponseDTO;
                })
                .collect(Collectors.toList());
        return new BaseResponse<>(ResponseCode.SUCCESS, questions);
    }
}
