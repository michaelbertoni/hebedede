package fr.HebeDede.service;

import fr.HebeDede.data.UserData;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;

public class AuthentificationService {
	private UserData donneesUtilisateur = new UserData();

	public boolean login(String username, String password) {

		try {
			Utilisateur utilisateur = donneesUtilisateur.trouverUtilisateur(username);
			return utilisateur.getPassword().equals(password);

		} catch (UtilisateurInconnuException e) {
			return false;
		}

	}
}