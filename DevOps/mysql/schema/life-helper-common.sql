-- ----------------------------------------------------------------------------------------------------------------
-- 请求日志表
-- 对应实体: [com.weutil.common.entity.RequestLog]
-- 创建时间: 2024/12/12
-- ----------------------------------------------------------------------------------------------------------------

create table `request_log`
(
    /* 下方是通用字段 */
    `id`               bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`      datetime        not null default current_timestamp comment '创建时间',
    `update_time`      datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time`      datetime                 default null comment '删除时间（逻辑删除标志）',
    `create_client_ip` varchar(15)     not null default '' comment '创建时的客户端 IP 地址',
    `update_client_ip` varchar(15)     not null default '' comment '最后一次更新时的客户端 IP 地址',
    `update_count`     bigint unsigned not null default 0 comment '更新次数',

    /* 下方为用户相关类数据表通用字段 */
    `user_id`          bigint unsigned not null default 0 comment '所属的用户 ID',

    /* 下方为业务字段 */
    `method`           varchar(10)     not null default '' comment '请求方法',
    `path`             varchar(100)    not null default '' comment '请求路径',
    `querystring`      varchar(300)    not null default '' comment '请求参数',
    `status`           int             not null default 0 comment '响应状态码',
    `request_body`     varchar(10000)  not null default '' comment '请求数据',

    `start_time`       datetime        not null comment '请求开始时间',
    `end_time`         datetime        not null comment '请求结束时间',
    `duration`         int unsigned    not null default 0 comment '请求时长（单位：毫秒）',

    `trace_id`         varchar(50)     not null default '' comment '请求 ID（追踪 ID）',
    `client_ip`        varchar(50)     not null default '' comment '客户端 IP 地址',
    `access_token`     varchar(50)     not null default '' comment '访问凭证',
    `client_type`      int             not null default 0 comment '客户端类型（枚举值）',
    `client_id`        varchar(50)     not null default '' comment '客户端 ID',
    `client_version`   varchar(10)     not null default '' comment '客户端版本号',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='请求日志表';
