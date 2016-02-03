package fr.HebeDede.repositories;

import javax.persistence.EntityTransaction;

import fr.HebeDede.data.DAO;
import fr.HebeDede.model.Option;

public class OptionDAO extends DAO<Option> {

	@Override
	public Option find(long id) {
		return em.find(Option.class, id);
	}

	@Override
	public void create(Option obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void update(Option obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.persist(obj);
		transac.commit();
	}

	@Override
	public void delete(Option obj) {
		EntityTransaction transac = em.getTransaction();
		transac.begin();
		em.remove(obj);
		transac.commit();
	}
	
}