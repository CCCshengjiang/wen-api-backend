create table avatar_url
(
    id          bigint auto_increment comment '主键'
        primary key,
    data_info   varchar(256)                       not null comment '精选头像',
    status      tinyint  default 0                 not null comment '接口状态',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted  tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '精选头像表';

create table love_words
(
    id          bigint auto_increment comment '主键'
        primary key,
    data_info   varchar(256)                       not null comment '情话',
    status      tinyint  default 0                 not null comment '接口状态',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted  tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '情话表';

create table poison_chicken_soup
(
    id          bigint auto_increment comment '主键'
        primary key,
    data_info   varchar(256)                       not null comment '毒鸡汤',
    status      tinyint  default 0                 not null comment '接口状态',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted  tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '毒鸡汤表';