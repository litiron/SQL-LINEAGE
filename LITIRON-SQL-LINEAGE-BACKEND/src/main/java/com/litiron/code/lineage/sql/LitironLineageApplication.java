package com.litiron.code.lineage.sql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 启动类
 * @author: Litiron
 * @create: 2024-08-04 14:55
 **/
@SpringBootApplication
@MapperScan("com.litiron.code.lineage.sql.dao")
public class LitironLineageApplication {
    public static void main(String[] args) {
        SpringApplication.run(LitironLineageApplication.class);
    }
}
