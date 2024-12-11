package com.litiron.code.lineage.sql.service.database.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.litiron.code.lineage.sql.dao.DatabaseConnectionRepository;
import com.litiron.code.lineage.sql.dao.DatabaseDynamicRepository;
import com.litiron.code.lineage.sql.entity.database.DatabaseConnectionEntity;
import com.litiron.code.lineage.sql.service.database.DatabaseConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李日红
 * @description: 数据库连接Service实现类
 * @create 2024/12/1 18:06
 */
@Service
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {
    private DatabaseConnectionRepository databaseConnectionRepository;
    private DatabaseDynamicRepository databaseDynamicRepository;

    @Override
    public List<DatabaseConnectionEntity> getDatabaseConnectionInfo() {
        Page<DatabaseConnectionEntity> databaseConnectionEntityPage = databaseConnectionRepository.selectPage(new Page<>(1, 10), new LambdaQueryWrapper<>());

        return databaseConnectionRepository.selectList(new QueryWrapper<>());
    }

    @Override
    public DatabaseConnectionEntity getDatabaseConnectionInfoById(String id) {
        return databaseConnectionRepository.selectById(id);
    }

    @Override
    public IPage<Map<String, Object>> retrieveTableDetails(IPage<Map<String, Object>> page, String tableName) {
        return databaseDynamicRepository.retrieveTableDetails(page, tableName);
    }

    @Autowired
    public void setDatabaseConnectionRepository(DatabaseConnectionRepository databaseConnectionRepository) {
        this.databaseConnectionRepository = databaseConnectionRepository;
    }

    @Autowired
    public void setDatabaseDynamicRepository(DatabaseDynamicRepository databaseDynamicRepository) {
        this.databaseDynamicRepository = databaseDynamicRepository;
    }

}
