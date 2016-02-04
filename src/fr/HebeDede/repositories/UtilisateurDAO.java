package fr.HebeDede.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Utilisateur;

public class UtilisateurDAO extends DAO<Utilisateur> {

	public UtilisateurDAO(Connection conn) {
		super(conn);
	}

	public Utilisateur find(int id) {
		Utilisateur user = new Utilisateur();

		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM utilisateur WHERE idUtilisateur = " + id);
			if(result.first())
				user = new Utilisateur(result.getString("username"), result.getString("password"),
						result.getString("role"), id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public Utilisateur findByUsername(String username) {
		Utilisateur user = null;
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
).executeQuery(
					"SELECT * FROM utilisateur WHERE username = '" + username + "'");
			if(result.first())
				user = new Utilisateur(username, result.getString("password"), result.getString("role"),
						result.getInt("idUtilisateur"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean create(Utilisateur obj) {
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
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Utilisateur obj) {
		return false;
	}

	@Override
	public boolean update(Utilisateur obj) {
		return false;
	}

}