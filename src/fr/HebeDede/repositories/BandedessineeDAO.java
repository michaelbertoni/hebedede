package fr.HebeDede.repositories;

import javax.persistence.EntityTransaction;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Bandedessinee;

public class BandedessineeDAO extends DAO<Bandedessinee> {

	@Override
	public Bandedessinee find(long id) {
		return em.find(Bandedessinee.class, id);
	}

	@Override
	public void create(Bandedessinee obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void update(Bandedessinee obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void delete(Bandedessinee obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.remove(obj);
		transac.commit();
	}
	
}