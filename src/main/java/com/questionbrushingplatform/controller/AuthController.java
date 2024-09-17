package com.questionbrushingplatform.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.exception.BaseException;
import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;

import com.questionbrushingplatform.dto.request.LoginAndRegisterRequestDTO;
import com.questionbrushingplatform.dto.request.UserAddRequestDTO;
import com.questionbrushingplatform.dto.response.LoginResponseDTO;
import com.questionbrushingplatform.entity.User;

import com.questionbrushingplatform.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author 永
 */
@RestController
@RequestMapping("/auth")
@Slf4j
@Api(tags = "授权模块")
public class AuthController {

    @Autowired
    private UserService userService;


    /**
     * 登录
     * @param loginAndRegisterDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public BaseResponse<LoginResponseDTO> login(@RequestBody LoginAndRegisterRequestDTO loginAndRegisterDTO) {

        User user=userService.login(loginAndRegisterDTO);
        StpUtil.login(user.getId());

        LoginResponseDTO loginVO = LoginResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .userAvatar(user.getUserAvatar())
                .nickname(user.getNickname())
                .userProfile(user.getUserProfile())
                .userRole(user.getUserRole())
                .password(StrUtil.hide(user.getPassword(), 1, 3))
                .createdTime(user.getCreatedTime())
                .editTime(user.getEditTime())
                .updateTime(user.getUpdateTime())
                .build();

        return new BaseResponse<>(ResponseCode.SUCCESS,loginVO);
    }


    /**
     * 注册
     * @param loginAndRegisterDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public BaseResponse<String> register(@RequestBody LoginAndRegisterRequestDTO loginAndRegisterDTO) {
        //检查一下是否填写了账号和密码
        if (StrUtil.isBlank(loginAndRegisterDTO.getUsername())||StrUtil.isBlank(loginAndRegisterDTO.getPassword())){
            log.error("UserController.register error: the username or password is empty which is {}", loginAndRegisterDTO);
            throw new BaseException(MessageConstant.ERROR_ACCOUNT_AND_PASSWORD);
        }
        UserAddRequestDTO userAddDTO = new UserAddRequestDTO();
        BeanUtils.copyProperties(loginAndRegisterDTO, userAddDTO);
        userService.add(userAddDTO);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public BaseResponse<String> logout() {
        StpUtil.logout();
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }


}