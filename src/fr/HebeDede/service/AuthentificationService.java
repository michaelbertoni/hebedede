package fr.HebeDede.service;

import fr.HebeDede.data.DatabaseConnection;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.old.Utilisateur;

public class AuthentificationService {
	private DatabaseConnection donneesUtilisateur = new DatabaseConnection();

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