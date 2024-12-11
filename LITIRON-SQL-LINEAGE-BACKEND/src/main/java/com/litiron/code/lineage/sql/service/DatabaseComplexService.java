package com.litiron.code.lineage.sql.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.litiron.code.lineage.sql.dto.database.DatabaseConnectionDto;
import com.litiron.code.lineage.sql.dto.database.QueryTableDetailsParamsDto;
import com.litiron.code.lineage.sql.dto.database.SchemaStructInfoDto;

import java.util.List;
import java.util.Map;

/**
 * @description: 数据库相关操作类
 * @author: 李日红
 * @create: 2024/11/30 13:57
 */
public interface DatabaseComplexService {
    /**
     * @description: 检索所有数据库连接信息
     * @return: java.util.List<DatabaseConnectionDto>
     * @author: 李日红
     * @create: 2024/12/1 14:18
     */
    List<DatabaseConnectionDto> retrieveDatabaseConnectionInfo();

    /**
     * @param id 连接信息key
     * @return java.util.List<com.litiron.code.lineage.sql.dto.database.SchemaStructInfoDto>
     * @description: 目前是每次新增连接都会存放在thread local的map集合中，key相同的话就直接从map中获取，不再读取数据库
     * @author 李日红
     * @since 2024/12/2 17:43
     */
    List<SchemaStructInfoDto> updateDatabaseConnection(String id) throws Exception;

    /**
     * @description: 获取表内容信息
     * @param: queryTableDetailsParamsDto 查询表内容参数
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<java.util.Map<java.lang.String,java.lang.Object>>
     * @author: 李日红
     * @create: 2024/12/7 13:35
     */
    IPage<Map<String, Object>> retrieveTableDetails(QueryTableDetailsParamsDto queryTableDetailsParamsDto);
}
