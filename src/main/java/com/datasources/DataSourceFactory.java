package com.datasources;

public class DataSourceFactory {

	private DataSourceFactory() {

	}

	private static DataSourceMap instance;

	public static synchronized DataSourceMap getInstance() {
		if (instance != null)
			return instance;

		instance = new DataSourceMap();

		return instance;
	}
}
