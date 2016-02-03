package fr.HebeDede.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class DAO<T> {
	public EntityManagerFactory emf = Persistence.createEntityManagerFactory("fr.hebedede.jpa");
	public EntityManager em = emf.createEntityManager();
	
	/**
	 * Permet de r�cup�rer un objet via son ID
	 * @param id
	 * @return
	 */
	public abstract T find(long id);
	
	/**
	 * Permet de cr�er une entr�e dans la base de donn�es
	 * par rapport � un objet
	 * @param obj
	 */
	public abstract void create(T obj);
	
	/**
	 * Permet de mettre � jour les donn�es d'une entr�e dans la base 
	 * @param obj
	 */
	public abstract void update(T obj);
	
	/**
	 * Permet la suppression d'une entr�e de la base
	 * @param obj
	 */
	public abstract void delete(T obj);
}
