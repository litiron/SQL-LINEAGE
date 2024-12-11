package com.litiron.code.lineage.sql.bo.database;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 李日红
 * @description: 查询表内容参数Bo
 * @create 2024/12/8 18:32
 */
@Setter
@Getter
public class QueryTableDetailsParamsBo {
    private Integer pageSize;
    private Integer pageNumber;
    /*
     *要查询的表名（模式名.表名）
     */
    private String tableName;
    private String connectionId;
}
