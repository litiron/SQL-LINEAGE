package com.litiron.code.lineage.sql.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.litiron.code.lineage.sql.holder.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李日红
 * @description: 配置默认数据源、配置Druid数据库连接池、配置sql工厂加载mybatis的文件，扫描实体等
 * @create 2024/12/2 21:33
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DruidDBConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    // 连接池连接信息
    @Value("${spring.datasource.druid.initial-size}")
    private int initialSize;
    @Value("${spring.datasource.druid.min-idle}")
    private int minIdle;
    @Value("${spring.datasource.druid.max-active}")
    private int maxActive;
    @Value("${spring.datasource.druid.max-wait}")
    private int maxWait;

    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        // 基础连接信息
        dataSource.setUrl(this.dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        // 连接池连接信息
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setPoolPreparedStatements(true); // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        //  datasource.setConnectionProperties("oracle.net.CONNECT_TIMEOUT=6000;oracle.jdbc.ReadTimeout=60000");//对于耗时长的查询sql，会受限于ReadTimeout的控制，单位毫秒
        dataSource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");// 对于耗时长的查询sql，会受限于ReadTimeout的控制，单位毫秒
        dataSource.setTestOnBorrow(true); // 申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
        dataSource.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        String validationQuery = "select 1";
        dataSource.setValidationQuery(validationQuery); // 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
        dataSource.setFilters("stat,wall");// 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
        dataSource.setTimeBetweenEvictionRunsMillis(60000); // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(180000); // 配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
        dataSource.setKeepAlive(true); // 打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select * from dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断
        dataSource.setRemoveAbandoned(true); // 是否移除泄露的连接/超过时间限制是否回收。
        dataSource.setRemoveAbandonedTimeout(3600); // 泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里配置为1小时
        dataSource.setLogAbandoned(true); // 移除泄露连接发生是是否记录日志
        return dataSource;
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource() throws SQLException {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDebug(false);
        // 配置缺省的数据源
        // 默认数据源配置 DefaultTargetDataSource
        dynamicDataSource.setDefaultTargetDataSource(dataSource());
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // 额外数据源配置 TargetDataSources
        targetDataSources.put("mainDataSource", dataSource());
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }
}
