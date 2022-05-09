package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datasources.DataSourceFactory;
import com.datasources.DataSourceMap;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Servlet implementation class MultipleDatasourcesServlet
 */
@WebServlet("/users")
public class userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public userServlet() throws ClassNotFoundException, SQLException {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			/*
			 * cluseterId 4 -> dataSource 2 -> user's names end by 2 
			 * cluseterId 5 -> dataSource 1 -> user's names end by 1
			 * 
			 */

			String clusterIdString = request.getParameter("cluster");

			DataSourceMap dataSourceMap = DataSourceFactory.getInstance();

			HikariDataSource ds = dataSourceMap.getDataSource(clusterIdString);			
			
			PrintWriter out = response.getWriter();

			// connect on a datasource
			try {
				Connection con = ds.getConnection();

				Statement stmt = con.createStatement();
				
				try {
					ResultSet rs = stmt.executeQuery("select * from user");

					// Retrieve names
					while (rs.next()) {
						String name = rs.getString("name");
						String id = rs.getString("id");

						out.println(id + " : " + name);
					}
				} finally {
					stmt.close();
					con.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			/*
			 * cluseterId 4 -> dataSource 2 -> user's names end by 2 
			 * cluseterId 5 -> dataSource 1 -> user's names end by 1
			 * 
			 */

			String clusterIdString = request.getParameter("cluster");

			DataSourceMap dataSourceMap = DataSourceFactory.getInstance();

			HikariDataSource ds = dataSourceMap.getDataSource(clusterIdString);			
			
			PrintWriter out = response.getWriter();

			// connect on a datasource
			try {
				Connection con = ds.getConnection();

				PreparedStatement st = con.prepareStatement("insert into user(id, name) values(?, ?)");
				
				try {
					String name = request.getParameter("name");

					st.setString(1, UUID.randomUUID().toString());
					st.setString(2, name);

					st.executeUpdate();

					out.println("<html><body><b>Successfully Inserted" + "</b></body></html>");
				} finally {
					st.close();
					con.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
