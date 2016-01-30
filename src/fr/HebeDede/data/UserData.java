package fr.HebeDede.data;

import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.exception.UserAlreadyExistsException;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.old.Role;
import fr.HebeDede.model.old.Utilisateur;


public class UserData implements UserDataAccess{

	List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

	public void DonneesUtilisateur() {
		// Jeu d'essai
		utilisateurs.add(new Utilisateur("Robert", "licorne42", RoleClient()));
		utilisateurs.add(new Utilisateur("Paul", "licorne43", RoleEmploye()));
		utilisateurs.add(new Utilisateur("Jean", "licorne44", RolePatron()));
	}

	public Utilisateur findUser(String username) throws UtilisateurInconnuException {

		for (Utilisateur u : utilisateurs) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}

		throw new UtilisateurInconnuException();

	}
	
	
	
	public Role RoleEmploye() {
		Role role = new Role(false, false, true, true, true);
		return role;
	}
	
	public Role RoleClient() {
		Role role = new Role(true, true, true, false, false);
		return role;
	}
	
	public Role RolePatron() {
		Role role = new Role(true, true, true, true, true);
		return role;
	}
	
	
	@Override
	public List<Utilisateur> getAllUser() {
		this.DonneesUtilisateur();
		return utilisateurs;
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