package fr.HebeDede;

import fr.HebeDede.data.DatabaseConnection;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.repositories.UtilisateurDAO;
import fr.HebeDede.ui.Console;

public class App {
	
		public static void main(String[] args) throws UtilisateurInconnuException{
			
//			DatabaseConnection.getInstance();
//			
//			Console.promptLogin();
//			
//			Console.promptMenu();
			UtilisateurDAO dao = new UtilisateurDAO();
			System.out.println(dao.find(1));
			
		}
}
