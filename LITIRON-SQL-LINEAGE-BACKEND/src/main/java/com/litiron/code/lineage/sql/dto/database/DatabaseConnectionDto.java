package com.litiron.code.lineage.sql.dto.database;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 李日红
 * @description:
 * @create 2024/12/1 14:21
 */
@Setter
@Getter
public class DatabaseConnectionDto {
    private Integer id;
    private String connectionName;
}
