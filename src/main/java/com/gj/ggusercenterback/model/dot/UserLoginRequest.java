package com.gj.ggusercenterback.model.dot;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = 8985024204462406263L;
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;


}
