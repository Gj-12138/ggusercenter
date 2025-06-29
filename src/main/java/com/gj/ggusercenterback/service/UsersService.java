package com.gj.ggusercenterback.service;

import com.gj.ggusercenterback.model.entitle.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 14908
* @description 针对表【users(用户)】的数据库操作Service
* @createDate 2025-06-28 17:16:35
*/
public interface UsersService extends IService<Users> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    String getEncryptPassword(String userPassword);
}
