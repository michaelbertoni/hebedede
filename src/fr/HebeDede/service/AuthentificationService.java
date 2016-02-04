package fr.HebeDede.service;

import fr.HebeDede.data.DatabaseConnection;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.UtilisateurDAO;

public class AuthentificationService {
	
	public boolean login(String username, String password) throws UtilisateurInconnuException, ClassNotFoundException,
			IllegalAccessException {
		Utilisateur utilisateur;
		UtilisateurDAO utilisateurDAO = new UtilisateurDAO(DatabaseConnection.getInstance());
		utilisateur = utilisateurDAO.findByUsername(username);
		return utilisateur.getPassword().equals(password);
	}
	
}