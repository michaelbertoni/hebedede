package fr.HebeDede.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static Connection connect;

	public static Connection getInstance() throws ClassNotFoundException,
			IllegalAccessException {
		if(connect == null){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver O.K.");

				connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/HebeDede", "root", "root");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connect;
	}	
}