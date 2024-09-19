package com.questionbrushingplatform.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.constant.RedisConstant;
import com.questionbrushingplatform.common.exception.BaseException;
import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.dto.request.UserRequestDTO;
import com.questionbrushingplatform.dto.request.UserUpdatePasswordRequestDTO;
import com.questionbrushingplatform.dto.response.UserResponseDTO;
import com.questionbrushingplatform.entity.User;
import com.questionbrushingplatform.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.BitSet;
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
    @Autowired
    private RedissonClient redissonClient;



    /**
     * 修改当前用户信息
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("修改当前用户信息")
    public BaseResponse<UserResponseDTO> updateInfo(@RequestBody UserRequestDTO userDTO) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userDTO,user);
            user.setId(StpUtil.getLoginIdAsLong());
            userService.updateUser(user);
            log.info("UserController.updateCurrentInfo success: {}", user);
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("UserController.updateCurrentInfo error: {}", e.getMessage());
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }

    /**
     * 修改当前登录用户密码
     * @param userUpdatePasswordDTO
     * @return
     */
    @PutMapping("/updatePassword")
    @ApiOperation("修改当前登录用户密码")
    public BaseResponse<UserUpdatePasswordRequestDTO> updatePassword(@RequestBody UserUpdatePasswordRequestDTO userUpdatePasswordDTO) {
        try {
            //要保证密码长度符合规定
            if (userUpdatePasswordDTO.getNewPassword().length() < 6 || userUpdatePasswordDTO.getNewPassword().length() > 16){
                log.error("UserController.updatePassword error: the newPassword length not allowed which is {}", userUpdatePasswordDTO.getNewPassword());
                throw new BaseException(MessageConstant.ERROR_DATABASE);
            }
            if (userUpdatePasswordDTO.getOldPassword().length() < 6 || userUpdatePasswordDTO.getOldPassword().length() > 16){
                log.error("UserController.updatePassword error: the oldPassword length not allowed which is {}", userUpdatePasswordDTO.getOldPassword());
                throw new BaseException(MessageConstant.ERROR_DATABASE);
            }
            if (userUpdatePasswordDTO.getConfirmPassword().length() < 6 || userUpdatePasswordDTO.getConfirmPassword().length() > 16){
                log.error("UserController.updatePassword error: the confirmPassword length not allowed which is {}", userUpdatePasswordDTO.getConfirmPassword());
                throw new BaseException(MessageConstant.ERROR_DATABASE);
            }
            Long currentId = StpUtil.getLoginIdAsLong();
            User user = userService.getUserById(currentId);
            if (!user.getPassword().equals(userUpdatePasswordDTO.getOldPassword())){
                log.error("UserController.updatePassword error: the oldPassword is not correct which is {}", userUpdatePasswordDTO.getOldPassword());
                throw new BaseException(MessageConstant.OLD_PASSWORD_ERROR);
            }
            if (!userUpdatePasswordDTO.getNewPassword().equals(userUpdatePasswordDTO.getConfirmPassword())){
                log.error("UserController.updatePassword error: the newPassword and confirmPassword is not same which is {}", userUpdatePasswordDTO.getNewPassword());
                throw new BaseException(MessageConstant.CONFIRM_PASSWORD_ERROR);
            }
            user.setPassword(userUpdatePasswordDTO.getNewPassword());
            userService.updateUser(user);
            log.info("UserController.updatePassword success: updatePassword success which is {}", userUpdatePasswordDTO.getNewPassword());
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("UserController.updatePassword error: {}", e.getMessage());
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
    }


    /**
     * 获取当前用户信息
     * @return
     */
    @GetMapping("/get")
    @ApiOperation("获取当前用户信息")
    public BaseResponse<UserResponseDTO> get() {
        User user = userService.getUserById(StpUtil.getLoginIdAsLong());
        if (user == null) {
            log.error("UserController.getCurrentUser error: the user not exist which is {}", StpUtil.getLoginId());
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user,userResponseDTO);
        log.info("UserController.getCurrentUser success: {}", user);
        return new BaseResponse<>(ResponseCode.SUCCESS,userResponseDTO);
    }


    /**
     * 添加用户签到记录
     * @return
     */
    @PostMapping("/addSignIn")
    @ApiOperation("添加用户签到记录")
    public BaseResponse<Boolean> addSignIn() {
        try {
            //必须登录才能签到
            if (!StpUtil.isLogin()) {
                log.error("UserController.addSignIn error: the user not login");
                return new BaseResponse<>(ResponseCode.NO_LOGIN);
            }
            LocalDate date = LocalDate.now();
            String key = RedisConstant.getUserSignInRedisKey(date.getYear(), StpUtil.getLoginIdAsLong());

            //查询Redis的BitMap
            RBitSet bitSet = redissonClient.getBitSet(key);
            //获取当前如期是一年中的第几天，作为偏移量（从1开始计数）
            int offset = date.getDayOfYear();
            //查询当天是否签到
            if (!bitSet.get(offset)){
                //如果当天未签到，则签到
                bitSet.set(offset, true);
                //设置过期时间为下一年的第一天
//            bitSet.expire(LocalDate.ofYearDay(date.getYear()+1,1).toEpochDay(), TimeUnit.SECONDS);
//            bitSet.expire(6, TimeUnit.DAYS);
            }
            //当天已签到
            log.info("UserController.addSignIn success: add userSignIn success which is {}", StpUtil.getLoginIdAsLong());
            return new BaseResponse<>(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error("UserController.addSignIn error: {}", e.getMessage());
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
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
        if (year == null){
            LocalDate date = LocalDate.now();
            year = date.getYear();
        }
        String key = RedisConstant.getUserSignInRedisKey(year, StpUtil.getLoginIdAsLong());
        //查询Redis的BitMap
        RBitSet bitSet = redissonClient.getBitSet(key);
        //加载BitSet到内存中，避免后续读取时发送多次请求
        BitSet newBitSet = bitSet.asBitSet();
        //统计签到的日期
        ArrayList<Integer> list = new ArrayList<>();
        //从索引0开始查找下一个被设置为1的位
        int index = newBitSet.nextSetBit(0);
        while (index >= 0){
            list.add(index);
            //从下一位开始继续查找被设为1的位
            index = newBitSet.nextSetBit(index + 1);
        }
        log.info("UserController.getUserSignInRecord success: getUserSignInRecord success which is {}", list);
        return new BaseResponse<>(ResponseCode.SUCCESS, list);
    }

}
