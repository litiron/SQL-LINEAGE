package com.litiron.code.lineage.sql.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.litiron.code.lineage.sql.bo.database.QueryTableDetailsParamsBo;
import com.litiron.code.lineage.sql.common.Rest;
import com.litiron.code.lineage.sql.dto.database.DatabaseConnectionDto;
import com.litiron.code.lineage.sql.dto.database.QueryTableDetailsParamsDto;
import com.litiron.code.lineage.sql.dto.database.SchemaStructInfoDto;
import com.litiron.code.lineage.sql.service.DatabaseComplexService;
import com.litiron.code.lineage.sql.vo.database.DatabaseConnectionVo;
import com.litiron.code.lineage.sql.vo.database.SchemaStructInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 数据库相关控制层
 * @author: 李日红
 * @create: 2024/11/26 22:01
 */
@RequestMapping("/database")
@RestController
@Slf4j
public class DatabaseController {
    private DatabaseComplexService databaseComplexService;

    @GetMapping("/retrieve/connection")
    public Rest<List<DatabaseConnectionVo>> retrieveDatabaseConnectionInfo() {
        List<DatabaseConnectionDto> connectionDtoList = databaseComplexService.retrieveDatabaseConnectionInfo();
        List<DatabaseConnectionVo> connectionVos = BeanUtil.copyToList(connectionDtoList, DatabaseConnectionVo.class);
        return Rest.success(connectionVos);
    }

    @GetMapping("/connection/update")
    public Rest<?> updateDatabaseConnection(@RequestParam(value = "id") String id) {
        try {
            List<SchemaStructInfoDto> schemaStructInfoDtos = databaseComplexService.updateDatabaseConnection(id);
            List<SchemaStructInfoVo> schemaStructInfoVos = BeanUtil.copyToList(schemaStructInfoDtos, SchemaStructInfoVo.class);
            return Rest.success(schemaStructInfoVos);
        } catch (Exception e) {
            log.error("Update database connection error,id is {}", id, e);
        }
        return Rest.error("error");
    }

    @PostMapping("/retrieve/table/details")
    public Rest<?> getTableDetails(@RequestBody QueryTableDetailsParamsBo queryTableDetailsBo) {
        QueryTableDetailsParamsDto queryTableDetailsParamsDto = BeanUtil.copyProperties(queryTableDetailsBo, QueryTableDetailsParamsDto.class);
        IPage<Map<String, Object>> tableDetails = databaseComplexService.retrieveTableDetails(queryTableDetailsParamsDto);

        return Rest.success(tableDetails.getRecords(), tableDetails.getTotal());
    }

    @Autowired
    public void setDatabaseService(DatabaseComplexService databaseComplexService) {
        this.databaseComplexService = databaseComplexService;
    }

}
