package com.datasources;

import java.util.HashMap;

import com.zaxxer.hikari.HikariDataSource;

public class DataSourceMap {

	HashMap<String, HikariDataSource> dataSourceMap = new HashMap<String, HikariDataSource>();

	public void putDataSource(String cluster_id, HikariDataSource dataSource) {
		dataSourceMap.put(cluster_id, dataSource);
	}

	public HikariDataSource getDataSource(String cluster_id) {
		return dataSourceMap.get(cluster_id);
	}
}
