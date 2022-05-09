package com.servlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.configurations.DatabaseConnection;
import com.datasources.DataSourceConfig;
import com.datasources.DataSourceFactory;
import com.datasources.DataSourceMap;

public class MyServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {

		System.out.println("Server running ....");

		DataSourceMap dataSourceMap = DataSourceFactory.getInstance();

		// Get DataSources URLs

		try {
			Connection con = DatabaseConnection.initializeDatabase();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from datasource");
			
			// Retrieve URLS
			while (rs.next()) {
				String cluster_id = rs.getString("clusterID");
				String url = rs.getString("dbURL");

				// Initialize dataSources
				DataSourceConfig dSource = new DataSourceConfig(url);

				dataSourceMap.putDataSource(cluster_id, dSource.getDataSource());

			}
			
			// Close all the connections
            con.close();

		} catch (Exception e) {
			System.out.println("Could not iniltialize database :(");
			e.printStackTrace();
		}

	}

}
