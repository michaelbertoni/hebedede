package fr.HebeDede.model.old;

public class Figurine extends Article {

	private static final long serialVersionUID = -5142790565594273287L;
	
	private Float taille;
	private String description;
	
	public Float getTaille() {
		return taille;
	}
	public void setTaille(Float taille) {
		this.taille = taille;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
