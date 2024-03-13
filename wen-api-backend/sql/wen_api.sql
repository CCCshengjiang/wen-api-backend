create database wen_api;
use wen_api;

-- 接口信息表
create table if not exists interface_info
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '接口名称',
    `description` varchar(256) null comment '接口描述',
    `user_id` varchar(256) not null comment '创建人 ID',
    `url` varchar(512) not null comment '接口地址',
    `method` varchar(256) not null comment '请求类型',
    `request_header` text not null comment '请求头',
    `response_header` varchar(256) not null comment '响应头',
    `status` tinyint default 0 not null comment '接口状态',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_deleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息表';

# 创建用户表
create table if not exists user
(
    id            bigint auto_increment comment 'id'
        primary key,
    user_password varchar(512)                       null comment '密码',
    avatar_url    varchar(1024)                      null comment '头像',
    gender        tinyint                            null comment '性别',
    user_account  varchar(512)                       null comment '账号',
    user_profile  varchar(512)                       null comment '自我介绍',
    email         varchar(512)                       null comment '邮箱',
    user_status   int      default 0                 null comment '状态',
    tags          varchar(1024)                      null comment '用户标签 JSON',
    phone         varchar(128)                       null comment '电话',
    is_delete     tinyint  default 0                 null comment '是否删除',
    user_role     int      default 0                 not null comment '用户角色，0-普通用户，1-管理员',
    username      varchar(256)                       null comment '用户名',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '用户信息表';