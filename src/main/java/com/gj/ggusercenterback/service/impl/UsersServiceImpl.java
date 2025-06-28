package com.gj.ggusercenterback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gj.ggusercenterback.model.entitle.Users;
import com.gj.ggusercenterback.service.UsersService;
import com.gj.ggusercenterback.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author 14908
* @description 针对表【users(用户)】的数据库操作Service实现
* @createDate 2025-06-28 17:16:35
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




