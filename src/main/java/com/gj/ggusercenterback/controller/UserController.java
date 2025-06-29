package com.gj.ggusercenterback.controller;

import com.gj.ggusercenterback.common.BaseResponse;
import com.gj.ggusercenterback.common.ResultUtils;
import com.gj.ggusercenterback.exception.ErrorCode;
import com.gj.ggusercenterback.exception.ThrowUtils;
import com.gj.ggusercenterback.model.dot.UserRegisterRequest;
import com.gj.ggusercenterback.service.UsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UsersService usersService;
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = usersService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }
}
