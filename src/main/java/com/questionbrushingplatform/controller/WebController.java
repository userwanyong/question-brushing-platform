package com.questionbrushingplatform.controller;

import cn.hutool.core.util.StrUtil;
import com.questionbrushingplatform.common.constant.JwtClaimsConstant;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.context.BaseContext;
import com.questionbrushingplatform.common.properties.JwtProperties;
import com.questionbrushingplatform.common.result.Result;
import com.questionbrushingplatform.common.utils.JwtUtil;
import com.questionbrushingplatform.pojo.dto.LoginAndRegisterDTO;
import com.questionbrushingplatform.pojo.dto.UserAddDTO;
import com.questionbrushingplatform.pojo.entity.User;
import com.questionbrushingplatform.pojo.vo.LoginVO;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/web")
@Slf4j
@Api(tags = "登录注册接口")
public class WebController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     * @param loginAndRegisterDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<LoginVO> login(@RequestBody LoginAndRegisterDTO loginAndRegisterDTO) {

        User user=userService.login(loginAndRegisterDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        LoginVO loginVO = LoginVO.builder()
                .id(user.getId())
                .userAccount(user.getUserAccount())
                .userAvatar(user.getUserAvatar())
                .userName(user.getUserName())
                .userProfile(user.getUserProfile())
                .userRole(user.getUserRole())
                .userPassword(StrUtil.hide(user.getUserPassword(), 1, 3))
                .createTime(user.getCreateTime())
                .editTime(user.getEditTime())
                .updateTime(user.getUpdateTime())
                .token(token)
                .build();

        return Result.success(loginVO);
    }


    /**
     * 注册
     * @param loginAndRegisterDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public Result<String> register(@RequestBody LoginAndRegisterDTO loginAndRegisterDTO) {
        UserAddDTO userAddDTO = new UserAddDTO();
        BeanUtils.copyProperties(loginAndRegisterDTO, userAddDTO);
        userService.add(userAddDTO);
        return Result.success(MessageConstant.USER_REGISTER_SUCCESS);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public Result<String> logout() {
        BaseContext.removeCurrentId();
        return Result.success(MessageConstant.USER_LOGOUT_SUCCESS);
    }





}
