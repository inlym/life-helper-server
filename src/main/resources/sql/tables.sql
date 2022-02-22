SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `weather_place`;
CREATE TABLE `weather_place`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `user_id`     int(11) unsigned NOT NULL COMMENT '所属用户 ID',
    `name`        varchar(100)     NOT NULL DEFAULT '' COMMENT '位置名称',
    `address`     varchar(100)     NOT NULL DEFAULT '' COMMENT '详细地址',
    `longitude`   double           NOT NULL COMMENT '经度',
    `latitude`    double           NOT NULL COMMENT '纬度',
    `province`    varchar(32)      NOT NULL DEFAULT '' COMMENT '坐标点所在省',
    `city`        varchar(32)      NOT NULL DEFAULT '' COMMENT '坐标点所在市',
    `district`    varchar(32)      NOT NULL DEFAULT '' COMMENT '坐标点所在区',
    `delete_time` datetime                  DEFAULT NULL COMMENT '删除时间',
    `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='天气地点表';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`            int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `openid`        varchar(32)      NOT NULL DEFAULT '' COMMENT '微信小程序用户唯一标识',
    `phone`         char(11)                  DEFAULT NULL COMMENT '绑定的账户手机号',
    `nick_name`     varchar(100)     NOT NULL DEFAULT '' COMMENT '用户昵称',
    `avatar`        varchar(100)     NOT NULL DEFAULT '' COMMENT '用户头像图片路径',
    `register_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `create_time`   datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_openid` (`openid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';

SET FOREIGN_KEY_CHECKS = 1;
