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
import fr.HebeDede.model.Bandedessinee;

public class BandedessineeDAO extends DAO<Bandedessinee> {
	
	ArticleDAO articleDAO;

<<<<<<< HEAD
	public BandedessineeDAO() throws ClassNotFoundException, IllegalAccessException {
		super();
=======
	public BandedessineeDAO(Connection conn) {
		super(conn);
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854
	}

	@Override
	public boolean create(Bandedessinee obj) {
		try {
			articleDAO.create(obj);
			Integer articleId = articleDAO.findLastEntryId();
			
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM bandedessinee");
			
				result.moveToInsertRow();
				result.updateString("auteur", obj.getAuteur());
				result.updateString("categorie", obj.getCategorie());
				result.updateString("collection", obj.getCollection());
				result.updateString("description", obj.getDescription());
				result.updateString("editeur", obj.getEditeur());
				result.updateString("etat", obj.getEtat());
				result.updateString("libelle", obj.getLibelle());
				result.updateInt("nbrPages", obj.getNbrPages());
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
	public boolean delete(Bandedessinee obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM bandedessinee");
	
			while (result.next()) {
					int id = result.getInt("idBandeDessinee");
					if (id == obj.getIdBandeDessinee()) {
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
	public boolean update(Bandedessinee obj) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(
					"SELECT * FROM bandedessinee");

			while (result.next()) {
					int id = result.getInt("idBandeDessinee");
					if (id == obj.getIdArticle()) {
						result.moveToCurrentRow();
						result.updateString("auteur", obj.getAuteur());
						result.updateString("categorie", obj.getCategorie());
						result.updateString("collection", obj.getCollection());
						result.updateString("description", obj.getDescription());
						result.updateString("editeur", obj.getEditeur());
						result.updateString("etat", obj.getEtat());
						result.updateString("libelle", obj.getLibelle());
						result.updateInt("nbrPages", obj.getNbrPages());
						
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
	public Bandedessinee find(Integer id) {
		Bandedessinee bd = new Bandedessinee();

		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
<<<<<<< HEAD
			        ).executeQuery("SELECT * FROM bandedessinee "
			        		+ "INNER JOIN article on bandedessinee.article_idArticle = article.idArticle "
=======
			        ).executeQuery("SELECT * FROM bandedessinee"
			        		+ "INNER JOIN article on bandedessinee.article_idArticle = article.idArticle"
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854
			        		+ "WHERE idBandeDessinee = " + id);
			if(result.first()) {
				bd = new Bandedessinee(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("auteur"),
						result.getString("categorie"),
						result.getString("collection"),
						result.getString("description"),
						result.getString("editeur"),
						result.getString("etat"),
						result.getString("libelle"),
						result.getInt("nbrPages"),
						id);
				result.close();
				return bd;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
<<<<<<< HEAD
	
	public List<Bandedessinee> findAllBD() {
		List<Bandedessinee> bdList = new ArrayList<Bandedessinee>();
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM bandedessinee "
			        		+ "INNER JOIN article on bandedessinee.article_idArticle = article.idArticle");
			while (result.next()) {
				Bandedessinee bd = new Bandedessinee(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("auteur"),
						result.getString("categorie"),
						result.getString("collection"),
						result.getString("description"),
						result.getString("editeur"),
						result.getString("etat"),
						result.getString("libelle"),
						result.getInt("nbrPages"),
						result.getInt("idBandeDessinee"));
				bdList.add(bd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bdList;
	}
=======
>>>>>>> 1110ca8d1143e08fd04e1a3e755b094692ee4854

}