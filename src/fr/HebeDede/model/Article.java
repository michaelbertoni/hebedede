package fr.HebeDede.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the article database table.
 * 
 */
@Entity
@NamedQuery(name="Article.findAll", query="SELECT a FROM Article a")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idArticle;

	private Boolean enRayon;

	private BigDecimal prix;

	//bi-directional many-to-one association to Bandedessinee
	@OneToMany(mappedBy="article")
	private List<Bandedessinee> bandedessinees;

	//bi-directional many-to-one association to Figurine
	@OneToMany(mappedBy="article")
	private List<Figurine> figurines;

	//bi-directional many-to-one association to Option
	@OneToMany(mappedBy="article")
	private List<Option> options;

	public Article() {
	}

	public int getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	public Boolean getEnRayon() {
		return this.enRayon;
	}

	public void setEnRayon(Boolean enRayon) {
		this.enRayon = enRayon;
	}

	public BigDecimal getPrix() {
		return this.prix;
	}

	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}

	public List<Bandedessinee> getBandedessinees() {
		return this.bandedessinees;
	}

	public void setBandedessinees(List<Bandedessinee> bandedessinees) {
		this.bandedessinees = bandedessinees;
	}

	public Bandedessinee addBandedessinee(Bandedessinee bandedessinee) {
		getBandedessinees().add(bandedessinee);
		bandedessinee.setArticle(this);

		return bandedessinee;
	}

	public Bandedessinee removeBandedessinee(Bandedessinee bandedessinee) {
		getBandedessinees().remove(bandedessinee);
		bandedessinee.setArticle(null);

		return bandedessinee;
	}

	public List<Figurine> getFigurines() {
		return this.figurines;
	}

	public void setFigurines(List<Figurine> figurines) {
		this.figurines = figurines;
	}

	public Figurine addFigurine(Figurine figurine) {
		getFigurines().add(figurine);
		figurine.setArticle(this);

		return figurine;
	}

	public Figurine removeFigurine(Figurine figurine) {
		getFigurines().remove(figurine);
		figurine.setArticle(null);

		return figurine;
	}

	public List<Option> getOptions() {
		return this.options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public Option addOption(Option option) {
		getOptions().add(option);
		option.setArticle(this);

		return option;
	}

	public Option removeOption(Option option) {
		getOptions().remove(option);
		option.setArticle(null);

		return option;
	}

}