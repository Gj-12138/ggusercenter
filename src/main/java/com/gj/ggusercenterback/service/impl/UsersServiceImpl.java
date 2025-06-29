package com.gj.ggusercenterback.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gj.ggusercenterback.exception.BusinessException;
import com.gj.ggusercenterback.exception.ErrorCode;
import com.gj.ggusercenterback.model.entitle.Users;
import com.gj.ggusercenterback.model.enums.UserRoleEnum;
import com.gj.ggusercenterback.service.UsersService;
import com.gj.ggusercenterback.mapper.UsersMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{


    // 密码正则表达式（至少8位，包含大小写字母和数字）
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$";
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
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 2. 账号校验
        if (userAccount.length() < 1 || userAccount.length() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度需在1-10位之间");
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
    @Override
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "GUOJINZHANG";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }
}




