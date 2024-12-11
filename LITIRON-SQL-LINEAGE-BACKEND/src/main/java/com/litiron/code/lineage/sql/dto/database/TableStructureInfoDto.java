package com.litiron.code.lineage.sql.dto.database;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 李日红
 * @description: 获取的表结构信息Dto
 * @date 2024/11/30 14:46
 */
@Getter
@Setter
public class TableStructureInfoDto {
    /*
     *表名
     */
    private String tableName;
    /*
     * 表注释
     */
    private String tableComment;
    /*
     * 列结构
     */
    List<ColumnStructureInfoDto> columnStructureInfoDtoList;

}
