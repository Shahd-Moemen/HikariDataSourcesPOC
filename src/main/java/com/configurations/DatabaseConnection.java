package com.configurations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection initializeDatabase()
        throws SQLException, ClassNotFoundException
    {
        // Initialize all the information regarding
    	final String DB_URL = "jdbc:mysql://localhost/databasemeta";
    	final String USER = "root";
    	final String PASSWORD = "root";
  
    	Connection con = null;
    	
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println(e);
		}
		
    	return con;
    }
}