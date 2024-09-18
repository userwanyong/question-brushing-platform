package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.questionbrushingplatform.pojo.dto.LoginAndRegisterDTO;
import com.questionbrushingplatform.pojo.dto.PageDTO;
import com.questionbrushingplatform.pojo.dto.UserAddDTO;
import com.questionbrushingplatform.pojo.dto.UserUpdatePasswordDTO;
import com.questionbrushingplatform.entity.User;
import com.questionbrushingplatform.pojo.query.UserQuery;
import com.questionbrushingplatform.pojo.vo.UserVO;

import java.util.List;


/**
 * @author 永
 */
public interface UserService extends IService<User> {
    /**
     * 新增用户
     * @param userAddDTO
     */
    void add(UserAddDTO userAddDTO);

    /**
     * 修改密码
     * @param userUpdatePasswordDTO
     */
    void updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO);

    /**
     * 通用更新时间
     * @param id
     */
//    void updateTimeById(Long id);

    User getUserById(Long id);

    /**
     * 分页查询用户
     * @param userQuery
     * @return
     */
    PageDTO<UserVO> selectByPage(UserQuery userQuery);

    /**
     * 登录
     * @param loginAndRegisterDTO
     */
    User login(LoginAndRegisterDTO loginAndRegisterDTO);

//    /**
//     * 判断是否为管理员
//     */
//    void isAdmin();

    /**
     * 添加用户签到记录
     * @param userId 用户id
     * @return 当前用户是否已签到成功
     */
    boolean addUserSignIn(Long userId);


    /**
     * 获取用户某年份签到记录
     * @param userId 用户id
     * @param year 年份（为空表示当前年份）
     * @return 签到记录
     */
    List<Integer> getUserSignInRecord(Long userId, Integer year);
}
