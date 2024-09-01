package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.constant.PasswordConstant;
import com.questionbrushingplatform.common.exception.BaseException;
import com.questionbrushingplatform.mapper.UserMapper;
import com.questionbrushingplatform.pojo.entity.User;
import com.questionbrushingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 管理员新增用户
     * @param user
     */
    public void add(User user) {
        //限制账号长度为11位
        if (user.getUserAccount().length() != 11){
            throw new BaseException(MessageConstant.ACCOUNT_NOT_ALLOWED);
        }
        //先判断数据库是否有该账号，如果有，则不新增
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, user.getUserAccount());
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser != null){
            throw new BaseException(MessageConstant.ACCOUNT_EXISTS);
        }
        //如果不存在，则新增
        if (user.getUserRole()==null|| user.getUserRole().isEmpty()){
            user.setUserRole("user");
        }
        user.setUserPassword(PasswordConstant.DEFAULT_PASSWORD);//默认密码123456
        user.setUserName(user.getUserAccount());//用户昵称默认为用户账号
        userMapper.insert(user);
    }

}
