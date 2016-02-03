package fr.HebeDede.ui;

import java.util.Scanner;

import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.service.AuthentificationService;

public class Console {
	
	public static Utilisateur user;
	
	public static void promptLogin() throws UtilisateurInconnuException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Login");
		String username = sc.nextLine();
		
		System.out.println("Password");
		String password = sc.nextLine();
		
		AuthentificationService authentificationService = new AuthentificationService();
		
		if(authentificationService.login(username, password)){
			System.out.println("Vous êtes authentifié\n");
			user = authentificationService.saveConnectedUser(username);
		}else{
			System.out.println("Echec de l'authentification\n");
		}
		
		sc.close();
	}

	public static void promptMenu() {
		System.out.println("Bienvenue sur le site web d'HébéDédé !\n\nMenu principal :");
		
		System.out.println("1. Rechercher de BD\n-------------------------");
		if (user.getRole().equals("Employe")) {
			System.out.println("2. Ajouter une BD\n-------------------------");
		}
	}

}