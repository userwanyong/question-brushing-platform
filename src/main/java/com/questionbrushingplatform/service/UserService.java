package com.questionbrushingplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.questionbrushingplatform.pojo.dto.LoginAndRegisterDTO;
import com.questionbrushingplatform.pojo.dto.PageDTO;
import com.questionbrushingplatform.pojo.dto.UserAddDTO;
import com.questionbrushingplatform.pojo.dto.UserUpdatePasswordDTO;
import com.questionbrushingplatform.pojo.entity.User;
import com.questionbrushingplatform.pojo.query.UserQuery;
import com.questionbrushingplatform.pojo.vo.UserVO;


public interface UserService extends IService<User> {
    /**
     * 新增用户
     * @param userAddDTO
     */
    void add(UserAddDTO userAddDTO);


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

    /**
     * 通用更新时间
     * @param id
     */
    void updateTimeById(Long id);

    /**
     * 判断是否为管理员
     */
    void isAdmin();

    /**
     * 修改密码
     * @param userUpdatePasswordDTO
     */
    void updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO);
}
