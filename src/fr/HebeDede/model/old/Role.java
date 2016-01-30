package fr.HebeDede.model.old;

import java.io.Serializable;

public class Role implements Serializable {
	
	private static final long serialVersionUID = -3915850192530083621L;
	
	private Boolean droitOptionBD;
	private Boolean droitAnnulationOption;
	private Boolean droitConsultationOption;
	private Boolean droitModifBD;
	private Boolean droitModifFigurine;
	
	public Boolean getDroitOptionBD() {
		return droitOptionBD;
	}
	public void setDroitOptionBD(Boolean droitOptionBD) {
		this.droitOptionBD = droitOptionBD;
	}
	public Boolean getDroitAnnulationOption() {
		return droitAnnulationOption;
	}
	public void setDroitAnnulationOption(Boolean droitAnnulationOption) {
		this.droitAnnulationOption = droitAnnulationOption;
	}
	public Boolean getDroitConsultationOption() {
		return droitConsultationOption;
	}
	public void setDroitConsultationOption(Boolean droitConsultationOption) {
		this.droitConsultationOption = droitConsultationOption;
	}
	public Boolean getDroitModifBD() {
		return droitModifBD;
	}
	public void setDroitModifBD(Boolean droitModifBD) {
		this.droitModifBD = droitModifBD;
	}
	public Boolean getDroitModifFigurine() {
		return droitModifFigurine;
	}
	public void setDroitModifFigurine(Boolean droitModifFigurine) {
		this.droitModifFigurine = droitModifFigurine;
	}
	
	public Role() {
		super();
		this.setDroitAnnulationOption(false);
		this.setDroitConsultationOption(false);
		this.setDroitModifBD(false);
		this.setDroitModifFigurine(false);
		this.setDroitOptionBD(false);
	}
	
	public Role(Boolean optionBd, Boolean annulOpt, Boolean consultOpt, Boolean modifBd, Boolean modifFig) {
		super();
		this.setDroitAnnulationOption(annulOpt);
		this.setDroitConsultationOption(consultOpt);
		this.setDroitModifBD(modifBd);
		this.setDroitModifFigurine(modifFig);
		this.setDroitOptionBD(optionBd);
	}

	
}