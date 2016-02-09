package fr.HebeDede.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Figurine;
import fr.HebeDede.service.ConsoleService;

public class FigurineDAO extends DAO<Figurine> {
	
	ArticleDAO articleDAO = new ArticleDAO();

	public FigurineDAO() {
		super();
	}

	@Override
	public void create(Figurine obj) {
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
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		}
	}

	@Override
	public void delete(Figurine obj) {
		
	}

	@Override
	public void update(Figurine obj) {
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
					}
				}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
		}
	}

	public List<Figurine> findAllFig() {
		List<Figurine> figList = new ArrayList<Figurine>();
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM figurine "
			        		+ "INNER JOIN article on figurine.Article_idArticle = article.idArticle");
			while (result.next()) {
				Figurine fig = new Figurine(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						result.getInt("idArticle"),
						result.getString("description"),
						result.getInt("taille"),
						result.getInt("idFigurine"));
				figList.add(fig);
			}
			result.close();
			return figList;
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		}
	
	}

	@Override
	public Figurine find(Integer id) {
		Figurine fig = new Figurine();

		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM figurine "
			        		+ "INNER JOIN article on figurine.article_idArticle = article.idArticle "
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
			ConsoleService.affiche("Echec de l'opération");
			return null;
		}
		return null;
	}
	
	public Figurine findByIdArticle(Integer id) {
		Figurine fig = new Figurine();
	
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			        ).executeQuery("SELECT * FROM figurine "
			        		+ "INNER JOIN article on figurine.Article_idArticle = article.idArticle "
			        		+ "WHERE Article_idArticle = " + id);
			if(result.first()) {
				fig = new Figurine(result.getBoolean("enRayon"),
						result.getFloat("prix"),
						id,
						result.getString("description"),
						result.getInt("taille"),
						result.getInt("idFigurine"));
				result.close();
				return fig;
			}
		} catch (SQLException e) {
			ConsoleService.affiche("Echec de l'opération");
			return null;
		}
		return null;
	}
}