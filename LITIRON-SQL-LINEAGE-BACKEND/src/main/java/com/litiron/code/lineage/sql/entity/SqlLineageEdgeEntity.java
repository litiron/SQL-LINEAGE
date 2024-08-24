package com.litiron.code.lineage.sql.entity;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @description: 结点连接线关系
 * @author: Litiron
 * @create: 2024-07-02 22:01
 **/
@Node(labels = "表连接关系")
public class SqlLineageEdgeEntity {

    @RelationshipId
    private String id;


    private SqlLineageNodeEntity from;

    @TargetNode
    private SqlLineageNodeEntity to;

    private String relation;
}
