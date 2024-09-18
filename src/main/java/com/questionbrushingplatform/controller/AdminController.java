package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.dto.request.ReviewRequestDTO;
import com.questionbrushingplatform.dto.response.QuestionResponseDTO;
import com.questionbrushingplatform.dto.response.ReviewResponseDTO;
import com.questionbrushingplatform.dto.response.TagResponseDTO;
import com.questionbrushingplatform.dto.response.UserInfoResponseDTO;
import com.questionbrushingplatform.entity.*;
import com.questionbrushingplatform.service.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理模块
 * @author wenruohan
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Api(tags = "管理模块")
public class AdminController {

    private static final Integer PASS = 1;

    private static final Integer FAIL = 2;

    private final UserService userService;

    private final ReviewService reviewService;

    private final QuestionService questionService;

    private final TagService tagService;

    private final QuestionTagsMappingService questionTagsMappingService;

    @GetMapping("/review/question/list")
    public BaseResponse<List<Question>> getReviewQuestionList() {
        try {
            List<Question> questions = reviewService.getReviewQuestionList().stream()
                    .map(Review::getQuestionId)
                    .map(questionService::getQuestionById)
                    .toList();
            return new BaseResponse<>(ResponseCode.SUCCESS, questions);
        } catch (Exception e) {
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/review/question/{id}")
    public BaseResponse<Question> getReviewQuestion(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return new BaseResponse<>(ResponseCode.SUCCESS, question);
        } catch (Exception e) {
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/review/question/{id}/pass")
    public BaseResponse<Void> passReviewQuestion(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        try {
            Question question = new Question();
            question.setId(id);
            question.setReviewerId(Long.valueOf(reviewRequestDTO.getReviewerId()));
            question.setTitle(reviewRequestDTO.getQuestionInfo().getTitle());
            question.setAnswer(reviewRequestDTO.getQuestionInfo().getAnswer());
            question.setPriority(reviewRequestDTO.getPriority());
            question.setStatus(PASS);
            questionService.updateQuestion(question);
            List<QuestionTagsMapping> tags = reviewRequestDTO.getQuestionInfo().getTags().stream()
                    .map(tagService::getTagById)
                    .map(tag -> new QuestionTagsMapping() {{
                        setQuestionId(id);
                        setTagId(tag.getId());
                    }})
                    .collect(Collectors.toList());
            questionTagsMappingService.updateQuestionTagsMapping(tags);

//            QuestionResponseDTO questionInfo = new QuestionResponseDTO();
//            questionInfo.setId(String.valueOf(id));
//            questionInfo.setTitle(reviewRequestDTO.getQuestionInfo().getTitle());
//            questionInfo.setAnswer(reviewRequestDTO.getQuestionInfo().getAnswer());
//            questionInfo.setTags(tags.stream()
//                    .map(mapping -> {
//                        Tag tag = tagService.getTagById(mapping.getTagId());
//                        return new TagResponseDTO(tag.getId(), tag.getName());
//                    })
//                    .collect(Collectors.toList()));
//
//            User reviewer = userService.getUserById(Long.valueOf(reviewRequestDTO.getReviewerId()));
//            UserInfoResponseDTO reviewerInfo = new UserInfoResponseDTO();
//            reviewerInfo.setUserId(String.valueOf(reviewer.getId()));
//            // FIXME: getUserAccount() method to getUserName()
//            reviewerInfo.setUsername(reviewer.getUserAccount());
//            reviewerInfo.setNickname(reviewer.getUserName());
//            reviewerInfo.setUserAvatar(reviewer.getUserAvatar());
//
//            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO(reviewerInfo, reviewRequestDTO.getReviewMsg(), questionInfo, reviewRequestDTO.getPriority());
            return new BaseResponse<>(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/review/question/{id}/fail")
    public BaseResponse<Void> failReviewQuestion(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        try {
            Question question = new Question();
            question.setId(id);
            question.setReviewerId(Long.valueOf(reviewRequestDTO.getReviewerId()));
            question.setTitle(reviewRequestDTO.getQuestionInfo().getTitle());
            question.setAnswer(reviewRequestDTO.getQuestionInfo().getAnswer());
            question.setPriority(reviewRequestDTO.getPriority());
            question.setStatus(FAIL);
            questionService.updateQuestion(question);
            List<QuestionTagsMapping> tags = reviewRequestDTO.getQuestionInfo().getTags().stream()
                    .map(tagService::getTagById)
                    .map(tag -> new QuestionTagsMapping() {{
                        setQuestionId(id);
                        setTagId(tag.getId());
                    }})
                    .collect(Collectors.toList());
            questionTagsMappingService.updateQuestionTagsMapping(tags);

//            QuestionResponseDTO questionInfo = new QuestionResponseDTO();
//            questionInfo.setId(String.valueOf(id));
//            questionInfo.setTitle(reviewRequestDTO.getQuestionInfo().getTitle());
//            questionInfo.setAnswer(reviewRequestDTO.getQuestionInfo().getAnswer());
//            questionInfo.setTags(tags.stream()
//                    .map(mapping -> {
//                        Tag tag = tagService.getTagById(mapping.getTagId());
//                        return new TagResponseDTO(tag.getId(), tag.getName());
//                    })
//                    .collect(Collectors.toList()));
//
//            User reviewer = userService.getUserById(Long.valueOf(reviewRequestDTO.getReviewerId()));
//            UserInfoResponseDTO reviewerInfo = new UserInfoResponseDTO();
//            reviewerInfo.setUserId(String.valueOf(reviewer.getId()));
//            // FIXME: getUserAccount() method to getUserName()
//            reviewerInfo.setUsername(reviewer.getUserAccount());
//            reviewerInfo.setNickname(reviewer.getUserName());
//            reviewerInfo.setUserAvatar(reviewer.getUserAvatar());
//
//            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO(reviewerInfo, reviewRequestDTO.getReviewMsg(), questionInfo, reviewRequestDTO.getPriority());
            return new BaseResponse<>(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

}
