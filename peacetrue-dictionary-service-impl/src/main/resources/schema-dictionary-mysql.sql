drop table if exists dictionary_type;
create table dictionary_type
(
    id            bigint unsigned primary key auto_increment comment '主键',
    code          varchar(32)     not null comment '编码',
    name          varchar(32)     not null comment '名称',
    remark        varchar(255)    not null default '' comment '备注',
    creator_id    bigint unsigned not null comment '创建者主键',
    created_time  datetime        not null comment '创建时间',
    modifier_id   bigint unsigned not null comment '修改者主键',
    modified_time datetime        not null comment '最近修改时间'
) COMMENT '字典类型';

create unique index dictionary_type_code on dictionary_type (code);
create index dictionary_type_name on dictionary_type (name);

drop table if exists dictionary_value;
create table dictionary_value
(
    id                   bigint unsigned primary key auto_increment comment '主键',
    dictionary_type_id   bigint unsigned   not null comment '字典类型. 主键',
    dictionary_type_code varchar(32)       not null comment '字典类型. 冗余字段',
    code                 varchar(32)       not null comment '编码',
    name                 varchar(255)      not null comment '名称',
    serial_number        smallint unsigned not null comment '序号',
    remark               varchar(255)      not null default '' comment '备注',
    creator_id           bigint unsigned   not null comment '创建者主键',
    created_time         datetime          not null comment '创建时间',
    modifier_id          bigint unsigned   not null comment '修改者主键',
    modified_time        datetime          not null comment '最近修改时间'
) comment '字典项值';

create unique index dictionary_value_code on dictionary_value (dictionary_type_id, code);
