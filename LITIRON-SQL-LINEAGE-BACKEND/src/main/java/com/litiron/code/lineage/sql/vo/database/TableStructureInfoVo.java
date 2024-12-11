package com.litiron.code.lineage.sql.vo.database;

import com.litiron.code.lineage.sql.dto.database.ColumnStructureInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 李日红
 * @description: 获取的表结构信息Vo
 * @create 2024/12/7 11:46
 */
@Setter
@Getter
public class TableStructureInfoVo {
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
