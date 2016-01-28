package fr.HebeDede.service;

import fr.HebeDede.data.UserData;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;

public class AuthentificationService {
	private UserData donneesUtilisateur = new UserData();

	public boolean login(String username, String password) {

		donneesUtilisateur.DonneesUtilisateur();
		Utilisateur utilisateur;
		try {
			utilisateur = donneesUtilisateur.findUser(username);
			return utilisateur.getPassword().equals(password);
		} catch (UtilisateurInconnuException e) {
			return false;
		}

	}
	
	public Utilisateur saveConnectedUser(String username) {
		donneesUtilisateur.DonneesUtilisateur();
		Utilisateur connectedUser = null;
		try {
			connectedUser = donneesUtilisateur.findUser(username);
			return connectedUser;
		} catch (UtilisateurInconnuException e) {
			e.printStackTrace();
		}
		return null;

	}
}