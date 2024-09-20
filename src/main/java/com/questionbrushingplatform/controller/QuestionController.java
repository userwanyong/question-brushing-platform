package com.questionbrushingplatform.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.dto.request.QuestionRequestDTO;
import com.questionbrushingplatform.dto.response.QuestionResponseDTO;
import com.questionbrushingplatform.dto.response.TagResponseDTO;
import com.questionbrushingplatform.entity.Question;
import com.questionbrushingplatform.entity.QuestionTagsMapping;
import com.questionbrushingplatform.entity.Tag;
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

    private static final Integer TOREVIEW = 0;

    private final QuestionService questionService;

    private final QuestionTagsMappingService questionTagsMappingService;

    private final TagService tagService;


    /**
     * 添加题目
     * @param questionDTO 所要添加的题目信息
     * @return 添加的题目信息
     */
    @PostMapping("/add")
    @ApiOperation("添加题目")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<QuestionResponseDTO> addQuestion(@RequestBody QuestionRequestDTO questionDTO) {
        // check if the user id is equal to the login id
        if (StpUtil.getLoginIdAsLong() != Long.parseLong(questionDTO.getUserId())) {
            log.error("QuestionController.addQuestion error: user id {} is not equal to login id {}", questionDTO.getUserId(), StpUtil.getLoginIdAsLong());
            return new BaseResponse<>(ResponseCode.PARAMETER_ERROR);
        }
        try {
            // convert QuestionRequestDTO to Question
            Question question = new Question();
            question.setTitle(questionDTO.getTitle());
            question.setContent(questionDTO.getContent());
            question.setAnswer(questionDTO.getAnswer());
            question.setUserId(Long.valueOf(questionDTO.getUserId()));
            questionService.addQuestion(question);
            Question res = questionService.getQuestionByTitle(question.getTitle());
            // Convert List<String> to List<QuestionTagsMapping>
            List<QuestionTagsMapping> questionTagsMappings = questionDTO.getTags().stream()
                    .map(tagService::getTagById)
                    .map(tag -> new QuestionTagsMapping() {{
                        setQuestionId(res.getId());
                        setTagId(tag.getId());
                    }})
                    .collect(Collectors.toList());
            questionTagsMappingService.updateQuestionTagsMapping(questionTagsMappings);
            // return the added question
            QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
            questionResponseDTO.setId(String.valueOf(res.getId()));
            questionResponseDTO.setTitle(res.getTitle());
            questionResponseDTO.setContent(res.getContent());
            questionResponseDTO.setAnswer(res.getAnswer());
            questionResponseDTO.setUserId(String.valueOf(res.getUserId()));
            questionResponseDTO.setTags(
                    questionTagsMappings.stream()
                            .map(mapping -> {
                                Tag tag = tagService.getTagById(mapping.getTagId());
                                return new TagResponseDTO(tag.getId(), tag.getName());
                            })
                            .collect(Collectors.toList()));
            return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTO);
        } catch (RuntimeException e) {
            log.error("QuestionController.addQuestion error: question dto is {}", questionDTO, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }


    /**
     * 删除题目
     * @param id 所要删除的id
     * @return 成功/失败
     */
    @PostMapping("/delete/{id}")
    @ApiOperation("删除题目")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> deleteQuestion(@PathVariable String id) {
        try {
            // delete the question and the mappings
            questionService.deleteQuestion(Long.valueOf(id));
            questionTagsMappingService.listQuestionTagsMappingsByQuestionId(Long.valueOf(id))
                    .forEach(mapping -> questionTagsMappingService.deleteQuestionTagsMapping(mapping.getId()));
            log.info("QuestionController.deleteQuestion success: which question id is {}", id);
            return new BaseResponse<>(ResponseCode.SUCCESS);
        } catch (RuntimeException e) {
            log.error("QuestionController.deleteQuestion error: which id is {}", id, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    /**
     * 更新题目
     * @param id 所要更新的题目id
     * @param questionDTO 所要更新的题目信息
     * @return 更新后的题目信息
     */
    @PostMapping("/update/{id}")
    @ApiOperation("更新题目")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<QuestionResponseDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequestDTO questionDTO) {
        try {
            // update the question
            Question question = new Question();
            question.setId(id);
            question.setTitle(questionDTO.getTitle());
            question.setContent(questionDTO.getContent());
            question.setAnswer(questionDTO.getAnswer());
            question.setUserId(Long.valueOf(questionDTO.getUserId()));
            question.setStatus(TOREVIEW);
            questionService.updateQuestion(question);

            // update the tags
            // get the existing tag ids and the new tag ids
            List<Integer> existingTagIds = questionTagsMappingService.listQuestionTagsMappingsByQuestionId(id).stream()
                    .map(QuestionTagsMapping::getTagId)
                    .toList();

            // get the new tag ids
            List<Integer> newTagIds = questionDTO.getTags().stream()
                    .distinct()
                    .map(tagService::getTagById)
                    .map(Tag::getId)
                    .toList();

            // Delete mappings that are not in the new tag list
            existingTagIds.stream()
                    .filter(tagId -> !newTagIds.contains(tagId))
                    .forEach(tagId -> questionTagsMappingService.deleteQuestionTagsMapping(
                            questionTagsMappingService.listQuestionTagsMappingsByTagId(tagId).stream()
                                    .filter(mapping -> mapping.getQuestionId().equals(id))
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Mapping not found"))
                                    .getId()));

            // Add new mappings that do not exist
            List<QuestionTagsMapping> questionTagsMappings = newTagIds.stream()
                    .filter(tagId -> !existingTagIds.contains(tagId))
                    .map(tagId -> new QuestionTagsMapping() {{
                        setQuestionId(id);
                        setTagId(tagId);
                    }})
                    .collect(Collectors.toList());

            // update the mappings
            if (!questionTagsMappings.isEmpty()) {
                questionTagsMappingService.updateQuestionTagsMapping(questionTagsMappings);
            }

            // return the updated question
            QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
            questionResponseDTO.setId(String.valueOf(id));
            questionResponseDTO.setTitle(questionDTO.getTitle());
            questionResponseDTO.setContent(questionDTO.getContent());
            questionResponseDTO.setAnswer(questionDTO.getAnswer());
            questionResponseDTO.setUserId(questionDTO.getUserId());
            questionResponseDTO.setTags(
                    questionTagsMappings.stream()
                            .map(mapping -> {
                                Tag tag = tagService.getTagById(mapping.getTagId());
                                return new TagResponseDTO(tag.getId(), tag.getName());
                            })
                            .collect(Collectors.toList()));
            return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTO);
        } catch (RuntimeException e) {
            log.error("QuestionController.updateQuestion error: which question id is {} and question is {} ", id, questionDTO, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取题目
     * @param id 所要获取的题目id
     * @return 题目信息
     */
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
                        .map(mapping -> {
                            Tag tag = tagService.getTagById(mapping.getTagId());
                            return new TagResponseDTO(tag.getId(), tag.getName());
                        })
                        .collect(Collectors.toList()));
        log.info("QuestionController.getQuestion success: {}", questionResponseDTO);
        return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTO);
    }

    /**
     * 获取题目列表
     * @param pageNum 当前页码
     * @param pageSize 每页数量
     * @return 题目列表
     */
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
                                    .map(mapping -> {
                                        Tag tag = tagService.getTagById(mapping.getTagId());
                                        return new TagResponseDTO(tag.getId(), tag.getName());
                                    })
                                    .collect(Collectors.toList()));
                    return questionResponseDTO;
                })
                .collect(Collectors.toList());
        log.info("QuestionController.listQuestions success: {}", questionResponseDTOList);
        return new BaseResponse<>(ResponseCode.SUCCESS, questionResponseDTOList);
    }
}