package com.questionbrushingplatform.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.questionbrushingplatform.dto.request.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.constant.PasswordConstant;
import com.questionbrushingplatform.common.constant.RedisConstant;
import com.questionbrushingplatform.common.exception.BaseException;
import com.questionbrushingplatform.dto.request.LoginAndRegisterRequestDTO;
import com.questionbrushingplatform.dto.request.UserAddRequestDTO;
import com.questionbrushingplatform.dto.request.UserUpdatePasswordRequestDTO;
import com.questionbrushingplatform.dto.response.UserResponseDTO;
import com.questionbrushingplatform.entity.User;
import com.questionbrushingplatform.mapper.UserMapper;
import com.questionbrushingplatform.pojo.query.UserQuery;
import com.questionbrushingplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @author 永
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserMapper userMapper;


    /**
     * 新增用户
     * @param userAddDTO 用户新增信息
     */
    public void add(UserAddRequestDTO userAddDTO) {
        //限制账号长度为11位
        if (userAddDTO.getUsername().length() != 11){
            log.error("UserServiceImpl.add error: the username length not allowed which is {}", userAddDTO.getUsername());
            throw new BaseException(MessageConstant.ACCOUNT_NOT_ALLOWED);
        }
        //先判断数据库是否有该账号，如果有，则不新增
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userAddDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser != null){
            log.error("UserServiceImpl.add error: the username already exists which is {}", userAddDTO.getUsername());
            throw new BaseException(MessageConstant.ACCOUNT_EXISTS);
        }
        //如果不存在，则新增
        if (userAddDTO.getUserRole()==null|| userAddDTO.getUserRole().isEmpty()){
            userAddDTO.setUserRole("user");//默认角色为用户
        }
        //如果没有输入密码，则默认密码为123456
        if (userAddDTO.getPassword()==null||userAddDTO.getPassword().isEmpty()) {
            userAddDTO.setPassword(PasswordConstant.DEFAULT_PASSWORD);
        }
        //限制密码长度在6-16位
        else if (userAddDTO.getPassword().length() < 6 || userAddDTO.getPassword().length() > 16){
            log.error("UserServiceImpl.add error: the password length not allowed which is {}", userAddDTO.getPassword());
            throw new BaseException(MessageConstant.ERROR_ACCOUNT_AND_PASSWORD);
        }
        //如果没有输入用户名，则默认用户名为账号
        if (userAddDTO.getNickname()==null||userAddDTO.getNickname().isEmpty()){
            userAddDTO.setNickname(userAddDTO.getUsername());
        }
        User user = new User();
        user.setUsername(userAddDTO.getUsername());
        BeanUtils.copyProperties(userAddDTO,user);
        userMapper.insert(user);
        log.info("UserServiceImpl.add success: add userAddDTO success which is {}", userAddDTO);
    }

    /**
     * 添加用户签到记录
     * @param userId 用户id
     * @return 当前用户是否已签到成功
     */
    public boolean addUserSignIn(Long userId) {

        LocalDate date = LocalDate.now();
        String key = RedisConstant.getUserSignInRedisKey(date.getYear(), userId);

        //查询Redis的BitMap
        RBitSet bitSet = redissonClient.getBitSet(key);
        //获取当前如期是一年中的第几天，作为偏移量（从1开始计数）
        int offset = date.getDayOfYear();
        //查询当天是否签到
        if (!bitSet.get(offset)){
            //如果当天未签到，则签到
            bitSet.set(offset, true);
            //设置过期时间为下一年的第一天
//            bitSet.expire(LocalDate.ofYearDay(date.getYear()+1,1).toEpochDay(), TimeUnit.SECONDS);
//            bitSet.expire(6, TimeUnit.DAYS);
        }
        //当天已签到
        log.info("UserServiceImpl.addUserSignIn success: add userSignIn success which is {}", userId);
        return true;
    }

    /**
     * 通用更新时间
     * @param id
     */
