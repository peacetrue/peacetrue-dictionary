drop table if exists dictionary_type;
create table dictionary_type
(
    id            bigint primary key auto_increment,
    code          varchar(32)  not null comment '编码',
    name          varchar(32)  not null comment '名称',
    remark        varchar(255) not null comment '备注',
    creator_id    bigint       not null comment '创建者主键',
    created_time  datetime     not null comment '创建时间',
    modifier_id   bigint       not null comment '修改者主键',
    modified_time datetime     not null comment '最近修改时间'
);

COMMENT ON TABLE dictionary_type IS '字典类型';
COMMENT ON COLUMN dictionary_type.id IS '主键';

create unique index dictionary_type_code
    on dictionary_type (code);

drop table if exists dictionary_value;
create table dictionary_value
(
    id                   bigint primary key auto_increment,
    dictionary_type_id   bigint       not null comment '字典类型. 主键',
    dictionary_type_code varchar(32)  not null comment '字典类型. 冗余编码方便查询',
    code                 varchar(32)  not null comment '编码',
    name                 varchar(255) not null comment '名称',
    serial_number        smallint     not null comment '序号',
    remark               varchar(255) not null comment '备注',
    creator_id           bigint       not null comment '创建者主键',
    created_time         datetime     not null comment '创建时间',
    modifier_id          bigint       not null comment '修改者主键',
    modified_time        datetime     not null comment '最近修改时间'
);


COMMENT ON TABLE dictionary_value IS '字典项值';
COMMENT ON COLUMN dictionary_value.id IS '主键';

create unique index dictionary_value_code
    on dictionary_value (dictionary_type_id, code);
