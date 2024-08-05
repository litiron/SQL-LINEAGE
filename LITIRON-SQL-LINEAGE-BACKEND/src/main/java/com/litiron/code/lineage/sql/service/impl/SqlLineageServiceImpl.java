package com.litiron.code.lineage.sql.service.impl;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.litiron.code.lineage.sql.dao.SqlLineageNodeRepository;
import com.litiron.code.lineage.sql.dto.ParsedTableMeta;
import com.litiron.code.lineage.sql.dto.SqlTableJoinLineMeta;
import com.litiron.code.lineage.sql.service.SqlLineageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: Litiron
 * @create: 2024-06-08 10:02
 **/
@Service
public class SqlLineageServiceImpl implements SqlLineageService {

    private SqlLineageNodeRepository sqlLineageNodeRepository;

    @Override
    public ParsedTableMeta parseRelationTables(String sql) {
        return obtainAllTables(sql);
    }

    @Override
    public void parseSqlJoinRelation(String sql) {
        List<SQLStatement> sqlStatements = parsePgStatements(sql);
        Map<String, SqlTableJoinLineMeta> joinLineMetaMap = new HashMap<>(16);
        for (SQLStatement statement : sqlStatements) {
            PGSchemaStatVisitor pgSchemaStatVisitor = new PGSchemaStatVisitor();
            statement.accept(pgSchemaStatVisitor);
            Set<TableStat.Relationship> relationships = pgSchemaStatVisitor.getRelationships();
            for (TableStat.Relationship relationship : relationships) {
                if (joinLineMetaMap.containsKey(relationship.getLeft().getTable())) {
                    SqlTableJoinLineMeta sqlTableJoinLineMeta = joinLineMetaMap.get(relationship.getLeft().getTable());
                    sqlTableJoinLineMeta.setJoinSymbol(relationship.getOperator());
                    sqlTableJoinLineMeta.setDestField(relationship.getRight().getName());
                    sqlTableJoinLineMeta.setSourceField(relationship.getLeft().getName());
                }
            }
            Collection<TableStat.Column> columns = pgSchemaStatVisitor.getColumns();
        }
    }

    private List<SQLStatement> parsePgStatements(String sql) {
        return SQLUtils.parseStatements(sql, DbType.postgresql);
    }

    private ParsedTableMeta obtainAllTables(String sqlStatement) {
        ParsedTableMeta parsedTableMeta = new ParsedTableMeta();
        List<SQLStatement> sqlStatements = parsePgStatements(sqlStatement);
        List<String> sourceTableList = new ArrayList<>();
        List<String> destTableList = new ArrayList<>();
        for (SQLStatement statement : sqlStatements) {
            PGSchemaStatVisitor pgSchemaStatVisitor = new PGSchemaStatVisitor();
            statement.accept(pgSchemaStatVisitor);
            distinguishTableType(sourceTableList, destTableList, pgSchemaStatVisitor);
            Set<TableStat.Relationship> relationships = pgSchemaStatVisitor.getRelationships();
            Collection<TableStat.Column> columns = pgSchemaStatVisitor.getColumns();
        }
        parsedTableMeta.setDestTableList(destTableList);
        parsedTableMeta.setSourceTableList(sourceTableList);
        return parsedTableMeta;
    }

    private void distinguishTableType(List<String> selectTableList,
                                      List<String> insertTableList,
                                      PGSchemaStatVisitor pgSchemaStatVisitor) {
        Map<TableStat.Name, TableStat> allTables = pgSchemaStatVisitor.getTables();
        allTables.forEach((k, v) -> {
            if (v.getInsertCount() > 0) {
                insertTableList.add(k.getName());
            } else {
                selectTableList.add(k.getName());
            }
        });
    }

    @Autowired
    public void setSqlLineageNodeRepository(SqlLineageNodeRepository sqlLineageNodeRepository) {
        this.sqlLineageNodeRepository = sqlLineageNodeRepository;
    }
}
