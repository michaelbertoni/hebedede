package fr.HebeDede.model.old;

import java.io.Serializable;

public class BandeDessinee extends Article implements Serializable {

	private static final long serialVersionUID = -1242814648320433272L;

	private String libelle;
	private String categorie;
	private String description;
	private String etat;
	private String auteur;
	private String editeur;
	private Integer nbPages;
	private String codeISBN;
	private String collection;
	
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public String getEditeur() {
		return editeur;
	}
	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}
	public Integer getNbPages() {
		return nbPages;
	}
	public void setNbPages(Integer nbPages) {
		this.nbPages = nbPages;
	}
	public String getCodeISBN() {
		return codeISBN;
	}
	public void setCodeISBN(String codeISBN) {
		this.codeISBN = codeISBN;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	
}
