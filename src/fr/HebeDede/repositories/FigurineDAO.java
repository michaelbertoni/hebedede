package fr.HebeDede.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Figurine;

public class FigurineDAO extends DAO<Figurine> {
	
	ArticleDAO articleDAO;

	public FigurineDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Figurine obj) {
		try {
			articleDAO.create(obj);
			Integer articleId = articleDAO.findLastEntryId();
			
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM figurine");
			
				result.moveToInsertRow();
				result.updateString("description", obj.getDescription());
				result.updateInt("taille", obj.getTaille());
				result.updateInt("Article_idArticle", articleId);
				result.insertRow();
				
				result.close();
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Figurine obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM figurine");
	
			while (result.next()) {
					int id = result.getInt("idFigurine");
					if (id == obj.getIdFigurine()) {
						result.deleteRow();
						
						articleDAO.delete(obj);
						
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
	public boolean update(Figurine obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM figurine");

			while (result.next()) {
					int id = result.getInt("idFigurine");
					if (id == obj.getIdFigurine()) {
						result.moveToCurrentRow();
						result.updateString("description", obj.getDescription());
						result.updateInt("taille", obj.getTaille());
						
						articleDAO.update(obj);
						
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
	public Figurine find(Integer id) {
		Figurine fig = new Figurine();

		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM figurine"
			        		+ "INNER JOIN article on figurine.article_idArticle = article.idArticle"
			        		+ "WHERE idFigurine = " + id);
			if(result.first()) {
				fig = new Figurine(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("description"),
						result.getInt("taille"),
						id);
				result.close();
				return fig;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}