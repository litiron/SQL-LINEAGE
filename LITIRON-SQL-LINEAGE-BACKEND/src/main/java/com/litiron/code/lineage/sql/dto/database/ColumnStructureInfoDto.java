package com.litiron.code.lineage.sql.dto.database;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 李日红
 * @description: 列结构信息Dto
 * @date 2024/11/30 15:11
 */
@Setter
@Getter
public class ColumnStructureInfoDto {
    /*
     *列名
     */
    private String columnName;
    /*
     * 列注释
     */
    private String columnComment;
}
