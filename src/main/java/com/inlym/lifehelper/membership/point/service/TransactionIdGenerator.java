package com.inlym.lifehelper.membership.point.service;

import com.inlym.lifehelper.membership.point.entity.PointTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 订单号生成器
 * <p>
 * <h2>主要用途
 * <p>用于生成交易订单号。
 * <p>
 * <h2>规则
 * <p>（1）第1~12位，创建时间，去掉 `20` 开头的“年月日时分秒”，示例 `230908001435`
 * <p>（2）第13位，交易渠道，示例 `1`
 * <p>（3）第14~17位，交易类型，示例 `0101`
 * <p>（4）第18~19位，用户 ID 的最后2位，示例 `22`
 * <p>（5）第20~22位，随机数，示例 `123`
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/6
 * @since 2.0.2
 **/
@Service
@Slf4j
public class TransactionIdGenerator {
    /**
     * 生成新的订单号
     *
     * @param transaction 订单号生成种子
     *
     * @date 2023/9/6
     * @since 2.0.2
     */
    public String generate(PointTransaction transaction) {
        String s1 = formatCreateTime(transaction.getCreateTime());
        String s2 = String.valueOf(transaction
                                       .getChannel()
                                       .getCode());
        String s3 = String.format("%04d", transaction
            .getType()
            .getCode());
        String s4 = String.format("%02d", transaction.getUserId() % 100);
        String s5 = String.format("%02d", new Random().nextInt(999));

        log.trace("生成交易订单号 - {} {} {} {} {} ", s1, s2, s3, s4, s5);
        return s1 + s2 + s3 + s4 + s5;
    }

    /**
     * 格式化创建时间
     * <p>
     * <h2>说明
     * <p>生成去掉 `20` 开头的“年月日时分秒”，示例 `230908001435`
     *
     * @param dt 创建时间
     *
     * @date 2023/9/6
     * @since 2.0.2
     */
    private String formatCreateTime(LocalDateTime dt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return formatter.format(dt);
    }
}
