package com.gj.ggusercenterback.model.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginUserVO implements Serializable {

    private static final long serialVersionUID = -2818081584569143264L;
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;


    /**
     * 用户角色：user/admin
     */
    private String userRole;




}
