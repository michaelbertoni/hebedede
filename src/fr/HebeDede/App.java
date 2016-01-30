package fr.HebeDede;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import fr.HebeDede.ui.Console;

public class App {
	
		public static void main(String[] args) throws SQLException {
			
			// Chargement MySQL JDBC Driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("JDBC driver successfuly loaded");
			
			
			// Connexion à la BDD
			Connection conn = null;
			try{
			conn = (Connection) DriverManager.getConnection( "jdbc:mysql://localhost:3308/HebeDede", "root", "root");
			}catch(Exception e){
			// Gestion des exceptions
			}finally{
			// Fermeture de la connexion dans tous les cas
			if(conn != null){
			conn.close();
			}
			
			Console.promptLogin();
			
			Console.promptMenu();
		}
		
	}

}