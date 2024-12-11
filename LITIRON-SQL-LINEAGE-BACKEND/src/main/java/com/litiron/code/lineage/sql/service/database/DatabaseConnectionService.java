package com.litiron.code.lineage.sql.service.database;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.litiron.code.lineage.sql.entity.database.DatabaseConnectionEntity;

import java.util.List;
import java.util.Map;

/**
 * @description: 数据库连接Service
 * @author: 李日红
 * @create: 2024/12/1 18:05
 */

public interface DatabaseConnectionService {
    /**
     * @description: 检索所有数据库连接信息
     * @return: java.util.List<DatabaseConnectionDto>
     * @author: 李日红
     * @create: 2024/12/1 14:18
     */
    List<DatabaseConnectionEntity> getDatabaseConnectionInfo();

    /**
     * @description: 根据id获取连接信息
     * @param: id
     * @return: com.litiron.code.lineage.sql.dto.database.DatabaseConnectionDto
     * @author: 李日红
     * @create: 2024/12/1 18:04
     */
    DatabaseConnectionEntity getDatabaseConnectionInfoById(String id);

    /**
     * @description: 获取表内容信息
     * @param: page 分页参数
     * @param: tableName 表名
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<java.util.Map < java.lang.String, java.lang.Object>>
     * @author: 李日红
     * @create: 2024/12/7 13:35
     */
    IPage<Map<String, Object>> retrieveTableDetails(IPage<Map<String, Object>> page, String tableName);
}
