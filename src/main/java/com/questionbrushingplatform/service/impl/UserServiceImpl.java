package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.entity.User;
import com.questionbrushingplatform.mapper.UserMapper;
import com.questionbrushingplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 永
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserMapper userMapper;


    /**
     * 根据用户名查询用户
     * @param queryWrapper
     */
    public User getByUsername(LambdaQueryWrapper<User> queryWrapper) {
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 新增用户
     * @param user
     */
    public void addUser(User user) {
        boolean result = save(user);
        if (!result) {
            throw new RuntimeException("添加失败");
        }
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User getUserById(Long id) {
        return getById(id);
    }

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(Long id) {
        boolean result = removeById(id);
        if (!result) {
            throw new RuntimeException("删除失败");
        }
    }

    /**
     * 批量删除用户
     * @param ids
     */
    public void deleteUserByIds(List<Long> ids) {
        boolean result = removeByIds(ids);
        if (!result) {
            throw new RuntimeException("批量删除失败");
        }
    }

    /**
     * 更新用户
     * @param user
     */
    public void updateUser(User user) {
        boolean result = updateById(user);
        if (!result) {
            throw new RuntimeException("更新失败");
        }
    }

    /**
     * 获取用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<User> listUser(Integer pageNum, Integer pageSize) {
        return page(new Page<>(pageNum, pageSize));
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User listUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return getOne(queryWrapper);
    }

}
