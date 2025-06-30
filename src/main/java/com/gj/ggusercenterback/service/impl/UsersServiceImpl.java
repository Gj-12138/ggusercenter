package com.gj.ggusercenterback.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gj.ggusercenterback.exception.BusinessException;
import com.gj.ggusercenterback.exception.ErrorCode;
import com.gj.ggusercenterback.model.entitle.Users;
import com.gj.ggusercenterback.model.enums.UserRoleEnum;
import com.gj.ggusercenterback.model.vo.LoginUserVO;
import com.gj.ggusercenterback.service.UsersService;
import com.gj.ggusercenterback.mapper.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
* @author 14908
* @description 针对表【users(用户)】的数据库操作Service实现
* @createDate 2025-06-28 17:16:35
*/

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{
    // 密码正则表达式（至少8位，包含大小写字母和数字）
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$";

    /**
    * 用户登录状态键
    */
    private  static final String USER_LOGIN_STATE = "user_login_state";
    /**
     * 用户注册
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户的 ID
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

//        1.校验账号，密码，验证密码不能为空
//        2.账号不能小于一个字，不能大于20个字。
//        3.密码不能小于8个字，且要有英文大小写、数字、常见符号，不能有特殊符号。
//        4.验证两次密码是否相同
//        5.账号不能重复
//        6.加密密码。
//        7.将数据插入数据库中

        // 1. 基本校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号、密码和验证密码不能为空");
        }
        // 2. 账号校验
        if (userAccount.length() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度过长");
        }

        // 3. 密码校验
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能小于8位");
        }
        if (!Pattern.matches(PASSWORD_PATTERN, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,
                    "密码必须包含大小写字母和数字");
        }

        // 4. 确认密码校验
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不一致");
        }

        // 5. 账号查重
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已存在");
        }
        // 6. 密码加密
        String  encryptedPassword = getEncryptPassword(userPassword);
        // 7. 插入数据
        Users user = new Users();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setUserName("未命名");
        user.setUserRole(UserRoleEnum.USER.getValue());

        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }

        return user.getId();

    }

    // 密码加密盐值（增强安全性）
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "GUOJINZHANG";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }
    /**
     * 用户登录
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      HTTP 请求对象
     * @return 登录用户的信息
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
//        1.校验判断接收参数是否为空
//        2.判断账号格式是否正确（账号长度需在1-10位之间）
//        3.校验登录用户是否存在。
//        判断密码格式是否正确（密码长度不能小于8位，密码必须包含大小写字母和数字）
//        3.与数据库中的密码校验 ，是否正确
//        4.记录登录态，存在服务器上。
// 1. 参数基础校验
        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码不能为空");
        }

        // 2. 账号格式校验（1-10位字母数字组合）
        if (userAccount.length() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号过长");
        }

        // 3. 密码格式校验（至少8位，包含大小写字母和数字）
        if (!Pattern.matches(PASSWORD_PATTERN, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,
                    "密码需至少8位且包含大小写字母和数字");
        }

        // 4. 密码加密
        String  encryptedPassword = getEncryptPassword(userPassword);
        // 5. 查询用户是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptedPassword);
        Users user = this.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "账号不存在");
        }

        // 6. 记录登录态（使用Session）
        request.getSession().setAttribute(USER_LOGIN_STATE, user);

        // 7. 脱敏后返回用户信息（避免返回密码等敏感字段）
        return this.getLoginUserVO(user);
    }

    /**
     * 获取当前登录用户
     * 从 request 请求对象对‍应的 Session 中直接获取到之前保存的登录用户信息
     * 为了保证获取到的数据始终‍是最新的，先从 Session 中获取登录用户的 id，然后从数据库中查询最新的结果
     * @param request HTTP 请求对象
     * @return 当前登录用户
     */
    @Override
    public Users getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        Users currentUser = (Users) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接返回上述结果）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 获取脱敏后的登录用户信息
     * @param user 用户对象
     * @return 脱敏后的用户信息
     */
    @Override
    public LoginUserVO getLoginUserVO(Users user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }



}




