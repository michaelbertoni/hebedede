package fr.HebeDede.repositories;

import javax.persistence.EntityTransaction;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Article;

public class ArticleDAO extends DAO<Article> {

	@Override
	public Article find(long id) {
		return em.find(Article.class, id);
	}

	@Override
	public void create(Article obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void update(Article obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void delete(Article obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.remove(obj);
		transac.commit();
	}
	
}