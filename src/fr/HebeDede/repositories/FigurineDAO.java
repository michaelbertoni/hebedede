package fr.HebeDede.repositories;

import javax.persistence.EntityTransaction;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Figurine;

public class FigurineDAO extends DAO<Figurine> {

	@Override
	public Figurine find(long id) {
		return em.find(Figurine.class, id);
	}

	@Override
	public void create(Figurine obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void update(Figurine obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void delete(Figurine obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.remove(obj);
		transac.commit();
	}
	
}