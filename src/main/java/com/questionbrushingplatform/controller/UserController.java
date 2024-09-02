package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.result.Result;
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

import java.time.LocalDateTime;
import java.util.List;

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
    public Result add(@RequestBody UserAddDTO userAddDTO) {
        userService.add(userAddDTO);
        return Result.success("新增成功");
    }

    /**
     * 单个删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    @ApiOperation("单个删除用户")
    public Result deleteById(@PathVariable Long id) {
        //判断是否为管理员
        userService.isAdmin();
        //不能重复删除
        if (userService.getById(id) == null) {
            return Result.error(MessageConstant.USER_NOT_FOUND);
        }
        userService.updateTimeById(id);
        userService.removeById(id);
        return Result.success("删除成功");
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteByIds")
    @ApiOperation("批量删除用户")
    public Result deleteByIds(@RequestBody List<Long> ids) {
        //判断是否为管理员
        userService.isAdmin();
        for (Long id : ids) {
            //不能重复删除
            if (userService.getById(id) == null) {
                return Result.error(MessageConstant.USER_NOT_FOUND);
            }
            userService.updateTimeById(id);
        }
        userService.removeByIds(ids);
        return Result.success("删除成功");
    }

    /**
     * 修改用户信息
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("修改用户信息")
    public Result update(@RequestBody UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("修改成功");
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    @ApiOperation("根据id查询用户")
    public Result getById(@PathVariable Long id) {
        //判断是否为管理员
        userService.isAdmin();
        User user = userService.getById(id);
        if (user == null) {
            return Result.error(MessageConstant.USER_NOT_FOUND);
        }
        return Result.success(user);
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
        userService.isAdmin();
        PageDTO<UserVO> page=userService.selectByPage(userQuery);
        return page;
    }


    /**
     * 修改密码
     * @param userUpdatePasswordDTO
     * @return
     */
    @PutMapping("/updatePassword")
    @ApiOperation("修改密码")
    public Result updatePassword(@RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO) {
        userService.updatePassword(userUpdatePasswordDTO);
        return Result.success("修改成功");
    }

}
