package com.questionbrushingplatform.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.constant.NumberConstant;
import com.questionbrushingplatform.common.exception.BaseException;
import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.dto.request.LoginAndRegisterRequestDTO;
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
     * @param loginAndRegisterDTO 用户登录信息
     * @return 该用户信息
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public BaseResponse<LoginResponseDTO> login(@RequestBody LoginAndRegisterRequestDTO loginAndRegisterDTO) {

        if (StrUtil.isBlank(loginAndRegisterDTO.getUsername())||StrUtil.isBlank(loginAndRegisterDTO.getPassword())){
            log.error("AuthController.login error: the userAccount or userPassword is empty which is {}", loginAndRegisterDTO);
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        //根据账号查询数据库的用户信息
        User dbUser = userService.listUserByUsername(loginAndRegisterDTO.getUsername());
        //若数据库无该账号，抛出异常
        if (dbUser == null){
            log.error("AuthController.login error: the userAccount is not found which is {}", loginAndRegisterDTO.getUsername());
            throw new BaseException(MessageConstant.ACCOUNT_OR_PASSWORD_NOT_FOUND);
        }
        //若所输入密码与数据库密码不一致，抛出异常
        if (!loginAndRegisterDTO.getPassword().equals(dbUser.getPassword())){
            log.error("AuthController.login error: the userPassword is not correct which is {}", loginAndRegisterDTO.getPassword());
            throw new BaseException(MessageConstant.ACCOUNT_OR_PASSWORD_NOT_FOUND);
        }
        StpUtil.login(dbUser.getId());

        LoginResponseDTO loginVO = LoginResponseDTO.builder()
                .id(dbUser.getId())
                .username(dbUser.getUsername())
                .userAvatar(dbUser.getUserAvatar())
                .nickname(dbUser.getNickname())
                .userProfile(dbUser.getUserProfile())
                .userRole(dbUser.getUserRole())
                .password(StrUtil.hide(dbUser.getPassword(), 1, 3))
                .createdTime(dbUser.getCreatedTime())
                .updatedTime(dbUser.getUpdatedTime())
                .build();

        return new BaseResponse<>(ResponseCode.SUCCESS,loginVO);
    }


    /**
     * 注册
     * @param loginAndRegisterDTO 用户注册信息
     * @return 成功/失败
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public BaseResponse<String> register(@RequestBody LoginAndRegisterRequestDTO loginAndRegisterDTO) {
        //检查一下是否填写了账号和密码
        if (StrUtil.isBlank(loginAndRegisterDTO.getUsername())||StrUtil.isBlank(loginAndRegisterDTO.getPassword())){
            log.error("AuthController.register error: the userAccount or userPassword is empty which is {}", loginAndRegisterDTO);
            throw new BaseException(MessageConstant.ERROR_ACCOUNT_AND_PASSWORD);
        }
        //先判断数据库是否有该账号，如果有，则不新增,如果不存在，则新增
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginAndRegisterDTO.getUsername());
        User dbUser = userService.getByUsername(queryWrapper);
        if (dbUser != null){
            log.error("AuthController.add error: the userAccount already exists which is {}", loginAndRegisterDTO.getUsername());
            throw new BaseException(MessageConstant.ACCOUNT_EXISTS);
        }
        //限制密码长度在6-16位
        if (loginAndRegisterDTO.getPassword().length() < 6 || loginAndRegisterDTO.getPassword().length() > 16){
            log.error("AuthController.add error: the userPassword length not allowed which is {}", loginAndRegisterDTO.getPassword());
            throw new BaseException(MessageConstant.ERROR_ACCOUNT_AND_PASSWORD);
        }
        User user = new User();
        BeanUtils.copyProperties(loginAndRegisterDTO, user);
        //如果没有输入用户名，则默认用户名为账号
        if (user.getUsername()==null||user.getUsername().isEmpty()){
            user.setUsername(user.getUsername());
        }
        user.setUserRole(NumberConstant.USER);
        userService.addUser(user);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 退出登录
     * @return 成功/失败
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public BaseResponse<String> logout() {
        StpUtil.logout();
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }


}
