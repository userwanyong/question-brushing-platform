package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.result.Result;
import com.questionbrushingplatform.pojo.entity.User;
import com.questionbrushingplatform.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 管理员新增用户
     * @param user
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("管理员新增用户")
    public Result add(@RequestBody User user) {
        userService.add(user);
        return Result.success("新增成功");
    }
}
