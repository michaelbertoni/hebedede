package fr.HebeDede.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Article;
import fr.HebeDede.model.Option;
import fr.HebeDede.model.Utilisateur;

public class OptionDAO extends DAO<Option> {
	
	UtilisateurDAO userDAO = new UtilisateurDAO();
	
	ArticleDAO articleDAO = new ArticleDAO();

	public OptionDAO() {
		super();
	}

	@Override
	public boolean create(Option obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM optionarticle");

			result.moveToInsertRow();
				result.updateTimestamp("dateDebutOption", obj.getDateDebutOption());
				result.updateTimestamp("dateFinOption", obj.getDateFinOption());
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
					"SELECT * FROM optionarticle");

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
					"SELECT * FROM optionarticle");

			while (result.next()) {
					int id = result.getInt("idOption");
					if (id == obj.getIdOption()) {
						result.moveToCurrentRow();
						result.updateTimestamp("dateDebutOption", obj.getDateDebutOption());
						result.updateTimestamp("dateFinOption", obj.getDateFinOption());
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
	public Option find(Integer id) {
		Option option = new Option();

		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM optionarticle WHERE idOption = " + id);
			if(result.first()) {
				option = new Option(result.getTimestamp("dateDebutOption"),
				result.getTimestamp("dateFinOption"),
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
	
	public List<Option> findAll() {
		List<Option> optionList = new ArrayList<Option>();
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM optionarticle");
			while (result.next()) {
				Option option = new Option(result.getTimestamp("dateDebutOption"),
					result.getTimestamp("dateFinOption"),
					articleDAO.find(result.getInt("Article_idArticle")),
					userDAO.find(result.getInt("Utilisateur_idUtilisateur")),
					result.getInt("idOption"));
				optionList.add(option);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optionList;
	}
	
	public List<Option> findByArticle(Article article) {
		List<Option> optionList = new ArrayList<Option>();
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM optionarticle WHERE Article_idArticle = " + article.getIdArticle());
			while (result.next()) {
				Option option = new Option(result.getTimestamp("dateDebutOption"),
					result.getTimestamp("dateFinOption"),
					article,
					userDAO.find(result.getInt("Utilisateur_idUtilisateur")),
					result.getInt("idOption"));
				optionList.add(option);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optionList;
	}
	
	public List<Option> findByUtilisateur(Utilisateur user) {
		List<Option> optionList = new ArrayList<Option>();
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM optionarticle WHERE Utilisateur_idUtilisateur = " + user.getIdUtilisateur());
			while (result.next()) {
				Option option = new Option(result.getTimestamp("dateDebutOption"),
					result.getTimestamp("dateFinOption"),
					articleDAO.find(result.getInt("Article_idArticle")),
					user,
					result.getInt("idOption"));
				optionList.add(option);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optionList;
	}

	
}