package fr.HebeDede.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the option database table.
 * 
 */
@Entity
@NamedQuery(name="Option.findAll", query="SELECT o FROM Option o")
public class Option implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OptionPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDebutOption;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFinOption;

	//bi-directional many-to-one association to Article
	@ManyToOne
	@JoinColumn(name="article_idArticle", referencedColumnName="idArticle", insertable=false, updatable=false)
	private Article article;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="utilisateur_idUtilisateur", referencedColumnName="idUtilisateur", insertable=false, updatable=false)
	private Utilisateur utilisateur;

	public Option() {
	}

	public OptionPK getId() {
		return this.id;
	}

	public void setId(OptionPK id) {
		this.id = id;
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