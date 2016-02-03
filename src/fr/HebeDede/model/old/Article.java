package fr.HebeDede.model.old;

import java.io.Serializable;

import javax.persistence.Column;

import org.hibernate.annotations.Type;

public class Article implements Serializable {
	
	private static final long serialVersionUID = -8278421890067757122L;
	
	private Long idArticle;
	private Float prix;
	
	private Boolean enRayon;
	
	private Integer stock;
	
	public Long getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(Long idArticle) {
		this.idArticle = idArticle;
	}
	public Float getPrix() {
		return prix;
	}
	public void setPrix(Float prix) {
		this.prix = prix;
	}
	public Boolean getEnRayon() {
		return enRayon;
	}
	public void setEnRayon(Boolean enRayon) {
		this.enRayon = enRayon;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	
}
