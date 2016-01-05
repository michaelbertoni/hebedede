package fr.HebeDede.data;

import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;

public class UserData {

	List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

	public void DonneesUtilisateur() {
		// Jeu d'essai
		utilisateurs.add(new Utilisateur("Robert", "licorne42"));
		utilisateurs.add(new Utilisateur("Paul", "licorne43"));
		utilisateurs.add(new Utilisateur("Jean", "licorne44"));
	}

	public Utilisateur trouverUtilisateur(String username) throws UtilisateurInconnuException {

		for (Utilisateur u : utilisateurs) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}

		throw new UtilisateurInconnuException();

	}

}