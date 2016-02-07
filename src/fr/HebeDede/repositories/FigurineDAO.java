package fr.HebeDede.repositories;

<<<<<<< HEAD
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
=======
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Figurine;

public class FigurineDAO extends DAO<Figurine> {
	
	ArticleDAO articleDAO;

<<<<<<< HEAD
	public FigurineDAO() throws ClassNotFoundException, IllegalAccessException {
		super();
=======
	public FigurineDAO(Connection conn) {
		super(conn);
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854
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
<<<<<<< HEAD
			        ).executeQuery("SELECT * FROM figurine "
			        		+ "INNER JOIN article on figurine.article_idArticle = article.idArticle "
=======
			        ).executeQuery("SELECT * FROM figurine"
			        		+ "INNER JOIN article on figurine.article_idArticle = article.idArticle"
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854
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
<<<<<<< HEAD
	}
	
	public List<Figurine> findAllFig() {
		List<Figurine> figList = new ArrayList<Figurine>();
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM figurine "
			        		+ "INNER JOIN article on figurine.article_idArticle = article.idArticle");
			while (result.next()) {
				Figurine fig = new Figurine(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("description"),
						result.getInt("taille"),
						result.getInt("idFigurine"));
				figList.add(fig);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return figList;
=======
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854
	}

	
}