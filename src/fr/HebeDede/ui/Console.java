package fr.HebeDede.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.HebeDede.data.DatabaseConnection;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Bandedessinee;
import fr.HebeDede.model.Figurine;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.BandedessineeDAO;
import fr.HebeDede.repositories.FigurineDAO;
import fr.HebeDede.repositories.UtilisateurDAO;
import fr.HebeDede.service.AuthentificationService;

public class Console {
	
	public static Utilisateur user;
	private static String username = null;
	public static Scanner sc = new Scanner(System.in);
	
	public static void promptLogin() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException {
		AuthentificationService authentificationService = new AuthentificationService();
		
		String password = null;
		
		Boolean userConnected = false;
		while(userConnected != true) {
			try {
				sc.nextLine();
				
				System.out.println("Login :");
				username = sc.nextLine();
				
				System.out.println("Password :");
				password = sc.nextLine();
				
				userConnected = authentificationService.login(username, password);
				if (userConnected != true) {
					System.out.println("Mot de passe incorrect ! Réessayez.");
				}
			} catch (UtilisateurInconnuException e) { }
		}
		UtilisateurDAO userDAO = new UtilisateurDAO();
		user = userDAO.findByUsername(username);
		
		System.out.println("Vous êtes connectés !");
		
		promptMenu();
	}

	public static void promptMenu() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException {
		System.out.println("Site Web d'HébéDédé !\n\nMenu principal :");
		
		System.out.println("1. Recherche/Liste des articles\n");
		if (user == null) {
			System.out.println("2. Login");
			System.out.println("3. Créer un compte");
		}
		else {
			switch (user.getRole()){
			case "Employe" :
				System.out.println("2. Liste des options");
				System.out.println("3. Mon compte");
				break;
			case "Chef" :
				System.out.println("2. Liste des options");
				System.out.println("3. Mon compte");
				System.out.println("4. Gérer comptes utilisateurs");
				break;
			case "Client" :
				System.out.println("2. Mes options");
				System.out.println("3. Mon compte");
				break;
			}
		}
		System.out.println("0. Quitter l'application");
		chooseMenu();
	}
		
	public static void chooseMenu() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException { 
	    Boolean choixCorrect = false;
	    int choice = 0;
	    while (choixCorrect == false) {
	    	System.out.println("\nVeuillez renseigner votre choix");
	    	choice = sc.nextInt();
	    	if (choice < 0 && choice > 4) {
	    		System.out.println("\nChoix incorrect.");
	    	} else {
	    		choixCorrect = true;
	    	}
	    }
	    selectMenu(choice);
	}
	
	public static void selectMenu(int choice) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException {
	    if (user == null) {
	    	switch (choice) {
		    	case 1:
		    		promptArticleList();
		    		break;
		    	case 2:
					promptLogin();
					break;
		    	case 3:
		    		promptCreerCompte();
		    		break;
		    	case 4:
		    		System.out.println("\nChoix incorrect.");
					chooseMenu();
					break;
		    	case 0:
	    			closeApp();
	    			break;
	    	}
	    } else {
			switch (choice) {
		    	case 1:
		    		promptArticleList();
		    		break;
		    	case 2:
		    		switch (user.getRole()) {
		    			case "Employe":
		    				promptOptionsList();
		    				break;
		    			case "Chef":
		    				promptOptionsList();
		    				break;
		    			case "Client":
		    				promptOptionsList();
		    				break;
		    		}
		    		break;
		    	case 3:
		    		switch (user.getRole()) {
		    			case "Employe":
		    				promptMonCompte();
		    				break;
		    			case "Chef":
		    				promptMonCompte();
		    				break;
		    			case "Client":
		    				promptMonCompte();
		    				break;
		    		}
		    		break;
	    		case 4:
	    			switch (user.getRole()) {
		    			case "Employe":
		    				System.out.println("\nChoix incorrect.");
		    				chooseMenu();
		    				break;
		    			case "Chef":
		    				promptGererComptesUtilisateurs();
		    				break;
		    			case "Client":
		    				System.out.println("\nChoix incorrect.");
		    				chooseMenu();
		    				break;
	    			}
	    			break;
	    		case 0:
	    			closeApp();
	    			break;
			}
	    }
	}
	
	public static void promptGererComptesUtilisateurs() {
		// TODO Auto-generated method stub
		
	}

	public static void promptMonCompte() {
		// TODO Auto-generated method stub
		
	}

	public static void promptOptionsList() {
		// TODO Auto-generated method stub
		
	}

	public static void promptCreerCompte() {
		// TODO Auto-generated method stub
		
	}

	public static void closeApp() {
		try {
			DatabaseConnection.getInstance().close();
			sc.close();
		} catch (ClassNotFoundException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public static void promptArticleList() throws ClassNotFoundException, IllegalAccessException {
		BandedessineeDAO bdDAO = new BandedessineeDAO(); 
		FigurineDAO figDAO = new FigurineDAO();
		List<Bandedessinee> bdList = bdDAO.findAllBD();
		List<Figurine> figList = figDAO.findAllFig();
		System.out.println("Liste des Articles");
		for (Bandedessinee bd : bdList) {
			System.out.println("Bande dessinée - Réf. article " + bd.getIdArticle() + " | Titre : " + bd.getLibelle() + " | Collection : " + bd.getCollection()  + " | Prix : " + bd.getPrix() + "€" + " | Disponible : " + bd.dispo());
		}
		for (Figurine fig : figList) {
			System.out.println("Figurine - Réf. article " + fig.getIdArticle() + " | Description : " + fig.getDescription() + " | Prix : " + fig.getPrix() + "€" + " | Disponible : " + fig.dispo());
		}
	}
}