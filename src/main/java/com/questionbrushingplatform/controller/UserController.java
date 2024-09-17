package com.questionbrushingplatform.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.pojo.dto.PageDTO;
import com.questionbrushingplatform.pojo.dto.UserAddDTO;
import com.questionbrushingplatform.pojo.dto.UserDTO;
import com.questionbrushingplatform.pojo.dto.UserUpdatePasswordDTO;
import com.questionbrushingplatform.pojo.entity.User;
import com.questionbrushingplatform.pojo.query.UserQuery;
import com.questionbrushingplatform.pojo.vo.UserVO;
import com.questionbrushingplatform.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 永
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 新增用户
     * @param userAddDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增用户")
    public BaseResponse<Boolean> add(@RequestBody UserAddDTO userAddDTO) {
        userService.add(userAddDTO);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 单个删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    @ApiOperation("单个删除用户")
    public BaseResponse<Boolean> deleteById(@PathVariable Long id) {
        //判断是否为管理员
//        userService.isAdmin();
        //不能重复删除
        if (userService.getById(id) == null) {
            log.error("UserController.deleteById error: the user not exist which is {}", id);
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
//        userService.updateTimeById(id);
        userService.removeById(id);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteByIds")
    @ApiOperation("批量删除用户")
    public BaseResponse<Boolean> deleteByIds(@RequestBody List<Long> ids) {
        //判断是否为管理员
//        userService.isAdmin();
        for (Long id : ids) {
            //不能重复删除
            if (userService.getById(id) == null) {
                log.error("UserController.deleteById error: the user not exist which is {}", id);
                return new BaseResponse<>(ResponseCode.NO_DATA);
            }
//            userService.updateTimeById(id);
        }
        userService.removeByIds(ids);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 修改用户信息
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("修改用户信息")
    public BaseResponse<UserDTO> update(@RequestBody UserDTO userDTO) {
        //判断是否为管理员
//        userService.isAdmin();
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
//        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 修改当前用户信息
     * @param userDTO
     * @return
     */
    @PutMapping("/updateCurrentInfo")
    @ApiOperation("修改当前用户信息")
    public BaseResponse<UserDTO> updateCurrentInfo(@RequestBody UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setId(StpUtil.getLoginIdAsLong());
//        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 修改当前登录用户密码
     * @param userUpdatePasswordDTO
     * @return
     */
    @PutMapping("/updatePassword")
    @ApiOperation("修改当前登录用户密码")
    public BaseResponse<UserUpdatePasswordDTO> updatePassword(@RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO) {
        userService.updatePassword(userUpdatePasswordDTO);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    @ApiOperation("根据id查询用户")
    public BaseResponse<User> getById(@PathVariable Long id) {
        //判断是否为管理员
//        userService.isAdmin();
        User user = userService.getById(id);
        if (user == null) {
            log.error("UserController.getById error: the user not exist which is {}", id);
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        return new BaseResponse<>(ResponseCode.SUCCESS,user);
    }

    /**
     * 获取当前用户信息
     * @return
     */
    @GetMapping("/getCurrentUser")
    @ApiOperation("获取当前用户信息")
    public BaseResponse<User> getCurrentUser() {
        User user = userService.getById((Serializable) StpUtil.getLoginId());
        if (user == null) {
            log.error("UserController.getCurrentUser error: the user not exist which is {}", StpUtil.getLoginId());
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        return new BaseResponse<>(ResponseCode.SUCCESS,user);
    }


    /**
     * 分页查询用户
     * @param userQuery
     * @return
     */
    @GetMapping("/selectByPage")
    @ApiOperation("分页查询用户")
    public PageDTO<UserVO> selectByPage(UserQuery userQuery) {
        //判断是否为管理员
//        userService.isAdmin();
        PageDTO<UserVO> page=userService.selectByPage(userQuery);
        return page;
    }


    /**
     * 添加用户签到记录
     * @return
     */
    @PostMapping("/addSignIn")
    @ApiOperation("添加用户签到记录")
    public BaseResponse<Boolean> addSignIn() {
        //必须登录才能签到
        if (!StpUtil.isLogin()) {
            log.error("UserController.addSignIn error: the user not login");
            return new BaseResponse<>(ResponseCode.NO_LOGIN);
        }
        boolean result = userService.addUserSignIn(StpUtil.getLoginIdAsLong());
        return new BaseResponse<>(result ? ResponseCode.SUCCESS : ResponseCode.SYSTEM_ERROR);
    }

    /**
     * 获取用户签到记录
     * @param year
     * @return
     */
    @GetMapping("/getSignInRecord")
    @ApiOperation("获取用户签到记录")
    public BaseResponse<List<Integer>> getSignInRecord(Integer year) {
        //必须登录
        if (!StpUtil.isLogin()) {
            log.error("UserController.getSignInRecord error: the user not login");
            return new BaseResponse<>(ResponseCode.NO_LOGIN);
        }
        List<Integer> userSignInRecord = userService.getUserSignInRecord(StpUtil.getLoginIdAsLong(), year);
        return new BaseResponse<>(ResponseCode.SUCCESS, userSignInRecord);
    }

}
