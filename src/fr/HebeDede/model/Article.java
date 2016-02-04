package fr.HebeDede.model;

import java.io.Serializable;


public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idArticle;

	private Boolean enRayon;

	private Float prix;

	public Integer getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(Integer idArticle) {
		this.idArticle = idArticle;
	}

	public Boolean getEnRayon() {
		return this.enRayon;
	}

	public void setEnRayon(Boolean enRayon) {
		this.enRayon = enRayon;
	}

	public Float getPrix() {
		return this.prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

}