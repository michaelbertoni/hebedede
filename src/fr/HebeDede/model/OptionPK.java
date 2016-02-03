package fr.HebeDede.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the option database table.
 * 
 */
@Embeddable
public class OptionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idOption;

//	@Column(insertable=false, updatable=false)
	private int utilisateur_idUtilisateur;

//	@Column(insertable=false, updatable=false)
	private int article_idArticle;

	public OptionPK() {
	}
	public int getIdOption() {
		return this.idOption;
	}
	public void setIdOption(int idOption) {
		this.idOption = idOption;
	}
	public int getUtilisateur_idUtilisateur() {
		return this.utilisateur_idUtilisateur;
	}
	public void setUtilisateur_idUtilisateur(int utilisateur_idUtilisateur) {
		this.utilisateur_idUtilisateur = utilisateur_idUtilisateur;
	}
	public int getArticle_idArticle() {
		return this.article_idArticle;
	}
	public void setArticle_idArticle(int article_idArticle) {
		this.article_idArticle = article_idArticle;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OptionPK)) {
			return false;
		}
		OptionPK castOther = (OptionPK)other;
		return 
			(this.idOption == castOther.idOption)
			&& (this.utilisateur_idUtilisateur == castOther.utilisateur_idUtilisateur)
			&& (this.article_idArticle == castOther.article_idArticle);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idOption;
		hash = hash * prime + this.utilisateur_idUtilisateur;
		hash = hash * prime + this.article_idArticle;
		
		return hash;
	}
}