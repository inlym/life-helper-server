## 积分模块

### 模块信息

| 项目   | 值                                       |
|------|-----------------------------------------|
| 模块名称 | 积分模块                                    |
| 包名   | `com.inlym.lifehelper.membership.point` |
| 父级模块 | 会员                                      |

### 建表语句

```sql
create table if not exists point_balance
(
    id          int unsigned auto_increment comment '主键 ID'
        primary key,
    user_id     int unsigned                          not null comment '用户 ID',
    balance     bigint(255) default 0                 not null comment '当前积分余额',
    expense     bigint(255) default 0                 not null comment '积分支出总额',
    income      bigint(255) default 0                 not null comment '积分收入总额',
    create_time datetime    default (now())           not null comment '创建时间',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最近更新时间',
    constraint user_id_index
        unique (user_id)
)
    comment '积分余额表';
```
