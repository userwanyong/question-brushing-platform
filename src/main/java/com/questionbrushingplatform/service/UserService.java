package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.questionbrushingplatform.pojo.entity.User;


public interface UserService extends IService<User> {
    /**
     * 管理员新增用户
     * @param user
     */
    void add(User user);
}
