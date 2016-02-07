package fr.HebeDede.repositories;

<<<<<<< HEAD
=======
import java.sql.Connection;
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.HebeDede.data.DAO;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Option;

public class OptionDAO extends DAO<Option> {
	
	UtilisateurDAO userDAO;
	
	ArticleDAO articleDAO;

<<<<<<< HEAD
	public OptionDAO() throws ClassNotFoundException, IllegalAccessException {
		super();
=======
	public OptionDAO(Connection conn) {
		super(conn);
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854
	}

	@Override
	public boolean create(Option obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM option");

			result.moveToInsertRow();
				result.updateDate("dateDebutOption", (Date) obj.getDateDebutOption());
				result.updateDate("dateFinOption", (Date) obj.getDateFinOption());
				result.updateInt("Utilisateur_idUtilisateur", obj.getUtilisateur().getIdUtilisateur());
				result.updateInt("Article_idArticle", obj.getArticle().getIdArticle());
				result.insertRow();
				result.close();
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Option obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM option");

			while (result.next()) {
					int id = result.getInt("idOption");
					if (id == obj.getIdOption()) {
						result.deleteRow();
						result.close();
						return true;
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Option obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM option");

			while (result.next()) {
					int id = result.getInt("idOption");
					if (id == obj.getIdOption()) {
						result.moveToCurrentRow();
						result.updateDate("dateDebutOption", (Date) obj.getDateDebutOption());
						result.updateDate("dateFinOption", (Date) obj.getDateFinOption());
						result.close();
						return true;
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
<<<<<<< HEAD
	public Option find(Integer id) throws UtilisateurInconnuException {
=======
	public Option find(Integer id) {
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854
		Option option = new Option();

		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM option WHERE idOption = " + id);
			if(result.first()) {
				option = new Option(result.getDate("dateDebutOption"),
				result.getDate("dateFinOption"),
				articleDAO.find(result.getInt("Article_idArticle")),
				userDAO.find(result.getInt("Utilisateur_idUtilisateur")),
				id);
				result.close();
				return option;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}