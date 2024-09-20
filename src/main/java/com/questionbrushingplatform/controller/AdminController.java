package com.questionbrushingplatform.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.questionbrushingplatform.common.constant.NumberConstant;
import com.questionbrushingplatform.common.constant.PasswordConstant;
import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.dto.request.*;
import com.questionbrushingplatform.dto.response.QuestionBankResponseDTO;
import com.questionbrushingplatform.dto.response.UserResponseDTO;
import com.questionbrushingplatform.entity.*;
import com.questionbrushingplatform.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后花园
 * @author wenruohan
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Api(tags = "管理模块")
@Slf4j
public class AdminController {

    private final UserService userService;

    private final ReviewService reviewService;

    private final QuestionService questionService;

    private final TagService tagService;

    private final QuestionTagsMappingService questionTagsMappingService;

    private final QuestionBankMappingService questionBankMappingService;

    private final QuestionBankService questionBankService;


    /**
     * 获取待审核问题列表
     * @return BaseResponse<List<Question>>
     */
    @GetMapping("/review/question/list")
    @ApiOperation("获取待审核问题列表")
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

    /**
     * 根据id获取待审核问题
     * @param id 待审核问题id
     * @return BaseResponse<Question>
     */
    @GetMapping("/review/question/{id}")
    @ApiOperation("根据id获取待审核问题")
    public BaseResponse<Question> getReviewQuestion(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return new BaseResponse<>(ResponseCode.SUCCESS, question);
        } catch (Exception e) {
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    /**
     * 通过审核问题
     * @param id 待审核问题id
     * @param reviewRequestDTO 审核请求
     * @return 成功
     */
    @PostMapping("/review/question/{id}/pass")
    @ApiOperation("通过审核问题")
    public BaseResponse<Void> passReviewQuestion(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        try {
            Question question = new Question();
            question.setId(id);
            question.setTitle(reviewRequestDTO.getQuestionInfo().getTitle());
            question.setAnswer(reviewRequestDTO.getQuestionInfo().getAnswer());
            question.setPriority(reviewRequestDTO.getPriority());
            question.setStatus(NumberConstant.PASS);
            questionService.updateQuestion(question);
            List<QuestionTagsMapping> tags = reviewRequestDTO.getQuestionInfo().getTags().stream()
                    .map(tagService::getTagById)
                    .map(tag -> new QuestionTagsMapping() {{
                        setQuestionId(id);
                        setTagId(tag.getId());
                    }})
                    .collect(Collectors.toList());
            questionTagsMappingService.updateQuestionTagsMapping(tags);

            Review review = new Review();
            review.setReviewerId(Long.valueOf(reviewRequestDTO.getReviewerId()));
            review.setReviewMsg(reviewRequestDTO.getReviewMsg());
            review.setStatus(NumberConstant.PASS);
            review.setQuestionId(id);
            reviewService.addReview(review);

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

    /**
     * 驳回审核问题
     * @param id 待审核问题id
     * @param reviewRequestDTO 审核请求
     * @return 失败
     */
    @PostMapping("/review/question/{id}/fail")
    @ApiOperation("驳回审核问题")
    public BaseResponse<Void> failReviewQuestion(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        try {
            Review review = new Review();
            review.setReviewerId(Long.valueOf(reviewRequestDTO.getReviewerId()));
            review.setReviewMsg(reviewRequestDTO.getReviewMsg());
            review.setStatus(NumberConstant.FAIL);
            review.setQuestionId(id);
            reviewService.addReview(review);

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


    /**
     * 添加题目到题库
     * @param questionId 题目id
     * @param bankId 题库id
     * @return 成功/失败
     */
    @PostMapping("/question/add/{questionId}/{bankId}")
    @ApiOperation("新增题目到题库")
    public BaseResponse<Void> addQuestionBankMapping(@PathVariable Long questionId,@PathVariable Long bankId) {
        //判断该题库id与题目id是否存在
        if (questionService.getQuestionById(questionId)==null || questionBankService.getQuestionBankById(bankId)==null) {
            return new BaseResponse<>(ResponseCode.PARAM_NOT_EXIST);
        }
        //判断该关系是否存在
        if (questionBankMappingService.getQuestionBankMappingById(questionId,bankId)!=null) {
            return new BaseResponse<>(ResponseCode.ALREADY_EXIST);
        }
        try {
            //添加题目到题库
            QuestionBankMapping build = QuestionBankMapping.builder()
                    .questionId(questionId)
                    .bankId(bankId)
                    .build();
            questionBankMappingService.save(build);
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }
        catch (Exception e){
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    /**
     * 新增用户
     * @param userAddDTO 新增用户信息
     * @return 用户信息
     */
    @PostMapping("/user/add")
    @ApiOperation("新增用户")
    public BaseResponse<UserResponseDTO> addUser(@RequestBody UserAddRequestDTO userAddDTO) {
        try {//限制账号长度为11位
            if (userAddDTO.getUsername().length() != 11){
                log.error("AdminController.add error: the userAccount length not allowed which is {}", userAddDTO.getUsername());
                return new BaseResponse<>(ResponseCode.PARAMETER_ERROR);
            }
            //先判断数据库是否有该账号，如果有，则不新增
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername, userAddDTO.getUsername());
            User dbUser = userService.getByUsername(queryWrapper);
            if (dbUser != null){
                log.error("AdminController.add error: the userAccount already exists which is {}", userAddDTO.getUsername());
                return new BaseResponse<>(ResponseCode.ALREADY_EXIST);
            }
            //如果不存在，则新增，默认角色为用户
            if (userAddDTO.getUserRole()==null){
                userAddDTO.setUserRole(NumberConstant.USER);
            }
            //如果没有输入密码，则默认密码为123456
            if (userAddDTO.getPassword()==null||userAddDTO.getPassword().isEmpty()) {
                userAddDTO.setPassword(PasswordConstant.DEFAULT_PASSWORD);
            }
            //限制密码长度在6-16位
            else if (userAddDTO.getPassword().length() < 6 || userAddDTO.getPassword().length() > 16){
                log.error("AdminController.add error: the userPassword length not allowed which is {}", userAddDTO.getPassword());
                return new BaseResponse<>(ResponseCode.PARAMETER_ERROR);
            }
            //如果没有输入用户名，则默认用户名为账号
            if (userAddDTO.getUsername()==null||userAddDTO.getUsername().isEmpty()){
                userAddDTO.setUsername(userAddDTO.getUsername());
            }
            User user = new User();
            user.setUsername(userAddDTO.getUsername());
            BeanUtils.copyProperties(userAddDTO,user);
            userService.addUser(user);
            log.info("AdminController.add success: add userAddDTO success which is {}", userAddDTO);
            //获取用户信息
            User getUser = userService.getUserById(user.getId());
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            BeanUtils.copyProperties(getUser,userResponseDTO);
            return new BaseResponse<>(ResponseCode.SUCCESS,userResponseDTO);
        }catch (Exception e){
            log.error("AdminController.addUser error: question dto is {}", userAddDTO, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    /**
     * 单个删除用户
     * @param id 用户id
     * @return 成功/失败
     */
    @DeleteMapping("/user/deleteById/{id}")
    @ApiOperation("单个删除用户")
    public BaseResponse<Boolean> deleteUser(@PathVariable Long id) {
        //判断该id是否存在
        if (userService.getUserById(id) == null) {
            log.error("AdminController.deleteById error: the user not exist which is {}", id);
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        try {
            userService.deleteUser(id);
            log.info("AdminController.deleteById success: delete user success which is {}", id);
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("AdminController.deleteById error: the user id is {}", id, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }


    /**
     * 批量删除用户
     * @param ids id列表
     * @return 成功/失败
     */
    @DeleteMapping("/user/deleteByIds")
    @ApiOperation("批量删除用户")
    public BaseResponse<Boolean> deleteUsers(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            //判断id是否存在
            if (userService.getUserById(id) == null) {
                log.error("AdminController.deleteById error: the user not exist which is {}", id);
                return new BaseResponse<>(ResponseCode.NO_DATA);
            }
        }
        try {
            userService.deleteUserByIds(ids);
            log.info("AdminController.deleteById success: delete user success which is {}", ids);
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("AdminController.deleteById error: the user id is {}", ids, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }


    /**
     * 修改用户信息
     * @param userDTO 用户信息
     * @return 修改后的用户信息
     */
    @PutMapping("/user/update")
    @ApiOperation("修改用户信息")
    public BaseResponse<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userDTO) {
        //判断用户id是否存在
        if (userService.getUserById(userDTO.getId()) == null) {
            log.error("AdminController.update error: the user not exist which is {}", userDTO);
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        try{
            User user = new User();
            BeanUtils.copyProperties(userDTO,user);
            //前端通过查询原有数据，在原有数据的基础上修改，所以不需要判断数据是否为空
            userService.updateUser(user);
            log.info("AdminController.update success: update user success which is {}", userDTO);
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("AdminController.update error: user dto is {}", userDTO, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }


    /**
     * 根据id查询用户
     * @param id id
     * @return 用户信息
     */
    @GetMapping("/user/get/{id}")
    @ApiOperation("根据id查询用户")
    public BaseResponse<UserResponseDTO> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            log.error("AdminController.getById error: the user not exist which is {}", id);
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user,userResponseDTO);
        log.info("AdminController.getById success: get user success which is {}", id);
        return new BaseResponse<>(ResponseCode.SUCCESS,userResponseDTO);
    }

    /**
     * 分页查询用户
     * @param pageNum 当前页码
     * @param pageSize 每页的数量
     * @return 用户列表
     */
    @GetMapping("/user/list")
    @ApiOperation("分页查询用户")
    public BaseResponse<List<UserResponseDTO>> listUser(@RequestParam(defaultValue = "1") Integer pageNum,
                                                        @RequestParam(defaultValue = "20") Integer pageSize) {

        Page<User> userPage = userService.listUser(pageNum, pageSize);
        List<UserResponseDTO> userResponseDTOList = userPage.getRecords().stream()
                .map(user -> {
                    UserResponseDTO userResponseDTO = new UserResponseDTO();
                    userResponseDTO.setId(Long.valueOf(String.valueOf(user.getId())));
                    userResponseDTO.setUsername(user.getUsername());
                    userResponseDTO.setPassword(user.getPassword());
                    userResponseDTO.setNickname(user.getNickname());
                    userResponseDTO.setUserAvatar(user.getUserAvatar());
                    userResponseDTO.setUserProfile(user.getUserProfile());
                    userResponseDTO.setUserRole(user.getUserRole());
                    userResponseDTO.setCreatedTime(user.getCreatedTime());
                    userResponseDTO.setUpdatedTime(user.getUpdatedTime());
                    return userResponseDTO;
                })
                .collect(Collectors.toList());
        log.info("AdminController.listUser success: {}", userResponseDTOList);
        return new BaseResponse<>(ResponseCode.SUCCESS, userResponseDTOList);
    }

    /**
     * 新增题库
     * @param questionBankAddDTO 题库信息
     * @return 所添加的题库信息
     */
    @PostMapping("/bank/add")
    @ApiOperation("新增题库")
    public BaseResponse<QuestionBankResponseDTO> addBank(@RequestBody QuestionBankAddRequestDTO questionBankAddDTO) {
        //必须要有题库名
        if (questionBankAddDTO.getTitle() == null|| questionBankAddDTO.getTitle().isEmpty()) {
            log.error("AdminController.add error: the title is bank witch is {}", questionBankAddDTO.getTitle());
            return new BaseResponse<>(ResponseCode.PARAMETER_ERROR);
        }
        //判断该题库是否存在
        QuestionBank dbQuestionBank = questionBankService.getBankByTitle(questionBankAddDTO.getTitle());
        if (dbQuestionBank != null) {
            log.error("AdminController.isExist error: the title is exists which is {}", questionBankAddDTO.getTitle());
            return new BaseResponse<>(ResponseCode.ALREADY_EXIST_MESSAGE);
        }
        try {
            QuestionBank questionBank = new QuestionBank();
            BeanUtils.copyProperties(questionBankAddDTO, questionBank);
            questionBank.setUserId(StpUtil.getLoginIdAsLong());
            questionBankService.addBank(questionBank);
            log.info("AdminController.add success: add questionBankAddDTO success which is {}", questionBankAddDTO);
            QuestionBank bankByTitle = questionBankService.getBankByTitle(questionBankAddDTO.getTitle());
            QuestionBankResponseDTO questionBankResponseDTO = new QuestionBankResponseDTO();
            BeanUtils.copyProperties(bankByTitle, questionBankResponseDTO);
            log.info("AdminController.add success: add questionBankAddDTO success which is {}", questionBankResponseDTO);
            return new BaseResponse<>(ResponseCode.SUCCESS,questionBankResponseDTO);
        }catch (Exception e){
            log.error("AdminController.add error: questionBankAddDTO is {}", questionBankAddDTO, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }


    /**
     * 根据id删除题库
     * @param id 题库id
     * @return 成功/失败
     */
    @PostMapping("/bank/delete/{id}")
    @ApiOperation("根据id删除题库")
    public BaseResponse<Boolean> deleteBankById(@PathVariable Long id) {
        try {
            //判断是否是正常的id并且不能重复删除
            if (questionBankService.getQuestionBankById(id) == null) {
                log.error("AdminController.deleteById error: the id is not exists which is {}", id);
                return new BaseResponse<>(ResponseCode.NO_DATA);
            }
            //判断该题库下是否关联了题目，如果关联了，不能删除该题库，要先删除其中的题目
            List<QuestionBankMapping> questionBankMappings = questionBankMappingService.listByBankId(id);
            if (!questionBankMappings.isEmpty()) {
                log.error("AdminController.deleteById error: the questionBank is not empty which is {}", id);
                return new BaseResponse<>(ResponseCode.QUESTION_BANK_NOT_EMPTY);
            }
            questionBankService.deleteBank(id);
            log.info("AdminController.deleteById success: delete questionBank success which is {}", id);
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("AdminController.deleteById error: id is {}", id, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    /**
     * 批量删除
     * @param ids id数组
     * @return 成功/失败
     */
    @PostMapping("/bank/deleteBatch")
    @ApiOperation("批量删除题库")
    public BaseResponse<Boolean> deleteBatch(@RequestBody Long[] ids) {
        try {
            //判断这些id在数据库是否存在
            for (Long id : ids) {
                if (questionBankService.getQuestionBankById(id) == null) {
                    log.error("AdminController.deleteBatch error: the id is not exists which is {}", id);
                    return new BaseResponse<>(ResponseCode.NO_DATA);
                }
            }
            //判断该题库下有没有题目，如果有的话，不能删除该题库，要先删除其中的题目
            Long countQuestion = questionBankMappingService.listByBankIds(ids);
            if (countQuestion > 0){
                return new BaseResponse<>(ResponseCode.QUESTION_BANK_NOT_EMPTY);
            }
            //删除所选题库
            questionBankService.deleteBankBatch(ids);
            log.info("AdminController.deleteByIds success: delete questionBank success which is {}", (Object) ids);
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("AdminController.deleteBatch error: ids is {}", ids, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }


    /**
     * 修改题库
     * @param questionBankUpdateDTO 所要修改的题库信息
     * @return 修改后的题库信息
     */
    @PostMapping("/bank/update")
    @ApiOperation("修改题库")
    public BaseResponse<QuestionBankResponseDTO> updateBank(@RequestBody QuestionBankUpdateRequestDTO questionBankUpdateDTO) {
        try {
            //判断该题库是否存在
            if (questionBankService.getQuestionBankById(questionBankUpdateDTO.getId())==null) {
                log.error("AdminController.update error: the question bank does not exist which is {}", questionBankUpdateDTO.getId());
                return new BaseResponse<>(ResponseCode.NO_DATA);
            }
            //判断是否传入了题库名
            if (StringUtils.isEmpty(questionBankUpdateDTO.getTitle())) {
                log.error("AdminController.update error: the title is empty which is {}", questionBankUpdateDTO.getTitle());
                return new BaseResponse<>(ResponseCode.PARAMETER_ERROR);
            }
            //判断修改后的题库名是否已存在
            QuestionBank dbQuestionBank = questionBankService.getBankByTitle(questionBankUpdateDTO.getTitle());
            if (dbQuestionBank != null) {
                log.error("AdminController.update error: the title is exists which is {}", questionBankUpdateDTO.getTitle());
                return new BaseResponse<>(ResponseCode.ALREADY_EXIST_MESSAGE);
            }
            //更新
            QuestionBank questionBank = new QuestionBank();
            BeanUtils.copyProperties(questionBankUpdateDTO,questionBank);
            questionBankService.updateBank(questionBank);
            log.info("AdminController.update success: update questionBank success which is {}", questionBankUpdateDTO);
            //封装返回数据
            QuestionBankResponseDTO questionBankResponseDTO = new QuestionBankResponseDTO();
            QuestionBank questionBankById = questionBankService.getQuestionBankById(questionBank.getId());
            BeanUtils.copyProperties(questionBankById, questionBankResponseDTO);
            return new BaseResponse<>(ResponseCode.SUCCESS, questionBankResponseDTO);
        }catch (Exception e){
            log.error("AdminController.update error: questionBankUpdateDTO is {}", questionBankUpdateDTO, e);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }




}
