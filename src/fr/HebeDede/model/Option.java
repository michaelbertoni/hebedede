package fr.HebeDede.model;

import java.io.Serializable;
import java.util.Date;

public class Option implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idOption;

	private Date dateDebutOption;

	private Date dateFinOption;

	private Article article;

	private Utilisateur utilisateur;
	
	public Option(Date début, Date fin, Article art, Utilisateur user, Integer id) {
		this.dateDebutOption = début;
		this.dateFinOption = fin;
		this.utilisateur = user;
		this.article = art;
		this.idOption = id;
	}
	
	public Option(Date début, Date fin, Article art, Utilisateur user) {
		this.dateDebutOption = début;
		this.dateFinOption = fin;
		this.utilisateur = user;
		this.article = art;
	}
	
	public Option() {
	}

	public Integer getIdOption() {
		return idOption;
	}

	public void setIdOption(Integer idOption) {
		this.idOption = idOption;
	}

	public Date getDateDebutOption() {
		return this.dateDebutOption;
	}

	public void setDateDebutOption(Date dateDebutOption) {
		this.dateDebutOption = dateDebutOption;
	}

	public Date getDateFinOption() {
		return this.dateFinOption;
	}

	public void setDateFinOption(Date dateFinOption) {
		this.dateFinOption = dateFinOption;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}