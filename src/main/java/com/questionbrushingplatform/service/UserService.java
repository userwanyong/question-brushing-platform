package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.questionbrushingplatform.entity.User;

import java.util.List;


/**
 * @author 永
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户
     * @param queryWrapper
     */
    User getByUsername(LambdaQueryWrapper<User> queryWrapper);

    /**
     * 新增用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 批量删除用户
     * @param ids
     */
    void deleteUserByIds(List<Long> ids);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 分页查询用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<User> listUser(Integer pageNum, Integer pageSize);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User listUserByUsername(String username);
//    /**
//     * 新增用户
//     * @param userAddDTO
//     */
//    void add(UserAddRequestDTO userAddDTO);
//
//    /**
//     * 修改密码
//     * @param userUpdatePasswordDTO
//     */
//    void updatePassword(UserUpdatePasswordRequestDTO userUpdatePasswordDTO);
//
//    /**
//     * 通用更新时间
//     * @param id
//     */
////    void updateTimeById(Long id);
//
//    User getUserById(Long id);
//
//    /**
//     * 分页查询用户
//     * @param userQuery
//     * @return
//     */
//    PageDTO<UserResponseDTO> selectByPage(UserQuery userQuery);
//
//    /**
//     * 登录
//     * @param loginAndRegisterDTO
//     */
//    User login(LoginAndRegisterRequestDTO loginAndRegisterDTO);
//
////    /**
////     * 判断是否为管理员
////     */
////    void isAdmin();
//
//    /**
//     * 添加用户签到记录
//     * @param userId 用户id
//     * @return 当前用户是否已签到成功
//     */
//    boolean addUserSignIn(Long userId);
//
//
//    /**
//     * 获取用户某年份签到记录
//     * @param userId 用户id
//     * @param year 年份（为空表示当前年份）
//     * @return 签到记录
//     */
//    List<Integer> getUserSignInRecord(Long userId, Integer year);
}
