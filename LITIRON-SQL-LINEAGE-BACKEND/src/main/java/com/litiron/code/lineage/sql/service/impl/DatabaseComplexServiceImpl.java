package com.litiron.code.lineage.sql.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.litiron.code.lineage.sql.constants.DatabaseConnectionConstant;
import com.litiron.code.lineage.sql.dto.database.*;
import com.litiron.code.lineage.sql.entity.database.DatabaseConnectionEntity;
import com.litiron.code.lineage.sql.holder.DBContextHolder;
import com.litiron.code.lineage.sql.holder.DynamicDataSource;
import com.litiron.code.lineage.sql.service.DatabaseComplexService;
import com.litiron.code.lineage.sql.service.database.DatabaseConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 数据库service操作实现类
 * @author: 李日红
 * @create: 2024/11/30 13:59
 */
@Service
@Slf4j
public class DatabaseComplexServiceImpl implements DatabaseComplexService {
    private DatabaseConnectionService databaseConnectionService;
    private DynamicDataSource dynamicDataSource;

    @Override
    public List<DatabaseConnectionDto> retrieveDatabaseConnectionInfo() {
        List<DatabaseConnectionEntity> databaseConnectionEntities = databaseConnectionService.getDatabaseConnectionInfo();

        return BeanUtil.copyToList(databaseConnectionEntities, DatabaseConnectionDto.class);
    }


    @Override
    public List<SchemaStructInfoDto> updateDatabaseConnection(String id) {
        DatabaseConnectionEntity testConnectionEntity = databaseConnectionService.getDatabaseConnectionInfoById(id);
        DatabaseMetaData metaData = dynamicDataSource.createDataSourceWithCheck(testConnectionEntity);
        List<SchemaStructInfoDto> schemaStructInfos = retrieveSchemaStructInfo(metaData, testConnectionEntity.getType());
        DBContextHolder.setDataSource(id);
        DBContextHolder.clearDataSource();
        return schemaStructInfos;
    }

    @Override
    public IPage<Map<String, Object>> retrieveTableDetails(QueryTableDetailsParamsDto queryTableDetailsParamsDto) {
        DBContextHolder.setDataSource(queryTableDetailsParamsDto.getConnectionId());
        IPage<Map<String, Object>> page = new Page<>(queryTableDetailsParamsDto.getPageNumber(), queryTableDetailsParamsDto.getPageSize());
        IPage<Map<String, Object>> tableDetails = databaseConnectionService.retrieveTableDetails(page, queryTableDetailsParamsDto.getTableName());
        DBContextHolder.clearDataSource();
        return tableDetails;
    }

    private List<SchemaStructInfoDto> retrieveSchemaStructInfo(DatabaseMetaData metaData, String type) {
        List<SchemaStructInfoDto> schemaStructInfos = new ArrayList<>();
        try {
            if (type.equals(DatabaseConnectionConstant.CONNECTION_TYPE_MYSQL)) {
                ResultSet catalogRet = metaData.getCatalogs();
                schemaStructInfos = buildSchemaInfo(catalogRet, metaData, type);
            } else if (type.equals(DatabaseConnectionConstant.CONNECTION_TYPE_PGSQL)) {
                ResultSet schemaRet = metaData.getSchemas();
                schemaStructInfos = buildSchemaInfo(schemaRet, metaData, type);
            }
        } catch (Exception e) {
            log.error("RetrieveSchemaStructInfo is error");
        }
        return schemaStructInfos;
    }

    private List<SchemaStructInfoDto> buildSchemaInfo(ResultSet schemaRet, DatabaseMetaData metaData, String type) {
        List<SchemaStructInfoDto> schemaStructInfos = new ArrayList<>();
        try {
            while (schemaRet.next()) {
                String schemaName = "";
                if (type.equals(DatabaseConnectionConstant.CONNECTION_TYPE_MYSQL)) {
                    schemaName = schemaRet.getString("TABLE_CAT");

                } else if (type.equals(DatabaseConnectionConstant.CONNECTION_TYPE_PGSQL)) {
                    schemaName = schemaRet.getString("TABLE_SCHEM");
                }
                SchemaStructInfoDto schemaDto = new SchemaStructInfoDto();
                schemaDto.setSchemaName(schemaName);
                List<TableStructureInfoDto> tableStructureInfoDtoList = buildTableInfo(schemaName, metaData, type);
                schemaDto.setTableStructureInfoDtoList(tableStructureInfoDtoList);
                schemaStructInfos.add(schemaDto);
            }
        } catch (Exception e) {
            log.error("BuildSchemaInfo is error", e);
        }
        return schemaStructInfos;
    }

    private List<TableStructureInfoDto> buildTableInfo(String schemaName, DatabaseMetaData metaData, String type) {
        List<TableStructureInfoDto> tableStructureInfoDtoList = new ArrayList<>();
        try {
            ResultSet tableRet = null;
            if (type.equals(DatabaseConnectionConstant.CONNECTION_TYPE_MYSQL)) {
                tableRet = metaData.getTables(schemaName, "%", "%",
                        new String[]{"TABLE"});
            } else if (type.equals(DatabaseConnectionConstant.CONNECTION_TYPE_PGSQL)) {
                tableRet = metaData.getTables(null, schemaName, "%",
                        new String[]{"TABLE"});
            }

            while (tableRet.next()) {
                TableStructureInfoDto tableDto = new TableStructureInfoDto();
                String tableName = tableRet.getString("TABLE_NAME");
                String tableComment = tableRet.getString("REMARKS");
                tableDto.setTableName(tableName);
                tableDto.setTableComment(tableComment);
                List<ColumnStructureInfoDto> columnStructureInfoDtoList = buildColumnInfo(schemaName, tableName, metaData);
                tableDto.setColumnStructureInfoDtoList(columnStructureInfoDtoList);
                tableStructureInfoDtoList.add(tableDto);
            }
        } catch (Exception e) {
            log.error("BuildTableInfo is error", e);
        }
        return tableStructureInfoDtoList;
    }

    private List<ColumnStructureInfoDto> buildColumnInfo(String schemaName, String tableName, DatabaseMetaData metaData) {
        List<ColumnStructureInfoDto> columnStructureInfoDtoList = new ArrayList<>();
        try {
            ResultSet columnRet = metaData.getColumns(schemaName, "%", tableName, "%");
            while (columnRet.next()) {
                ColumnStructureInfoDto columnStructureInfoDto = new ColumnStructureInfoDto();
                String columnName = columnRet.getString("COLUMN_NAME");
                String columnComment = columnRet.getString("REMARKS");
                columnStructureInfoDto.setColumnName(columnName);
                columnStructureInfoDto.setColumnComment(columnComment);
                columnStructureInfoDtoList.add(columnStructureInfoDto);
            }
        } catch (Exception e) {
            log.error("BuildColumnInfo is error", e);
        }
        return columnStructureInfoDtoList;
    }


    @Autowired
    public void setDatabaseConnectionService(DatabaseConnectionService databaseConnectionService) {
        this.databaseConnectionService = databaseConnectionService;
    }

    @Autowired
    public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }
}
