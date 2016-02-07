package fr.HebeDede.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Article;

public class ArticleDAO extends DAO<Article> {

	public ArticleDAO() throws ClassNotFoundException, IllegalAccessException {
		super();
	}

	@Override
	public boolean create(Article obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM article");

			result.moveToInsertRow();
				result.updateBoolean("enRayon", obj.getEnRayon());
				result.updateFloat("prix", obj.getPrix());
				result.insertRow();
				result.close();
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Article obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM article");

			while (result.next()) {
					int id = result.getInt("idArticle");
					if (id == obj.getIdArticle()) {
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
	public boolean update(Article obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM article");

			while (result.next()) {
					int id = result.getInt("idUtilisateur");
					if (id == obj.getIdArticle()) {
						result.moveToCurrentRow();
						result.updateBoolean("enRayon", obj.getEnRayon());
						result.updateFloat("prix", obj.getPrix());
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
	public Article find(Integer id) {
		Article article = new Article();

		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM article WHERE idArticle = " + id);
			if(result.first()) {
				article = new Article(result.getBoolean("enRayon"), result.getFloat("prix"), id);
				result.close();
				return article;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer findLastEntryId() {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM article");
			result.last();
			Integer id = result.getInt("idArticle");
			result.close();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}