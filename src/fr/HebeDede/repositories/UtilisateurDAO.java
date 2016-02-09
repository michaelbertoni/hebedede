package fr.HebeDede.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.data.DAO;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.service.ConsoleService;

public class UtilisateurDAO extends DAO<Utilisateur> {

	public UtilisateurDAO() {
		super();
	}

	@Override
	public void create(Utilisateur obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM utilisateur");
			if (this.findByUsername(obj.getUsername()) != null) {
				System.out.println("L'utilisateur existe déjà !");
			} else {
				result.moveToInsertRow();
				result.updateString("username", obj.getUsername());
				result.updateString("password", obj.getPassword());
				result.updateString("role", obj.getRole());
				result.insertRow();
				result.beforeFirst();
				result.close();
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		}
	}

	@Override
	public void delete(Utilisateur obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM utilisateur");
			
			while (result.next()) {
					int id = result.getInt("idUtilisateur");
					if (id == obj.getIdUtilisateur()) {
						result.deleteRow();
						result.close();
					}
				}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		}
	}

	@Override
	public void update(Utilisateur obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM utilisateur");
			
				while (result.next()) {
					int id = result.getInt("idUtilisateur");
					if (id == obj.getIdUtilisateur()) {
						result.moveToCurrentRow();
						result.updateString("username", obj.getUsername());
						result.updateString("password", obj.getPassword());
						result.updateString("role", obj.getRole());
						result.close();
					}
				}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		}
	}

	public List<Utilisateur> findAll() {
		List<Utilisateur> userList = new ArrayList<Utilisateur>();
		Utilisateur user = new Utilisateur();

		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM utilisateur");
			while (result.next()) {
				user = new Utilisateur(result.getString("username"), result.getString("password"),
						result.getString("role"), result.getInt("idUtilisateur"));
				userList.add(user);
			}
			result.close();
			return userList;
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		}
	}

	@Override
	public Utilisateur find(Integer id) {
		Utilisateur user = new Utilisateur();
	
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM utilisateur WHERE idUtilisateur = " + id);
			if(result.first()) {
				user = new Utilisateur(result.getString("username"), result.getString("password"),
						result.getString("role"), id);
				result.close();
				return user;
			} else {
				throw new UtilisateurInconnuException();
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} catch (UtilisateurInconnuException e) {
			return null;
		}
	}

	public Utilisateur findByUsername(String username) {
		Utilisateur user = null;
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM utilisateur WHERE username = '" + username + "'");
			if(result.first()) {
				user = new Utilisateur(username, result.getString("password"), result.getString("role"),
						result.getInt("idUtilisateur"));
				result.close();
				return user;
			} else {
				throw new UtilisateurInconnuException();
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		} catch (UtilisateurInconnuException e) {
			return null;
		}
	}

}