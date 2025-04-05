package com.vroomerp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_ruolo_utente database table.
 * 
 */
@Entity
@Table(name="t_ruolo_utente")
@NamedQuery(name="TRuoloUtente.findAll", query="SELECT t FROM TRuoloUtente t")
public class TRuoloUtente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ruolo_utente_id")
	private Integer ruoloUtenteId;

	private String descrizione;

	public TRuoloUtente() {
	}

	public Integer getRuoloUtenteId() {
		return this.ruoloUtenteId;
	}

	public void setRuoloUtenteId(Integer ruoloUtenteId) {
		this.ruoloUtenteId = ruoloUtenteId;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}