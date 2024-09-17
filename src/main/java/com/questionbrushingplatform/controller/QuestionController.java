package com.questionbrushingplatform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.dto.request.QuestionAddRequestDTO;
import com.questionbrushingplatform.dto.request.QuestionRequestDTO;
import com.questionbrushingplatform.dto.response.QuestionResponseDTO;
import com.questionbrushingplatform.pojo.entity.Question;
import com.questionbrushingplatform.pojo.entity.QuestionTagsMapping;
import com.questionbrushingplatform.service.QuestionService;
import com.questionbrushingplatform.service.QuestionTagsMappingService;
import com.questionbrushingplatform.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 永
 */
@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "题目接口")
public class QuestionController {

    private final QuestionService questionService;

    private final QuestionTagsMappingService questionTagsMappingService;

    private final TagService tagService;

    @PostMapping("/add")
    @ApiOperation("添加题目")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<QuestionResponseDTO> addQuestion(@RequestBody QuestionAddRequestDTO questionDTO) {
        Long questionId;
        try {
            Question question = new Question();
            question.setTitle(questionDTO.getTitle());
            question.setContent(questionDTO.getContent());
            question.setAnswer(questionDTO.getAnswer());
            question.setUserId(Long.valueOf(questionDTO.getUserId()));
            questionService.addQuestion(question);
            Question res = questionService.getQuestionByTitle(question.getTitle());
            questionId = res.getId();
            // Convert List<String> to List<QuestionTagsMapping>
            List<QuestionTagsMapping> questionTagsMappings = questionDTO.getTags().stream()
                    .map(tagService::getTagById)
                    .map(tag -> new QuestionTagsMapping() {{
                        setQuestionId(questionId);
                        setTagId(tag.getId());
                    }})
                    .collect(Collectors.toList());
            questionTagsMappingService.addQuestionTagsMappings(questionTagsMappings);
            QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
            questionResponseDTO.setId(String.valueOf(res.getId()));
            questionResponseDTO.setTitle(res.getTitle());
            questionResponseDTO.setContent(res.getContent());
            questionResponseDTO.setAnswer(res.getAnswer());
            questionResponseDTO.setUserId(String.valueOf(res.getUserId()));
            questionResponseDTO.setTags(
                    questionTagsMappings.stream()
                            .map(mapping -> tagService.getTagById(mapping.getTagId()))
                            .collect(Collectors.toList()));
        } catch (RuntimeException e) {
            log.error("QuestionController.addQuestion error: question dto is {}", questionDTO, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId(String.valueOf(questionId));
        questionResponseDTO.setTitle(questionDTO.getTitle());
        questionResponseDTO.setContent(questionDTO.getContent());
        questionResponseDTO.setAnswer(questionDTO.getAnswer());
        questionResponseDTO.setUserId(questionDTO.getUserId());
        questionResponseDTO.setTags(
                questionTagsMappingService.listQuestionTagsMappingsByQuestionId(questionId).stream()
                        .map(mapping -> tagService.getTagById(mapping.getTagId()))
                        .collect(Collectors.toList()));
        return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTO);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除题目")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> deleteQuestion(@PathVariable String id) {
        try {
            questionService.deleteQuestion(Long.valueOf(id));
            questionTagsMappingService.listQuestionTagsMappingsByQuestionId(Long.valueOf(id))
                    .forEach(mapping -> questionTagsMappingService.deleteQuestionTagsMapping(mapping.getId()));
        } catch (RuntimeException e) {
            log.error("QuestionController.deleteQuestion error: which id is {}", id, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        log.info("QuestionController.deleteQuestion success: which question id is {}", id);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    @PostMapping("/update/{id}")
    @ApiOperation("更新题目")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<QuestionResponseDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequestDTO questionDTO) {
        try {
            Question question = new Question();
            question.setId(id);
            question.setTitle(questionDTO.getTitle());
            question.setContent(questionDTO.getContent());
            question.setAnswer(questionDTO.getAnswer());
            question.setUserId(Long.valueOf(questionDTO.getUserId()));
            questionService.updateQuestion(question);
            questionTagsMappingService.listQuestionTagsMappingsByQuestionId(id)
                    .forEach(mapping -> questionTagsMappingService.deleteQuestionTagsMapping(mapping.getId()));
            List<QuestionTagsMapping> questionTagsMappings = questionDTO.getTags().stream()
                    .map(tagService::getTagById)
                    .map(tag -> new QuestionTagsMapping() {{
                        setQuestionId(question.getId());
                        setTagId(tag.getId());
                    }})
                    .collect(Collectors.toList());
            questionTagsMappingService.addQuestionTagsMappings(questionTagsMappings);
        } catch (RuntimeException e) {
            log.error("QuestionController.updateQuestion error: which question id is {} and question is {} ", id, questionDTO, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId(String.valueOf(id));
        questionResponseDTO.setTitle(questionDTO.getTitle());
        questionResponseDTO.setContent(questionDTO.getContent());
        questionResponseDTO.setAnswer(questionDTO.getAnswer());
        questionResponseDTO.setUserId(questionDTO.getUserId());
        questionResponseDTO.setTags(
                questionTagsMappingService.listQuestionTagsMappingsByQuestionId(id).stream()
                        .map(mapping -> tagService.getTagById(mapping.getTagId()))
                        .collect(Collectors.toList()));
        return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTO);
    }

    @GetMapping("/get/{id}")
    @ApiOperation("获取题目")
    public BaseResponse<QuestionResponseDTO> getQuestion(@PathVariable String id) {
        Question question = questionService.getQuestionById(Long.valueOf(id));
        if (question == null) {
            log.error("QuestionController.getQuestion error: {}", ResponseCode.NO_DATA);
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId(String.valueOf(question.getId()));
        questionResponseDTO.setTitle(question.getTitle());
        questionResponseDTO.setContent(question.getContent());
        questionResponseDTO.setAnswer(question.getAnswer());
        questionResponseDTO.setUserId(String.valueOf(question.getUserId()));
        questionResponseDTO.setTags(
                questionTagsMappingService.listQuestionTagsMappingsByQuestionId(Long.valueOf(id)).stream()
                        .map(mapping -> tagService.getTagById(mapping.getTagId()))
                        .collect(Collectors.toList()));
        log.info("QuestionController.getQuestion success: {}", questionResponseDTO);
        return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTO);
    }

//    @GetMapping("/list")
//    @ApiOperation("获取题目列表")
//    public BaseResponse<List<QuestionResponseDTO>> listQuestions() {
//        List<Question> questions = questionService.listQuestions();
//        List<QuestionResponseDTO> questionResponseDTOList = questions.stream()
//                .map(question -> {
//                    QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
//                    questionResponseDTO.setId(String.valueOf(question.getId()));
//                    questionResponseDTO.setTitle(question.getTitle());
//                    questionResponseDTO.setContent(question.getContent());
//                    questionResponseDTO.setAnswer(question.getAnswer());
//                    questionResponseDTO.setUserId(String.valueOf(question.getUserId()));
//                    questionResponseDTO.setTags(
//                            questionTagsMappingService.listQuestionTagsMappingsByQuestionId(question.getId()).stream()
//                                    .map(mapping -> tagService.getTagById(mapping.getTagId()))
//                                    .collect(Collectors.toList()));
//                    return questionResponseDTO;
//                })
//                .collect(Collectors.toList());
//        log.info("QuestionController.listQuestions success: {}", questionResponseDTOList);
//        return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTOList);
//    }

    @GetMapping("/list")
    @ApiOperation("获取题目列表")
    public BaseResponse<List<QuestionResponseDTO>> listQuestions(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<Question> questionPage = questionService.listQuestions(pageNum, pageSize);
        List<QuestionResponseDTO> questionResponseDTOList = questionPage.getRecords().stream()
                .map(question -> {
                    QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
                    questionResponseDTO.setId(String.valueOf(question.getId()));
                    questionResponseDTO.setTitle(question.getTitle());
                    questionResponseDTO.setContent(question.getContent());
                    questionResponseDTO.setAnswer(question.getAnswer());
                    questionResponseDTO.setUserId(String.valueOf(question.getUserId()));
                    questionResponseDTO.setTags(
                            questionTagsMappingService.listQuestionTagsMappingsByQuestionId(question.getId()).stream()
                                    .map(mapping -> tagService.getTagById(mapping.getTagId()))
                                    .collect(Collectors.toList()));
                    return questionResponseDTO;
                })
                .collect(Collectors.toList());
        log.info("QuestionController.listQuestions success: {}", questionResponseDTOList);
        return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTOList);
    }
}