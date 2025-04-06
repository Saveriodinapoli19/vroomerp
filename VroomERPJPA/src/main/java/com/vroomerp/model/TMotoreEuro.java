package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_motore_euro database table.
 * 
 */
@Entity
@Table(name="t_motore_euro")
@NamedQuery(name="TMotoreEuro.findAll", query="SELECT t FROM TMotoreEuro t")
public class TMotoreEuro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="motore_euro_id")
	private Integer motoreEuroId;

	private String descrizione;

	public TMotoreEuro() {
	}

	public Integer getMotoreEuroId() {
		return motoreEuroId;
	}

	public void setMotoreEuroId(Integer motoreEuroId) {
		this.motoreEuroId = motoreEuroId;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



}