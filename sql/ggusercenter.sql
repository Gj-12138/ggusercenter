create database if not exists gguserbackend;
use gguserbackend;
-- 用户表
create table if not exists users(
    id bigint auto_increment comment 'id' primary key ,
    userAccount varchar(256) not null  comment '账号',
    userPassword varchar(256) not null comment '密码',
    userName varchar(256) null  comment '用户名称',
    userAvatar varchar(1024) null  comment '用户头像',
    userProfile varchar(1024) null comment '用户简介',
    userRole varchar(256) default 'user' not null comment '用户角色：user/admin',
    editTime      datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime    datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint      default 0                 not null comment '是否删除',
    UNIQUE KEY uk_userAccount (userAccount),
    INDEX idx_userName (userName)
) comment '用户' ;