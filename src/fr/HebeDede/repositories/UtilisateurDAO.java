package fr.HebeDede.repositories;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Utilisateur;

public class UtilisateurDAO extends DAO<Utilisateur> {

	@Override
	public Utilisateur find(long id) {
		return em.find(Utilisateur.class, id);
	}

	@Override
	public void create(Utilisateur obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void update(Utilisateur obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void delete(Utilisateur obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.remove(obj);
		transac.commit();
	}
	
	public Utilisateur findByUsername(String username) {
		Query query = em.createQuery("Select a From Utilisateur a Where a.username := username");
		return (Utilisateur) query.getSingleResult();
	}
	
}