package fr.HebeDede.model.old;

import java.io.Serializable;
import java.util.Date;

public class Option implements Serializable {
	
	private static final long serialVersionUID = 3463257808317377914L;
	
	private Long idOption;
	private Date dateDebutOption;
	private Date dateFinOption;
	
	public Long getIdOption() {
		return idOption;
	}
	public void setIdOption(Long idOption) {
		this.idOption = idOption;
	}
	public Date getDateDebutOption() {
		return dateDebutOption;
	}
	public void setDateDebutOption(Date dateDebutOption) {
		this.dateDebutOption = dateDebutOption;
	}
	public Date getDateFinOption() {
		return dateFinOption;
	}
	public void setDateFinOption(Date dateFinOption) {
		this.dateFinOption = dateFinOption;
	}
	
	
}