package com.litiron.code.one.sql.config;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: Litiron
 * @create: 2024-06-26 01:13
 **/
@Configuration
public class Neo4jCypherDslConfig {

    @Bean
    org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
        return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig()
                .withDialect(Dialect.NEO4J_5).build();
    }
}
