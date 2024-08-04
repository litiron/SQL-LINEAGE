package com.litiron.code.one.sql.controller;

import com.litiron.code.common.Rest;
import com.litiron.code.one.sql.bo.ParseRelationParamsBo;
import com.litiron.code.one.sql.dto.ParsedTableMeta;
import com.litiron.code.one.sql.service.SqlLineageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: sql解析控制器
 * @author: Litiron
 * @create: 2024-06-08 08:52
 **/
@RequestMapping("/sql")
@RestController
public class SqlParserController {

    private SqlLineageService sqlLineageService;

    @PostMapping("/parse/relation/table")
    public Rest<ParsedTableMeta> parseRelationTables(@RequestBody ParseRelationParamsBo parseRelationParamsBo) {
        ParsedTableMeta parsedTableMeta = sqlLineageService.parseRelationTables(parseRelationParamsBo.getSql());
        return Rest.success(parsedTableMeta);
    }


    @Autowired
    public void setSqlLineageService(SqlLineageService sqlLineageService) {
        this.sqlLineageService = sqlLineageService;
    }
}
