package com.inlym.lifehelper.common.base.aliyun.tablestore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 表格存储工具类测试
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/16
 * @since 1.3.0
 **/
@SpringBootTest
public class TableStoreUtilsTests {
    @Test
    public void getRandomIdTest() {
        String id = TableStoreUtils.getRandomId();

        System.out.println(id);

        // 字符串长度为 32
        Assertions.assertEquals(32, id.length());

        // 字符串全部是英文字母和数字
        Assertions.assertTrue(id.matches("^\\w+$"));

        // 再来一次生成的结果与前一次不同
        Assertions.assertNotEquals(TableStoreUtils.getRandomId(), id);
    }

    @Test
    public void getNonClusteredIdTest() {
        int id = (int) Math.floor(Math.random() * 1000000);
        String result = TableStoreUtils.getHashedId(id);
        String[] list = result.split("_");

        // 第1部分为8位英文字母和数字
        Assertions.assertTrue(list[0].matches("^\\w+$"));
        Assertions.assertEquals(8, list[0].length());

        // 第2部分转化为数字后与原 id 相等
        Assertions.assertEquals(id, Integer.valueOf(list[1]));
    }
}
