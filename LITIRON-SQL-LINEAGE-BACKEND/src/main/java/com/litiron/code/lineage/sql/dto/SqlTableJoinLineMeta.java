package com.litiron.code.lineage.sql.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 表之间的关联关系
 * @author: Litiron
 * @create: 2024-06-16 15:24
 **/
@Getter
@Setter
public class SqlTableJoinLineMeta {

    private String sourceField;

    private String destField;

    private String joinSymbol;
}
