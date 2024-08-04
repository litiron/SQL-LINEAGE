package com.litiron.code.one.sql.dao;

import com.litiron.code.one.sql.entity.SqlLineageNodeEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @description: 结点dao层定义
 * @author: Litiron
 * @create: 2024-07-02 22:03
 **/
@Repository
public interface SqlLineageNodeRepository extends Neo4jRepository<SqlLineageNodeEntity, String> {
}
