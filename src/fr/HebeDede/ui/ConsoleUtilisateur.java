package fr.HebeDede.ui;

import java.util.List;

import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.UtilisateurDAO;
import fr.HebeDede.service.ConsoleService;

public class ConsoleUtilisateur {
	
	static UtilisateurDAO userDAO = new UtilisateurDAO();

	public static void promptCompteClient(Utilisateur user) {
		ConsoleService.affiche("\n******* Mon compte client *******\n");
		ConsoleService.affiche("Utilisateur n°" + user.getIdUtilisateur() + 
				" - Username : " + user.getUsername() + 
				" - " + user.getRole());
		
		ConsoleService.affiche("\nMenu :");
		ConsoleService.affiche("1. Modifier compte");
		ConsoleService.affiche("2. Supprimer un compte");
		
		ConsoleService.affiche("0. Retour au menu principal");
	}

	public static void promptGererComptesUtilisateurs() {
		ConsoleService.affiche("\n******* Liste des utilisateurs *******\n");
		List<Utilisateur> userList = userDAO.findAll();
		for (Utilisateur utilisateur : userList) {
			ConsoleService.affiche("Utilisateur n°" + utilisateur.getIdUtilisateur() + " - Username : " + utilisateur.getUsername() + " - " + utilisateur.getRole());
		}
		
		ConsoleService.affiche("\nMenu :");
		ConsoleService.affiche("1. Ajouter un compte");
		ConsoleService.affiche("2. Modifier un compte");
		ConsoleService.affiche("3. Supprimer un compte");
		
		ConsoleService.affiche("0. Retour au menu principal");
		
		Integer choice = ConsoleService.choixMenuMinMax(0,3);
	    
		Integer numUser = 0;
		Utilisateur utilisateur = new Utilisateur();
	    switch (choice) {
	    	case 1:
	    		promptCreerCompte();
	    	case 2:
	    		ConsoleService.affiche("Renseigner le numéro de l'utilisateur à modifier : ");
	    		numUser = Console.sc.nextInt();
	    		utilisateur = userDAO.find(numUser);
	    		promptModifierCompte(utilisateur);
	    	case 3:
	    		ConsoleService.affiche("Renseigner le numéro de l'utilisateur à supprimer : ");
	    		numUser = Console.sc.nextInt();
	    		utilisateur = userDAO.find(numUser);
	    		promptSupprimerCompte(utilisateur);
	    	case 0:
	    		Console.promptMenu();
	    }
	    
	}

	public static void promptCreerCompte() {
		ConsoleService.affiche("\n******* Création compte *******\n");
		ConsoleService.affiche("Renseigner un nom d'utilisateur :");
		String username = Console.sc.nextLine();
		String password = "";
		String password2 = "";
		do {
			ConsoleService.affiche("Renseigner un mot de passe :");
			password = Console.sc.nextLine();
			ConsoleService.affiche("Confirmer le mot de passe :");
			password2 = Console.sc.nextLine();
			if (password.equals(password2)) {
				ConsoleService.affiche("Les mots de passe ne correspondent pas !");
			}
		} while (password.equals(password2));
		String role = "";
		if (Console.user == null) {
			role = "Client";
		}
		else if (Console.user.getRole().equals("Chef")) {
			do {
				ConsoleService.affiche("Renseigner le type d'utilisateur à créer (Employe/Client): ");
				role = Console.sc.nextLine();
				if (role.equals("") && !role.equals("Employe") && !role.equals("Client")) {
					ConsoleService.affiche("Saisie incorrecte.");
				}
			} while (role.equals("") && !role.equals("Employe") && !role.equals("Client"));
		}
		
		ConsoleService.affiche("Création de l'utilisateur...");
		Utilisateur utilisateur = new Utilisateur(username, password, role);
		userDAO.create(utilisateur);
		ConsoleService.affiche("Compte utilisateur créé !");
		if (Console.user == null) {
			ConsoleService.affiche("Vous pouvez maintenant vous connecter à votre compte depuis le menu principal.");
			ConsoleService.sleep(3000);
		}
		else {
			ConsoleService.affiche("Retour au menu principal...");
			ConsoleService.sleep(3000);
		}
		Console.promptMenu();
	}

	public static void promptModifierCompte(Utilisateur utilisateur) {
		String choix = ConsoleService.renseigneChampDeuxString("\nQuel champ voulez-vous modifier ?", "username", "password");
		
		if (choix.equals("username")) {
			ConsoleService.affiche("Renseigner un nouveau nom d'utilisateur :");
			String newUsername = Console.sc.nextLine();
			utilisateur.setUsername(newUsername);
		} else if (choix.equals("password")) {
			String password = "";
			String password2 = "";
			do {
				ConsoleService.affiche("Renseigner un nouveau mot de passe :");
				password = Console.sc.nextLine();
				ConsoleService.affiche("Confirmer le mot de passe :");
				password2 = Console.sc.nextLine();
				if (password.equals(password2)) {
					ConsoleService.affiche("Les mots de passe ne correspondent pas !");
				}
			} while (password.equals(password2));
			utilisateur.setPassword(password);
		}
	
		ConsoleService.affiche("Modification de l'utilisateur...");
		userDAO.update(utilisateur);
		ConsoleService.affiche("Compte utilisateur modifié !");
		ConsoleService.affiche("Retour au menu principal...");
		ConsoleService.sleep(3000);
		Console.promptMenu();
		
	}

	public static void promptSupprimerCompte(Utilisateur utilisateur) {
		String choix = ConsoleService.renseigneChampDeuxString("\nÊtes-vous sûr de vouloir supprimer cet utilisateur ?\nSa suppression entrainera également l'effacement des réservations correspondantes également.", "oui", "non");
		
		if (choix.equals("oui")) {
			ConsoleService.affiche("Suppression de l'utilisateur...");
			userDAO.delete(utilisateur);
			ConsoleService.affiche("Compte utilisateur supprimé !");
		}
		
		ConsoleService.affiche("Retour au menu principal...");
		ConsoleService.sleep(3000);
		Console.promptMenu();
	}

}
