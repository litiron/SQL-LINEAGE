package com.litiron.code.one.sql.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 某个table在整个sql或者是整个数据库中相关的信息(无向图)
 * @author: Litiron
 * @create: 2024-06-16 15:08
 **/
@Getter
@Setter
public class SqlTableNodeMeta {

    private String tableName;

    private String schema;

    /**
     * 关联条件，自身的话是为空
     */
    private List<SqlTableJoinLineMeta> joins = new ArrayList<>();

    private List<SqlTableNodeMeta> relationsTables;
}
