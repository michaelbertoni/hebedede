package fr.HebeDede.service;

import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.UtilisateurDAO;

public class AuthentificationService {
	
	public Boolean login(String username, String password) {
		Utilisateur utilisateur;
		Boolean connected = false;
		UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
		utilisateur = utilisateurDAO.findByUsername(username);
		connected = utilisateur.getPassword().equals(password);
		return connected;
	}
	
}