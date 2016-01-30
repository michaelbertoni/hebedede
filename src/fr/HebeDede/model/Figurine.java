package fr.HebeDede.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the figurine database table.
 * 
 */
@Entity
@NamedQuery(name="Figurine.findAll", query="SELECT f FROM Figurine f")
public class Figurine implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;

	private String libelle;

	private int taille;

	//bi-directional many-to-one association to Article
	@ManyToOne
	private Article article;

	public Figurine() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getTaille() {
		return this.taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}