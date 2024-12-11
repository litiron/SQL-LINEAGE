package com.litiron.code.lineage.sql.vo.database;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 李日红
 * @description: 列结构信息Vo
 * @create 2024/12/7 11:55
 */
@Setter
@Getter
public class ColumnStructureInfoVo {
    /*
     *列名
     */
    private String columnName;
    /*
     * 列注释
     */
    private String columnComment;
}
