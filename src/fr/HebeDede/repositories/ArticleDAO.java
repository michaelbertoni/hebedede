package fr.HebeDede.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Article;

public class ArticleDAO extends DAO<Article> {

	public ArticleDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Article obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM article");
			if (this.find(obj.getIdArticle()) != null) {
				System.out.println("L'article existe déjà !");
			} else {
				result.moveToInsertRow();
				result.updateBoolean("enRayon", obj.getEnRayon());
				result.updateFloat("prix", obj.getPrix());
				result.insertRow();
				result.beforeFirst();
				result.close();
				this.connect.close();
				return true;
			}
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
						this.connect.close();
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
						this.connect.close();
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
				this.connect.close();
				return article;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}