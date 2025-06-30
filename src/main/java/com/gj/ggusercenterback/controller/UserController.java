package com.gj.ggusercenterback.controller;

import com.gj.ggusercenterback.common.BaseResponse;
import com.gj.ggusercenterback.common.ResultUtils;
import com.gj.ggusercenterback.exception.ErrorCode;
import com.gj.ggusercenterback.exception.ThrowUtils;
import com.gj.ggusercenterback.model.dot.UserLoginRequest;
import com.gj.ggusercenterback.model.dot.UserRegisterRequest;
import com.gj.ggusercenterback.model.entitle.Users;
import com.gj.ggusercenterback.model.vo.LoginUserVO;
import com.gj.ggusercenterback.service.UsersService;
import org.apache.catalina.connector.Request;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR,"请求参数不能为空");
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = usersService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }
    @PostMapping("/login")
    public  BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR,"请求参数不能为空");
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = usersService.userLogin(userAccount, userPassword,request);
        return ResultUtils.success(loginUserVO);
    }
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        Users user = usersService.getLoginUser(request);
        return ResultUtils.success(usersService.getLoginUserVO(user));
    }

}
