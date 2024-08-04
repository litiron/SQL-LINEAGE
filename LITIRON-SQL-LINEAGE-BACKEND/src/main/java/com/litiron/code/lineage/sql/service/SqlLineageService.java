package com.litiron.code.one.sql.service;

import com.litiron.code.one.sql.dto.ParsedTableMeta;

/**
 * @description: sql 血缘关系相关服务
 * @author: Litiron
 * @create: 2024-06-08 10:01
 **/
public interface SqlLineageService {

    /**
     * @param sql: sql语句
     * @Description: 解析sql中涉及的表信息
     * @Author: Litiron
     * @Date: 2024/6/15 19:32
     * @return: com.litiron.code.one.sql.dto.ParsedTableMeta
     **/
    ParsedTableMeta parseRelationTables(String sql);


    /**
     * @param sql: sql语句
     * @Description: 解析sql中关联关系
     * @Author: Litiron
     * @Date: 2024/6/16 15:30
     * @return: void
     **/
    void parseSqlJoinRelation(String sql);

}
