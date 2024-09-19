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

}
