package fr.HebeDede;

import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Bandedessinee;
import fr.HebeDede.repositories.BandedessineeDAO;
import fr.HebeDede.ui.Console;

public class App {
	
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		
//			Console.promptMenu();
			BandedessineeDAO bdDAO = new BandedessineeDAO();
			Bandedessinee bd = bdDAO.find(3);
			Console.supprimerFiche(bd);
		
		}
}
