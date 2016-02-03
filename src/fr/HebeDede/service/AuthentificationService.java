package fr.HebeDede.service;

import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.UtilisateurDAO;

public class AuthentificationService {
	
	static UtilisateurDAO userDAO = new UtilisateurDAO();

	public boolean login(String username, String password) throws UtilisateurInconnuException {
		Utilisateur utilisateur;
		utilisateur = userDAO.findByUsername(username);
		return utilisateur.getPassword().equals(password);
	}
	
	public Utilisateur saveConnectedUser(String username) throws UtilisateurInconnuException {
		Utilisateur connectedUser = null;
		connectedUser = userDAO.findByUsername(username);
		return connectedUser;
	}
}