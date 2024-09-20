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
     * @param queryWrapper 查询条件
     */
    User getByUsername(LambdaQueryWrapper<User> queryWrapper);

    /**
     * 新增用户
     * @param user 用户信息
     */
    void addUser(User user);

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return 用户信息
     */
    User getUserById(Long id);

    /**
     * 删除用户
     * @param id 用户id
     */
    void deleteUser(Long id);

    /**
     * 批量删除用户
     * @param ids 用户id
     */
    void deleteUserByIds(List<Long> ids);

    /**
     * 更新用户
     * @param user 用户信息
     */
    void updateUser(User user);

    /**
     * 获取用户列表
     * @param pageNum 当前页码
     * @param pageSize 每页数量
     * @return 用户列表
     */
    Page<User> listUser(Integer pageNum, Integer pageSize);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User listUserByUsername(String username);

}