//    public void updateTimeById(Long id) {
//        User user = userMapper.selectById(id);
//        user.setUpdateTime(LocalDateTime.now());
//        userMapper.updateById(user);
//    }

    /**
     * 修改密码
     * @param userUpdatePasswordDTO 用户修改密码信息
     */
    public void updatePassword(UserUpdatePasswordRequestDTO userUpdatePasswordDTO) {
        //要保证密码长度符合规定
        if (userUpdatePasswordDTO.getNewPassword().length() < 6 || userUpdatePasswordDTO.getNewPassword().length() > 16){
            log.error("UserServiceImpl.updatePassword error: the newPassword length not allowed which is {}", userUpdatePasswordDTO.getNewPassword());
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        if (userUpdatePasswordDTO.getOldPassword().length() < 6 || userUpdatePasswordDTO.getOldPassword().length() > 16){
            log.error("UserServiceImpl.updatePassword error: the oldPassword length not allowed which is {}", userUpdatePasswordDTO.getOldPassword());
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        if (userUpdatePasswordDTO.getConfirmPassword().length() < 6 || userUpdatePasswordDTO.getConfirmPassword().length() > 16){
            log.error("UserServiceImpl.updatePassword error: the confirmPassword length not allowed which is {}", userUpdatePasswordDTO.getConfirmPassword());
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        Long currentId = StpUtil.getLoginIdAsLong();
        User user = userMapper.selectById(currentId);
        if (!user.getPassword().equals(userUpdatePasswordDTO.getOldPassword())){
            log.error("UserServiceImpl.updatePassword error: the oldPassword is not correct which is {}", userUpdatePasswordDTO.getOldPassword());
            throw new BaseException(MessageConstant.OLD_PASSWORD_ERROR);
        }
        if (!userUpdatePasswordDTO.getNewPassword().equals(userUpdatePasswordDTO.getConfirmPassword())){
            log.error("UserServiceImpl.updatePassword error: the newPassword and confirmPassword is not same which is {}", userUpdatePasswordDTO.getNewPassword());
            throw new BaseException(MessageConstant.CONFIRM_PASSWORD_ERROR);
        }
        user.setPassword(userUpdatePasswordDTO.getNewPassword());
        user.setUpdateTime(LocalDateTime.now());
//        updateTimeById(currentId);
        userMapper.updateById(user);
        log.info("UserServiceImpl.updatePassword success: updatePassword success which is {}", userUpdatePasswordDTO.getNewPassword());
    }


    /**
     * 分页查询用户
     * @param userQuery
     * @return
     */
    public PageDTO<UserResponseDTO> selectByPage(UserQuery userQuery) {
        // 1.构建基础查询条件 默认是按创建时间降序排序，如果指定了sortBy和isAsc（必须先指定sortBy,isAsc才生效），则按指定的排序和顺序排序
        Page<User> page = userQuery.toMpPage("created_time", false);
        // 2.分页查询
        Page<User> p = lambdaQuery()
                .like(userQuery.getUsername()!=null,User::getUsername,userQuery.getUsername())
                .like(userQuery.getNickname()!=null,User::getNickname,userQuery.getNickname())
                .eq(userQuery.getUserRole()!=null,User::getUserRole,userQuery.getUserRole())
                .between(userQuery.getStartTime()!=null&&userQuery.getEndTime()!=null,User::getCreatedTime,userQuery.getStartTime(),userQuery.getEndTime())
                .between(userQuery.getStartTime()!=null&&userQuery.getEndTime()!=null,User::getUpdateTime,userQuery.getStartTime(),userQuery.getEndTime())
                .between(userQuery.getStartTime()!=null&&userQuery.getEndTime()!=null,User::getEditTime,userQuery.getStartTime(),userQuery.getEndTime())
                .page(page);
        // 3.封装VO结果
        log.info("UserServiceImpl.selectByPage success: selectByPage success which is {}", p);
        return PageDTO.of(p, UserResponseDTO.class);
    }

    /**
     * 获取用户某年份签到记录
     * @param userId 用户id
     * @param year 年份（为空表示当前年份）
     * @return 签到记录
     */
    public List<Integer> getUserSignInRecord(Long userId, Integer year) {
        if (year == null){
            LocalDate date = LocalDate.now();
            year = date.getYear();
        }
        String key = RedisConstant.getUserSignInRedisKey(year, userId);
        //查询Redis的BitMap
        RBitSet bitSet = redissonClient.getBitSet(key);
        //加载BitSet到内存中，避免后续读取时发送多次请求
        BitSet newBitSet = bitSet.asBitSet();
        //统计签到的日期
        ArrayList<Integer> list = new ArrayList<>();
        //从索引0开始查找下一个被设置为1的位
        int index = newBitSet.nextSetBit(0);
        while (index >= 0){
            list.add(index);
            //从下一位开始继续查找被设为1的位
            index = newBitSet.nextSetBit(index + 1);
        }
        log.info("UserServiceImpl.getUserSignInRecord success: getUserSignInRecord success which is {}", list);
        return list;
    }

    /**
     * 登录
     * @param loginAndRegisterDTO 用户登录信息
     */
    public User login(LoginAndRegisterRequestDTO loginAndRegisterDTO) {
        if (StrUtil.isBlank(loginAndRegisterDTO.getUsername())||StrUtil.isBlank(loginAndRegisterDTO.getPassword())){
            log.error("UserServiceImpl.login error: the username or password is empty which is {}", loginAndRegisterDTO);
            throw new BaseException(MessageConstant.ERROR_DATABASE);
        }
        //根据账号查询数据库的用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginAndRegisterDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);
        //若数据库无该账号，抛出异常
        if (dbUser == null){
            log.error("UserServiceImpl.login error: the username is not found which is {}", loginAndRegisterDTO.getUsername());
            throw new BaseException(MessageConstant.ACCOUNT_OR_PASSWORD_NOT_FOUND);
        }
        //若所输入密码与数据库密码不一致，抛出异常
        if (!loginAndRegisterDTO.getPassword().equals(dbUser.getPassword())){
            log.error("UserServiceImpl.login error: the password is not correct which is {}", loginAndRegisterDTO.getPassword());
            throw new BaseException(MessageConstant.ACCOUNT_OR_PASSWORD_NOT_FOUND);
        }
        return dbUser;
    }

}
