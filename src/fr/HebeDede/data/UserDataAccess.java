package fr.HebeDede.data;

import java.util.List;

import fr.HebeDede.exception.DataAccessException;
import fr.HebeDede.exception.UserAlreadyExistsException;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;

public interface UserDataAccess {
	
	public Utilisateur findUser(String username) throws UtilisateurInconnuException, DataAccessException;
	
	public List<Utilisateur> getAllUser() throws DataAccessException;
	
	public void createUser(Utilisateur newUser) throws UserAlreadyExistsException, DataAccessException;
	
	public void updateUser(Utilisateur user) throws UtilisateurInconnuException, DataAccessException;
	
	public void removeUser(String username) throws UtilisateurInconnuException, DataAccessException;

}
