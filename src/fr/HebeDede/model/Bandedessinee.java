package fr.HebeDede.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bandedessinee database table.
 * 
 */
@Entity
@NamedQuery(name="Bandedessinee.findAll", query="SELECT b FROM Bandedessinee b")
public class Bandedessinee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String libelle;

	private String auteur;

	private String categorie;

	private String collection;

	private String description;

	private String editeur;

	private String etat;

	private int nbrPages;

	//bi-directional many-to-one association to Article
	@ManyToOne
	private Article article;

	public Bandedessinee() {
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getAuteur() {
		return this.auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getCategorie() {
		return this.categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEditeur() {
		return this.editeur;
	}

	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public int getNbrPages() {
		return this.nbrPages;
	}

	public void setNbrPages(int nbrPages) {
		this.nbrPages = nbrPages;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}