package com.questionbrushingplatform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.constant.PasswordConstant;
import com.questionbrushingplatform.common.context.BaseContext;
import com.questionbrushingplatform.common.exception.BaseException;
import com.questionbrushingplatform.mapper.UserMapper;
import com.questionbrushingplatform.pojo.dto.LoginAndRegisterDTO;
import com.questionbrushingplatform.pojo.dto.PageDTO;
import com.questionbrushingplatform.pojo.dto.UserAddDTO;
import com.questionbrushingplatform.pojo.dto.UserUpdatePasswordDTO;
import com.questionbrushingplatform.pojo.entity.User;
import com.questionbrushingplatform.pojo.query.UserQuery;
import com.questionbrushingplatform.pojo.vo.UserVO;
import com.questionbrushingplatform.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通用更新时间
     * @param id
     */
    public void updateTimeById(Long id) {
        User user = userMapper.selectById(id);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    /**
     * 判断是否为管理员
     */
    public void isAdmin() {
        Long currentId = BaseContext.getCurrentId();
        User dbUser = userMapper.selectById(currentId);
        if (!dbUser.getUserRole().equals("admin")){
            throw new BaseException(MessageConstant.USER_NOT_ALLOWED);
        }
    }

    /**
     * 修改密码
     * @param userUpdatePasswordDTO
     */
    public void updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO) {
        //要保证密码长度符合规定
        if (userUpdatePasswordDTO.getNewPassword().length() < 6 || userUpdatePasswordDTO.getNewPassword().length() > 16){
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        if (userUpdatePasswordDTO.getOldPassword().length() < 6 || userUpdatePasswordDTO.getOldPassword().length() > 16){
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        if (userUpdatePasswordDTO.getConfirmPassword().length() < 6 || userUpdatePasswordDTO.getConfirmPassword().length() > 16){
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        Long currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        if (!user.getUserPassword().equals(userUpdatePasswordDTO.getOldPassword())){
            throw new BaseException(MessageConstant.OLD_PASSWORD_ERROR);
        }
        if (!userUpdatePasswordDTO.getNewPassword().equals(userUpdatePasswordDTO.getConfirmPassword())){
            throw new BaseException(MessageConstant.CONFIRM_PASSWORD_ERROR);
        }
        user.setUserPassword(userUpdatePasswordDTO.getNewPassword());
        updateTimeById(currentId);
        userMapper.updateById(user);
    }


    /**
     * 新增用户
     * @param userAddDTO
     */
    public void add(UserAddDTO userAddDTO) {
        //限制账号长度为11位
        if (userAddDTO.getUserAccount().length() != 11){
            throw new BaseException(MessageConstant.ACCOUNT_NOT_ALLOWED);
        }
        //先判断数据库是否有该账号，如果有，则不新增
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAddDTO.getUserAccount());
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser != null){
            throw new BaseException(MessageConstant.ACCOUNT_EXISTS);
        }
        //如果不存在，则新增
        if (userAddDTO.getUserRole()==null|| userAddDTO.getUserRole().isEmpty()){
            userAddDTO.setUserRole("user");//默认角色为用户
        }
        //如果没有输入密码，则默认密码为123456
        if (userAddDTO.getUserPassword()==null||userAddDTO.getUserPassword().isEmpty()) {
            userAddDTO.setUserPassword(PasswordConstant.DEFAULT_PASSWORD);
        }
        //限制密码长度在6-16位
        else if (userAddDTO.getUserPassword().length() < 6 || userAddDTO.getUserPassword().length() > 16){
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        //如果没有输入用户名，则默认用户名为账号
        if (userAddDTO.getUserName()==null||userAddDTO.getUserName().isEmpty()){
            userAddDTO.setUserName(userAddDTO.getUserAccount());
        }
        User user = new User();
        user.setUserAccount(userAddDTO.getUserAccount());
        BeanUtils.copyProperties(userAddDTO,user);
        userMapper.insert(user);
    }

    /**
     * 分页查询用户
     * @param userQuery
     * @return
     */
    public PageDTO<UserVO> selectByPage(UserQuery userQuery) {
        // 1.构建条件
        Page<User> page = userQuery.toMpPage("createTime", false);
        // 2.分页查询
        Page<User> p = lambdaQuery()
                .like(userQuery.getUserAccount()!=null,User::getUserAccount,userQuery.getUserAccount())
                .like(userQuery.getUserName()!=null,User::getUserName,userQuery.getUserName())
                .like(userQuery.getUserRole()!=null,User::getUserRole,userQuery.getUserRole())
                .between(userQuery.getStartTime()!=null&&userQuery.getEndTime()!=null,User::getCreateTime,userQuery.getStartTime(),userQuery.getEndTime())
                .between(userQuery.getStartTime()!=null&&userQuery.getEndTime()!=null,User::getUpdateTime,userQuery.getStartTime(),userQuery.getEndTime())
                .between(userQuery.getStartTime()!=null&&userQuery.getEndTime()!=null,User::getEditTime,userQuery.getStartTime(),userQuery.getEndTime())
                .page(page);
        // 3.封装VO结果
        return PageDTO.of(page, UserVO.class);
    }

    /**
     * 登录
     * @param loginAndRegisterDTO
     */
    public User login(LoginAndRegisterDTO loginAndRegisterDTO) {
        if (StrUtil.isBlank(loginAndRegisterDTO.getUserAccount())||StrUtil.isBlank(loginAndRegisterDTO.getUserPassword())){
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        //根据账号查询数据库的用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, loginAndRegisterDTO.getUserAccount());
        User dbUser = userMapper.selectOne(queryWrapper);
        //若数据库无该账号，抛出异常
        if (dbUser == null){
            throw new BaseException(MessageConstant.ACCOUNT_OR_PASSWORD_NOT_FOUND);
        }
        //若所输入密码与数据库密码不一致，抛出异常
        if (!loginAndRegisterDTO.getUserPassword().equals(dbUser.getUserPassword())){
            throw new BaseException(MessageConstant.ACCOUNT_OR_PASSWORD_NOT_FOUND);
        }
        return dbUser;
    }


}
