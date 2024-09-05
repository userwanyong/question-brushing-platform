package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.result.Result;
import com.questionbrushingplatform.pojo.dto.PageDTO;
import com.questionbrushingplatform.pojo.dto.QuestionBankAddDTO;
import com.questionbrushingplatform.pojo.entity.QuestionBank;
import com.questionbrushingplatform.pojo.query.QuestionBankQuery;
import com.questionbrushingplatform.pojo.vo.QuestionBankVO;
import com.questionbrushingplatform.service.QuestionBankService;
import com.questionbrushingplatform.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/questionBank")
@Slf4j
@Api(tags = "题库接口")
public class QuestionBankController {
    //以下所有接口只有管理员具有权限

    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private UserService userService;


    /**
     * 新增题库
     * @param questionBankAddDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增题库")
    public Result add(@RequestBody QuestionBankAddDTO questionBankAddDTO) {
        //判断是否为管理员
//        userService.isAdmin();
        questionBankService.add(questionBankAddDTO);
        return Result.success("新增成功");
    }

    /**
     * 根据id删除题库
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation("根据id删除题库")
    public Result deleteById(@PathVariable Long id) {
        //判断是否为管理员
//        userService.isAdmin();
        questionBankService.deleteById(id);
        return Result.success("删除成功");
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/deleteByIds")
    @ApiOperation("批量删除题库")
    public Result deleteByIds(@RequestBody Long[] ids) {
        //判断是否为管理员
//        userService.isAdmin();
        questionBankService.deleteByIds(ids);
        return Result.success("删除成功");
    }


    /**
     * 根据id查询题库
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    @ApiOperation("根据id查询题库")
    public Result getById(@PathVariable Long id) {
        //判断是否为管理员
//        userService.isAdmin();
        return Result.success(questionBankService.getById(id));
    }

    /**
     * 分页查询题库
     * @param questionBankQuery
     * @return
     */
    @GetMapping("/selectByPage")
    @ApiOperation("分页查询题库")
    public PageDTO<QuestionBankVO> selectByPage(QuestionBankQuery questionBankQuery) {
        //判断是否为管理员
//        userService.isAdmin();
        PageDTO<QuestionBankVO> page=questionBankService.selectByPage(questionBankQuery);
        return page;
    }

    /**
     * 修改题库
     * @param questionBankAddDTO
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改题库")
    public Result update(@RequestBody QuestionBankAddDTO questionBankAddDTO) {
        //判断是否为管理员
//        userService.isAdmin();
        QuestionBank questionBank = new QuestionBank();
        BeanUtils.copyProperties(questionBankAddDTO,questionBank);
        questionBank.setUpdateTime(LocalDateTime.now());
        questionBankService.updateById(questionBank);
        return Result.success("修改成功");
    }

}
