package fr.HebeDede.ui;

import java.sql.SQLException;
import java.util.Scanner;

import fr.HebeDede.data.DatabaseConnection;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.UtilisateurDAO;
import fr.HebeDede.service.AuthentificationService;
import fr.HebeDede.service.ConsoleService;

public class Console {
	
	public static Utilisateur user;
	public static Scanner sc = new Scanner(System.in);
	public static Long dureeResa = (long) (4*60*60*1000);
	
	public static void promptLogin() {
		AuthentificationService authentificationService = new AuthentificationService();
		
		String username = null;
		String password = null;
		
		Boolean userConnected = false;
		while(userConnected != true) {
			sc.nextLine();
			
			ConsoleService.affiche("Login :");
			username = sc.nextLine();
			
			ConsoleService.affiche("Password :");
			password = sc.nextLine();
			
			userConnected = authentificationService.login(username, password);
			if (userConnected != true) {
				ConsoleService.affiche("Mot de passe incorrect ! Réessayez.");
			}
		}
		UtilisateurDAO userDAO = new UtilisateurDAO();
		user = userDAO.findByUsername(username);
		
		ConsoleService.affiche("Vous êtes connectés !");
		
		promptMenu();
	}

	public static void promptMenu() {
		ConsoleService.affiche("\n******* Site Web d'HébéDédé *******\n\nMenu principal :");
		
		ConsoleService.affiche("1. Recherche/Liste des articles\n");
		if (user == null) {
			ConsoleService.affiche("2. Login");
			ConsoleService.affiche("3. Créer un compte");
		}
		else {
			switch (user.getRole()){
			case "Employe" :
				ConsoleService.affiche("2. Liste des options");
				ConsoleService.affiche("3. Mon compte");
				break;
			case "Chef" :
				ConsoleService.affiche("2. Liste des options");
				ConsoleService.affiche("3. Mon compte");
				ConsoleService.affiche("4. Gérer comptes utilisateurs");
				break;
			case "Client" :
				ConsoleService.affiche("2. Mes options");
				ConsoleService.affiche("3. Mon compte");
				break;
			}
		}
		ConsoleService.affiche("0. Quitter l'application");
		
		Integer choice = ConsoleService.choixMenuMinMax(0,4);
		selectMenu(choice);
	}
		
	public static void selectMenu(int choice) {
	    if (user == null) {
	    	switch (choice) {
		    	case 1:
		    		ConsoleArticle.promptArticleList();
		    		break;
		    	case 2:
					promptLogin();
					break;
		    	case 3:
		    		ConsoleUtilisateur.promptCreerCompte();
		    		break;
		    	case 4:
		    		ConsoleService.affiche("\nChoix incorrect.");
		    		choice = ConsoleService.choixMenuMinMax(0,4);
		    		selectMenu(choice);
					break;
		    	case 0:
	    			closeApp();
	    			break;
	    		default:
	    			ConsoleService.affiche("\nChoix incorrect.");
	    			choice = ConsoleService.choixMenuMinMax(0,4);
	    			selectMenu(choice);
					break;
	    	}
	    } else {
			switch (choice) {
		    	case 1:
		    		ConsoleArticle.promptArticleList();
		    		break;
		    	case 2:
		    		switch (user.getRole()) {
		    			case "Employe":
		    				ConsoleOption.promptOptionsListClient(user);
		    				break;
		    			case "Chef":
		    				ConsoleOption.promptOptionsListAll();
		    				break;
		    			case "Client":
		    				ConsoleOption.promptOptionsListAll();
		    				break;
		    		}
		    		break;
		    	case 3:
		    		switch (user.getRole()) {
		    			case "Employe":
		    				ConsoleUtilisateur.promptCompteClient(user);
		    				break;
		    			case "Chef":
		    				ConsoleUtilisateur.promptCompteClient(user);
		    				break;
		    			case "Client":
		    				ConsoleUtilisateur.promptCompteClient(user);
		    				break;
		    		}
		    		break;
	    		case 4:
	    			switch (user.getRole()) {
		    			case "Employe":
		    				ConsoleService.affiche("\nChoix incorrect.");
		    				choice = ConsoleService.choixMenuMinMax(0,4);
		    				selectMenu(choice);
		    				break;
		    			case "Chef":
		    				ConsoleUtilisateur.promptGererComptesUtilisateurs();
		    				break;
		    			case "Client":
		    				ConsoleService.affiche("\nChoix incorrect.");
		    				choice = ConsoleService.choixMenuMinMax(0,4);
		    				selectMenu(choice);
		    				break;
	    			}
	    			break;
	    		case 0:
	    			closeApp();
	    			break;
	    		default:
	    			ConsoleService.affiche("\nChoix incorrect.");
	    			choice = ConsoleService.choixMenuMinMax(0,4);
	    			selectMenu(choice);
					break;
			}
	    }
	}

	public static void closeApp() {
		try {
			DatabaseConnection.getInstance().close();
			sc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}