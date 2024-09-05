package com.questionbrushingplatform.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.questionbrushingplatform.pojo.entity.User;
import com.questionbrushingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *自定义权限加载接口实现类
 */
@Component //保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private UserService userService;

    /**
     *返回一个账号所拥有的权限码集合
     *loginId：账号id，即你在调用 StpUtil.login(id) 时写入的标识值。
     *loginType：账号体系标识，此处可以暂时忽略，在 [多账户认证]章节下会对这个概念做详细的解释
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<>();
//        list.add("101");
//        list.add("user.add");
//        list.add("user.update");
//        list.add("user.get");
//        // list.add("user.delete");
//        list.add("art.*");
        return list;
    }
    /**
     *返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        User user = userService.getById((Serializable) loginId);
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<>();
        list.add(user.getUserRole());
        return list;
    }
}