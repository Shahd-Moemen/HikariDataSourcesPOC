package com.datasources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceConfig {

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	public DataSourceConfig(String url) {
		config.setJdbcUrl(url);
		config.setUsername("root");
		config.setPassword("root");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);	
	}
	
	public HikariDataSource getDataSource() {
		return ds;
	}
}