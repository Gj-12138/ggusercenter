package com.gj.ggusercenterback.service;

import com.gj.ggusercenterback.model.entitle.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gj.ggusercenterback.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

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
    /**
     * 获取加密后的密码
     *
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);
    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);
    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    Users getLoginUser(HttpServletRequest request);
    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(Users user);

}
