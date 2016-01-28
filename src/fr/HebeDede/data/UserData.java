package fr.HebeDede.data;

import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.exception.UserAlreadyExistsException;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;


public class UserData implements UserDataAccess{

	List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

	public void DonneesUtilisateur() {
		// Jeu d'essai
		utilisateurs.add(new Utilisateur("Robert", "licorne42"));
		utilisateurs.add(new Utilisateur("Paul", "licorne43"));
		utilisateurs.add(new Utilisateur("Jean", "licorne44"));
	}

	public Utilisateur findUser(String username) throws UtilisateurInconnuException {

		for (Utilisateur u : utilisateurs) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}

		throw new UtilisateurInconnuException();

	}
	
	@Override
	public List<Utilisateur> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(Utilisateur newUser) throws UserAlreadyExistsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(Utilisateur user) throws UtilisateurInconnuException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUser(String username) throws UtilisateurInconnuException {
		// TODO Auto-generated method stub
		
	}

}