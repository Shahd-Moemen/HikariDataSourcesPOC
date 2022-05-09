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

import com.configurations.DatabaseConnection;

/**
 * Servlet implementation class clusterServlet
 */
@WebServlet("/clusters")
public class clusterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @see HttpServlet#HttpServlet()
	 */
	public clusterServlet() throws ClassNotFoundException, SQLException {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		// Retrieve names
		try {
			Connection con = DatabaseConnection.initializeDatabase();
			
			Statement stmt = con.createStatement();
			
			try {
				ResultSet rs = stmt.executeQuery("select * from datasource");

				while (rs.next()) {
					String url = rs.getString("dbURL");
					String id = rs.getString("clusterID");

					out.println(id + " : " + url);
				}
			} finally {
				stmt.close();
				con.close();
			}	

		} catch (Exception e) {
			System.out.println("!! ERROR !!");
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

			Connection con = DatabaseConnection.initializeDatabase();

			PreparedStatement st = con.prepareStatement("insert into datasource(id, dbURL) values(?, ?)");
			
			try {
				String datasourceURL = request.getParameter("url");

				st.setString(1, UUID.randomUUID().toString());
				st.setString(2, datasourceURL);

				st.executeUpdate();

				PrintWriter out = response.getWriter();
				out.println("<html><body><b>Successfully Inserted" + "</b></body></html>");
				
			} finally {
				st.close();
				con.close();
			}	

		} catch (Exception e) {
			System.out.println("!! ERROR !!");
			e.printStackTrace();
		}
	}

}
