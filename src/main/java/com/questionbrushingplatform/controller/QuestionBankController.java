//package com.questionbrushingplatform.controller;
//
//import com.questionbrushingplatform.common.constant.MessageConstant;
//import com.questionbrushingplatform.common.exception.BaseException;
//import com.questionbrushingplatform.common.resp.BaseResponse;
//import com.questionbrushingplatform.common.resp.ResponseCode;
//import com.questionbrushingplatform.pojo.dto.PageDTO;
//import com.questionbrushingplatform.pojo.dto.QuestionBankAddDTO;
//import com.questionbrushingplatform.pojo.dto.QuestionBankUpdateDTO;
//import com.questionbrushingplatform.entity.QuestionBank;
//import com.questionbrushingplatform.pojo.query.QuestionBankQuery;
//import com.questionbrushingplatform.pojo.vo.QuestionBankVO;
//import com.questionbrushingplatform.service.QuestionBankService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//
///**
// * @author 永
// */
//@RestController
//@RequestMapping("/questionBank")
//@Slf4j
//@Api(tags = "题库接口")
//public class QuestionBankController {
//    //以下所有接口只有管理员具有权限
//
//    @Autowired
//    private QuestionBankService questionBankService;
//
//
//    /**
//     * 新增题库
//     * @param questionBankAddDTO
//     * @return
//     */
//    @PostMapping("/add")
//    @ApiOperation("新增题库")
//    public BaseResponse<Boolean> add(@RequestBody QuestionBankAddDTO questionBankAddDTO) {
//        //判断是否为管理员
////        userService.isAdmin();
//        questionBankService.add(questionBankAddDTO);
//        return new BaseResponse<>(ResponseCode.SUCCESS);
//    }
//
//    /**
//     * 根据id删除题库
//     * @param id
//     * @return
//     */
//    @PostMapping("/deleteById/{id}")
//    @ApiOperation("根据id删除题库")
//    public BaseResponse<Boolean> deleteById(@PathVariable Long id) {
//        //判断是否为管理员
////        userService.isAdmin();
//        questionBankService.deleteById(id);
//        return new BaseResponse<>(ResponseCode.SUCCESS);
//    }
//
//
//    /**
//     * 批量删除
//     * @param ids
//     * @return
//     */
//    @PostMapping("/deleteByIds")
//    @ApiOperation("批量删除题库")
//    public BaseResponse<Boolean> deleteByIds(@RequestBody Long[] ids) {
//        //判断是否为管理员
////        userService.isAdmin();
//        questionBankService.deleteByIds(ids);
//        return new BaseResponse<>(ResponseCode.SUCCESS);
//    }
//
//    /**
//     * 修改题库
//     * @param questionBankUpdateDTO
//     * @return
//     */
//    @PostMapping("/update")
//    @ApiOperation("修改题库")
//    public BaseResponse<QuestionBankUpdateDTO> update(@RequestBody QuestionBankUpdateDTO questionBankUpdateDTO) {
//        //判断是否为管理员
////        userService.isAdmin();
//        //判断该id是否已存在
//        if (questionBankService.getById(questionBankUpdateDTO.getId())==null) {
//            log.error("QuestionBankController.update error: the question bank does not exist which is {}", questionBankUpdateDTO.getId());
//            throw new BaseException(MessageConstant.QUEST_BANK_NOT_FOUND);
//        }
//        questionBankService.isExist(questionBankUpdateDTO.getTitle());
//        QuestionBank questionBank = new QuestionBank();
//        BeanUtils.copyProperties(questionBankUpdateDTO,questionBank);
////        questionBank.setUpdateTime(LocalDateTime.now());
//        questionBankService.updateById(questionBank);
//        return new BaseResponse<>(ResponseCode.SUCCESS, questionBankUpdateDTO);
//    }
//
//    /**
//     * 根据id查询题库
//     * @param id
//     * @return
//     */
//    @GetMapping("/getById/{id}")
//    @ApiOperation("根据id查询题库")
//    public BaseResponse<QuestionBank> getById(@PathVariable Long id) {
//        //判断是否为管理员
////        userService.isAdmin();
//        //判断该id是否存在
//        if (questionBankService.getById(id)==null) {
//            log.error("QuestionBankController.getById error: the question bank does not exist which is {}", id);
//            throw new BaseException(MessageConstant.QUEST_BANK_NOT_FOUND);
//        }
//        QuestionBank res = questionBankService.getById(id);
//        return new BaseResponse<>(ResponseCode.SUCCESS, res);
//    }
//
//    /**
//     * 分页查询题库
//     * @param questionBankQuery
//     * @return
//     */
//    @GetMapping("/selectByPage")
//    @ApiOperation("分页查询题库")
//    public PageDTO<QuestionBankVO> selectByPage(QuestionBankQuery questionBankQuery) {
//        //判断是否为管理员
////        userService.isAdmin();
//        PageDTO<QuestionBankVO> page=questionBankService.selectByPage(questionBankQuery);
//        return page;
//    }
//
//
//
//}
